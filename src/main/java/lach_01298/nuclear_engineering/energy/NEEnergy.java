package lach_01298.nuclear_engineering.energy;

import net.minecraftforge.energy.EnergyStorage;

public class NEEnergy extends EnergyStorage
{

	public NEEnergy(int capacity)
	{
		super(capacity);
		
	}
	
	public NEEnergy(int capacity, boolean canRecive, boolean canExtract)
	{
		super(capacity);
		if(!canRecive)
		{
			this.maxReceive =0;
		}
		if(!canExtract)
		{
			this.maxExtract =0;
		}
	}

	public void setEnergy(int energy)
	{
		this.energy = energy;
	}
	
	public void generateEnergy(int energy)
	{
		this.energy =+ energy;
		if(this.energy > this.capacity)
		{
			this.energy = this.capacity;
		}
	}

	public void setCanReceive(boolean can)
	{
		if(can)
		{
			this.maxReceive =this.capacity;
		}
		else
		{
			this.maxReceive =0;
		}
		
	}

	public void setCanExtract(boolean can)
	{
		if(can)
		{
			this.maxExtract =this.capacity;
		}
		else
		{
			this.maxExtract =0;
		}
		
	}


}
