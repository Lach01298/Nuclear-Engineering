package lach_01298.nuclear_engineering.tile.generator;

import lach_01298.nuclear_engineering.block.generator.BlockRTG;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityRTG extends TileEntityGenerator implements ITickable
{

	public TileEntityRTG(int output)
	{
		super(output, output*10);
		
	
	}

	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{

		IBlockState state =	this.worldObj.getBlockState(this.getPos());
		EnumFacing direction = state.getValue(BlockRTG.FACING);
		
		if(capability == CapabilityEnergy.ENERGY)
		{
				if(direction.getAxis().equals(facing.getAxis()))
				{
					return (T) energy;	
				}
		}

		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		IBlockState state =	this.worldObj.getBlockState(this.getPos());
		EnumFacing direction = state.getValue(BlockRTG.FACING);
		
		if(capability == CapabilityEnergy.ENERGY)
		{
				if(direction.getAxis().equals(facing.getAxis()))
				{
					return true;	
				}
		}
		return false;
	}

	
	public void pushEnergy() {
		
		IBlockState state =	this.worldObj.getBlockState(this.getPos());
		EnumFacing direction = state.getValue(BlockRTG.FACING);
		
		
		EnumFacing outputSides[] = {direction,direction.getOpposite()};
		
		for (EnumFacing side : outputSides) 
		{
			
			if(this.getWorld().getTileEntity(this.getPos().offset(side)) != null)
			{
				TileEntity tile = this.getWorld().getTileEntity(this.getPos().offset(side));
				if(tile.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite()))
				{
					tile.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).receiveEnergy(this.energy.extractEnergy(this.energy.getEnergyStored(), false), false);
					//System.out.println(this.energy.getEnergyStored());
				}
			}
		}
			
			
			
			
			
			
		}
	
	

	@Override
	public void update()
	{
		this.energy.generateEnergy(maxEnergyOutput);
		pushEnergy();
	}
	
}
