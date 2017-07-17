package lach_01298.nuclear_engineering.tile.machine;

import java.util.ArrayList;
import java.util.List;

import lach_01298.nuclear_engineering.inventory.EnumSlotType;
import lach_01298.nuclear_engineering.inventory.NEItemStackHandler;
import lach_01298.nuclear_engineering.item.RegisterItems;
import lach_01298.nuclear_engineering.recipes.IRecipeManagerNE;
import lach_01298.nuclear_engineering.recipes.RecipeManagerGrinder;
import lach_01298.nuclear_engineering.tile.TileEntityNE;
import lach_01298.nuclear_engineering.util.UtilItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityEnergyMachine extends TileEntityNE implements ITickable
{

	private static int sizeInput =1;
	private static int sizeOutput =1;
	private static int sizeUpgrade = 2;
	private static int maxEnergy;
	private static int defualtRunningEnergy;
	private static int totalWork;
	IRecipeManagerNE recipeManager;
	
	protected int runningEnergy;
	protected int currentWork;
	protected int WorkPerTick;
	protected EnergyStorage energy;
	private static final int defualtWorkPerTick = 1;
	public static int SIZE;

	public TileEntityEnergyMachine(int inputSlotSize, int outputSlotSize, int upgradeSlotSize, int energyCapacity, int baseRunningEnergy, int processTime, IRecipeManagerNE recipeManager )
	{
		this.sizeInput = inputSlotSize;
		this.sizeOutput = outputSlotSize;
		this.sizeUpgrade = upgradeSlotSize;
		this.SIZE= sizeInput + sizeOutput+ sizeUpgrade;
		this.maxEnergy = energyCapacity;
		this.defualtRunningEnergy = baseRunningEnergy;
		this.totalWork = processTime;
		this.energy = new EnergyStorage(this.maxEnergy);
		this.recipeManager = recipeManager;
	}

	protected NEItemStackHandler slotsInput = new NEItemStackHandler(sizeInput,EnumSlotType.INPUT)
	{
		@Override
		protected void onContentsChanged(int slot)
		{

			TileEntityEnergyMachine.this.markDirty();
		}
	};

	protected NEItemStackHandler slotsOutput = new NEItemStackHandler(sizeOutput,EnumSlotType.OUTPUT)
	{
		@Override
		protected void onContentsChanged(int slot)
		{

			TileEntityEnergyMachine.this.markDirty();
		}
	};

	protected NEItemStackHandler slotsUpgrade = new NEItemStackHandler(sizeUpgrade,EnumSlotType.UPGRADE)
	{
		@Override
		protected void onContentsChanged(int slot)
		{

			TileEntityEnergyMachine.this.markDirty();
		}
	};

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{

		super.readFromNBT(compound);
		
			slotsInput.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
			slotsOutput.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
			slotsUpgrade.deserializeNBT((NBTTagCompound) compound.getTag("itemsUpg"));
		
			this.currentWork = compound.getInteger("work");
			energy.receiveEnergy(compound.getInteger("energy"), false);
			this.WorkPerTick = compound.getInteger("workPerTick");
			this.runningEnergy = compound.getInteger("runningEnergy");
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{

		super.writeToNBT(compound);
		compound.setTag("itemsIn", slotsInput.serializeNBT());
		compound.setTag("itemsOut", slotsOutput.serializeNBT());
		compound.setTag("itemsUpg", slotsUpgrade.serializeNBT());

		compound.setInteger("energy", energy.getEnergyStored());
		compound.setInteger("work", this.currentWork);
		compound.setInteger("runningEnergy", this.runningEnergy);
		compound.setInteger("workPerTick", this.WorkPerTick);

		return compound;
	}

	public boolean canInteractWith(EntityPlayer playerIn)
	{

		return !isInvalid()
				&& playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
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
		return super.hasCapability(capability, facing);
	}

	@Override
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

		return super.getCapability(capability, facing);
	}

	public int getWork()
	{
		return this.currentWork;
	}

	public int getMaxWork()
	{
		return totalWork;
	}

	public int getSpeed()
	{
		return this.WorkPerTick;
	}

	public int getRunningEnergy()
	{
		return this.runningEnergy;
	}
	public EnergyStorage getEnergyStorage()
	{
		return this.energy;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{

		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void update()
	{

		if(!this.worldObj.isRemote)
		{
			upgrades();
			run();
		}
	}

	private void run()
	{
		if(energy.getEnergyStored() >= this.runningEnergy && canCraft())
		{
			this.currentWork += this.WorkPerTick;
			energy.extractEnergy(this.runningEnergy, false);
		}
		else
		{
			this.currentWork -= this.WorkPerTick;
			if(this.currentWork < 0)
			{
				this.currentWork = 0;
			}
		}

		if(this.currentWork >= totalWork)
		{
			completeCraft();
		}
	}

	private void upgrades()
	{
		if(sizeUpgrade >= 2)
		{
			this.WorkPerTick = defualtWorkPerTick;
			this.runningEnergy = defualtRunningEnergy;
			int currentrunningEnergy = defualtRunningEnergy;
			
			// speed upgrade
			if(UtilItem.compareItemStacks(slotsUpgrade.getStackInSlot(0), new ItemStack(RegisterItems.itemUpgrade, 1, 0)))
			{
				this.WorkPerTick = defualtWorkPerTick
						* (slotsUpgrade.getStackInSlot(0).stackSize + 1);
				this.runningEnergy = (int) (defualtRunningEnergy * Math.pow(1.5f, slotsUpgrade.getStackInSlot(0).stackSize));
				currentrunningEnergy = (int) (defualtRunningEnergy * Math.pow(1.5f, slotsUpgrade.getStackInSlot(0).stackSize));
			}
			//energy upgrade
			if(UtilItem.compareItemStacks(slotsUpgrade.getStackInSlot(1), new ItemStack(RegisterItems.itemUpgrade, 1, 1)))
			{
				this.runningEnergy = (int) (currentrunningEnergy / (slotsUpgrade.getStackInSlot(1).stackSize + 1))
						+ currentrunningEnergy / 4;
			}
		}
	}

	protected boolean canCraft()
	{
		List<ItemStack> inputs = new ArrayList<ItemStack>();
		inputs.add(slotsInput.getStackInSlot(0));
		
		if(slotsInput.getStackInSlot(0) == null)
		{
			return false;
		}

		
		if(!recipeManager.isResultForItemstacks(inputs)) return false;
		if(slotsOutput.getStackInSlot(0) == null) return true;
		if(!slotsOutput.getStackInSlot(0).isItemEqual(recipeManager.getItemResult(inputs).get(0))) return false;
		int result = slotsOutput.getStackInSlot(0).stackSize + recipeManager.getItemResult(inputs).get(0).stackSize;
		return result <= slotsOutput.getStackInSlot(0).getMaxStackSize();

	}

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
		this.currentWork = 0;
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
			
			}
	}

}
