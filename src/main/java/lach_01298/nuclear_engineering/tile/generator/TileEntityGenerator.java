package lach_01298.nuclear_engineering.tile.generator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import lach_01298.nuclear_engineering.block.generator.BlockRTG;
import lach_01298.nuclear_engineering.energy.NEEnergy;
import lach_01298.nuclear_engineering.tile.TileEntityNE;

public class TileEntityGenerator extends TileEntityNE
{

	private int energyOutput;
	protected int maxEnergyOutput;
	private int capacity;
	protected NEEnergy energy;
	
	public TileEntityGenerator(int maxEnergy, int capacity)
	{
		this.maxEnergyOutput = maxEnergy;
		this.capacity = capacity;
		this.energy = new NEEnergy(this.capacity, false, true);
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


	

}
