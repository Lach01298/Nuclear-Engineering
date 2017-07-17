package lach_01298.nuclear_engineering.tile.multiblock;

import lach_01298.nuclear_engineering.fluid.EnumTankType;
import lach_01298.nuclear_engineering.fluid.NETank;
import lach_01298.nuclear_engineering.inventory.EnumSlotType;
import lach_01298.nuclear_engineering.inventory.NEItemStackHandler;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyMachine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityItemHatch extends TileEntityMultiblock 
{

	private static final int size = 1;
	
	protected NEItemStackHandler slots;
	
	
	public TileEntityItemHatch()
	{
		
		slots = new NEItemStackHandler(size,EnumSlotType.INPUT);
	}
	
	
	public TileEntityItemHatch(EnumSlotType type)
	{
		
		slots = new NEItemStackHandler(size,type)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				TileEntityItemHatch.this.markDirty();
			}
		};
		
	}
	

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
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
			
				return (T) slots;
			
			
		}
		return super.getCapability(capability, facing);
	}
		
	public NEItemStackHandler getSlotHandler()
	{
		return slots;
	}
	
	

	public void readFromNBT(NBTTagCompound compound)
	{
		slots.deserializeNBT(compound.getCompoundTag("slots"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("slots", slots.serializeNBT());

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
}
