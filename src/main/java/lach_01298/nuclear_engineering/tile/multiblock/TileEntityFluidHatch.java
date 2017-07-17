package lach_01298.nuclear_engineering.tile.multiblock;

import lach_01298.nuclear_engineering.fluid.EnumTankType;
import lach_01298.nuclear_engineering.fluid.NETank;
import lach_01298.nuclear_engineering.inventory.EnumSlotType;
import lach_01298.nuclear_engineering.inventory.NEItemStackHandler;
import lach_01298.nuclear_engineering.network.MessageFluid;
import lach_01298.nuclear_engineering.network.NEPacketHandler;
import lach_01298.nuclear_engineering.tile.ITankTile;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyFluidMachine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityFluidHatch  extends TileEntityMultiblock implements ITankTile 
{

	private static final int size = 10000;
	protected NETank tank;
	
	
	public TileEntityFluidHatch()
	{
		tank = new NETank(size,EnumTankType.INPUT,this);
	}
	
	
	public TileEntityFluidHatch(EnumTankType type)
	{
		tank = new NETank(size, type,this)
		{
			protected void onContentsChanged()
			{
				MessageFluid message = new MessageFluid(fluid, 0,  this.tile);
				NEPacketHandler.INSTANCE.sendToAll(message);

			}
		};
	}
	

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
				return (T) tank;
		}
		return super.getCapability(capability, facing);
	}
		
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		tank = (NETank) tank.readFromNBT(compound.getCompoundTag("tank"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
	
		
		return compound;
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
	public NETank getTank(int id)
	{
		return tank;
	}
	
	
}
