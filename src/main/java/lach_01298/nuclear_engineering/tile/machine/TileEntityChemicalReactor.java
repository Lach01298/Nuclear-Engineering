package lach_01298.nuclear_engineering.tile.machine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import lach_01298.nuclear_engineering.fluid.EnumTankType;
import lach_01298.nuclear_engineering.fluid.NETank;
import lach_01298.nuclear_engineering.network.MessageFluid;
import lach_01298.nuclear_engineering.network.NEPacketHandler;
import lach_01298.nuclear_engineering.recipes.IRecipeManagerNE;
import lach_01298.nuclear_engineering.recipes.RecipeManagerChemicalReactor;

public class TileEntityChemicalReactor extends TileEntityEnergyFluidMachine
{

	
	private static final int inputSlotSize = 1;
	private static final int outputSlotSize = 1;
	private static final int upgradeSlotSize = 2;
	private static final int energyStorage = 100000;
	private static final int baseRunningEnergy = 60;
	private static final int processTime = 50;
	private static final int tanksize = 8000;
	private NETank tankInput;
	private NETank tankOutput;
	
	final static IRecipeManagerNE recipeManager = RecipeManagerChemicalReactor.instance();
	
	public TileEntityChemicalReactor()
	{
		super(inputSlotSize, outputSlotSize, upgradeSlotSize, energyStorage, baseRunningEnergy, processTime, tanksize, recipeManager);
		
		
		tankInput = new NETank(tanksize,EnumTankType.INPUT,this)
		{ 
			protected void onContentsChanged()
			{
				MessageFluid message = new MessageFluid(fluid, 0, (TileEntityEnergyFluidMachine) this.tile);
				NEPacketHandler.INSTANCE.sendToAll(message);
				
			}
	    

	    };
		tankOutput = new NETank(tanksize,EnumTankType.OUTPUT,this)
		{ 
			protected void onContentsChanged()
			{
				MessageFluid message = new MessageFluid(fluid, 1, (TileEntityEnergyFluidMachine) this.tile);
				NEPacketHandler.INSTANCE.sendToAll(message);
			}
	    

	    };
	    tankInput.setTileEntity(this);
	    tankOutput.setTileEntity(this);
	    tanks.add(tankInput);
		tanks.add(tankOutput);
		
	}
	
	@Override
	protected boolean canCraft()
	{
		
		List<ItemStack> inputsI = new ArrayList<ItemStack>();
		inputsI.add(slotsInput.getStackInSlot(0));
		
		List<FluidStack> inputsF = new ArrayList<FluidStack>();
		inputsF.add(tankInput.getFluid());
		
		if(recipeManager.isResultForItemstacks(inputsI)&& recipeManager.isResultForFluidStacks(inputsF))
		{
			if(tankOutput.canFillFluid(recipeManager.getFluidResult(inputsF).get(0)))
			{
				if(slotsOutput.getStackInSlot(0)== null)
				{
					return true;
				}
				else
				{
					if(slotsOutput.getStackInSlot(0).isItemEqual(recipeManager.getItemResult(inputsI).get(0)))
					{
						int result = slotsOutput.getStackInSlot(0).stackSize
								+ recipeManager.getItemResult(inputsI).get(0).stackSize;
						if(result <= slotsOutput.getStackInSlot(0).getMaxStackSize())
						{
							return true;
						}
					}
				}
			}
		}
		return false;
		
	}
	
	@Override
	protected void completeCraft()
	{
		
		List<ItemStack> inputs = new ArrayList<ItemStack>();
		inputs.add(slotsInput.getStackInSlot(0));
		
		if(slotsOutput.getStackInSlot(0) == null)
		{
			slotsOutput.setStackInSlot(0, recipeManager.getItemResult(inputs).get(0));
		}
		else
		{
			slotsOutput.getStackInSlot(0).stackSize += recipeManager.getItemResult(inputs).get(0).stackSize;
		}
		slotsInput.getStackInSlot(0).stackSize -= 1;
		if(slotsInput.getStackInSlot(0).stackSize < 1)
		{
			slotsInput.setStackInSlot(0, null);
		}
		
		List<FluidStack> inputsF = new ArrayList<FluidStack>();
		inputsF.add(tankInput.getFluid());
		
		
		if(tankOutput.getFluid()== null)
		{
			tankOutput.fill(recipeManager.getFluidResult(inputsF).get(0));
		}
		else
		{
			tankOutput.fill(recipeManager.getFluidResult(inputsF).get(0).amount);
		}
		tankInput.drain(100);
		this.currentWork = 0;
	}
	
	
	
	
	
	
	

	
	
	
	
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{

		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if(facing == null)
			{
				return (T) slotsUpgrade;
			}
			switch(facing)
			{
				case UP:
					return (T) slotsInput;
				case DOWN:
					return (T) slotsOutput;
				default:
					return (T) slotsInput;

			}
		}
		if(capability == CapabilityEnergy.ENERGY)
		{
			return (T) energy;
		}
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			switch(facing)
			{
				case UP:
					return (T) tankInput;
				case DOWN:
					return (T) tankOutput;
				default:
					return (T) tankInput;

			}
		}

		return super.getCapability(capability, facing);
	}
	
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{

		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		if(capability == CapabilityEnergy.ENERGY)
		{
			return true;
		}
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		tankInput = (NETank) tankInput.readFromNBT(compound.getCompoundTag("InTank"));
		tankOutput = (NETank) tankOutput.readFromNBT(compound.getCompoundTag("OutTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("InTank", tankInput.writeToNBT(new NBTTagCompound()));
		compound.setTag("OutTank", tankOutput.writeToNBT(new NBTTagCompound()));
		return compound;
	}
	

	
	
	public void setField(int id, int value)
	{
		switch (id) {
			case 0:
				energy.extractEnergy(energy.getEnergyStored(), false);
				energy.receiveEnergy(value, false);
				break;
			case 1:
				this.runningEnergy = value;
				break;
			case 2:
				this.currentWork = value;
				break;
			case 3:
				this.WorkPerTick = value;
				break;
			case 4:
				this.tankInput.setAmount(value);
				break;
			case 5:
				this.tankOutput.setAmount(value);
			}
	}
	
}
