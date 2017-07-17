package lach_01298.nuclear_engineering.tile.multiblock;

import lach_01298.nuclear_engineering.block.generator.BlockRTG;
import lach_01298.nuclear_engineering.energy.NEEnergy;
import lach_01298.nuclear_engineering.fluid.NETank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityPowerHatch extends TileEntityMultiblock
{


	private int energyOutput;
	protected int maxEnergyOutput;
	private static final int  capacity = 100000;
	protected NEEnergy energy;
	
	public TileEntityPowerHatch()
	{
		this.energy = new NEEnergy(capacity, true, false);	
	}
	
	
	public TileEntityPowerHatch(boolean input)
	{
		
		if(input)
		{
			this.energy = new NEEnergy(capacity, true, false);	
		}
		else
		{
			this.energy = new NEEnergy(capacity, false, true);	
		}
		
	
		
	}

	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityEnergy.ENERGY)
		{
			return (T) energy;
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == CapabilityEnergy.ENERGY)
		{
			return true;
		}
		return false;
	}

	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		this.energy.setCanExtract(compound.getBoolean("extract"));
		this.energy.setCanReceive(compound.getBoolean("receive"));
		this.energy.setEnergy(compound.getInteger("energy"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setBoolean("extract", this.energy.canExtract());
		compound.setBoolean("receive", this.energy.canReceive());
		compound.setInteger("energy",this.energy.getEnergyStored());
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