package lach_01298.nuclear_engineering.fluid;

import lach_01298.nuclear_engineering.network.MessageFluid;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityFluidHatch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class NETank extends FluidTank
{

	private EnumTankType type;
	
	public NETank(int capacity, EnumTankType type, TileEntity tile)
	{
		super(capacity);
		this.type =type;
		this.tile = tile;
	}
	
	
	public NETank(FluidTank tank, EnumTankType type)
	{
		super(tank.getFluid(),tank.getCapacity());
		this.type =type;
	}
	
	

	//Accessible by Externals
	@Override
	public int fill(FluidStack resource, boolean doFill)
	 {
		if(type == EnumTankType.INPUT ||type == EnumTankType.STORAGE )
		{
			return super.fill(resource, doFill);
		}
		return 0;
		 
	 }

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		if(type == EnumTankType.OUTPUT ||type == EnumTankType.STORAGE )
		{
			return super.drain(resource, doDrain);
		}
		return null;

	}
	
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		if(type == EnumTankType.OUTPUT ||type == EnumTankType.STORAGE )
		{
			return super.drain(maxDrain, doDrain);
		}
		return null;
		
	}
	
	
	
	//Accessible by internals
	/**Bypasses tank type
     */
	public boolean canDrainFluid(FluidStack fluid)
	{
		if( this.fluid.amount - fluid.amount < 0)
		{
			return false;
		}
		if(fluid.equals(this.fluid) || this.fluid == null)
		{
			return true;
		}
		
		return false;
	}
	
	/**Bypasses tank type
     */
	public boolean canFillFluid(FluidStack fluid)
	{
		if(this.fluid == null)
		{
			return true;
		}
		
		if(fluid.amount+ this.fluid.amount> capacity)
		{
			return false;
		}
		if(fluid.equals(this.fluid))
		{
			return true;
		}
		
		return false;
	}

	/**Bypasses tank type
     */
	public void fill(int amount)
	{
		if(this.fluid != null)
		{
			this.fluid.amount += amount;
		}
		this.onContentsChanged();
	}
	public void setAmount(int amount)
	{
		if(this.fluid != null)
		{
			this.fluid.amount = amount;
		}
		
	}
	
	/**Bypasses tank type
     */
	public void fill(FluidStack fluid)
	{
		this.fluid = fluid;
		this.onContentsChanged();
		
	}
	/**Bypasses tank type
     */
	public void drain(int amount)
	{
		fluid.amount -= amount;
		if(fluid.amount < 1)
		{
			fluid =null;
		}
		this.onContentsChanged();
	}

	
	
	
	public FluidTank readFromNBT(NBTTagCompound nbt)
	{
		EnumTankType type = EnumTankType.valueOf(nbt.getString("tankType").toUpperCase());

		NETank tank = new NETank(super.readFromNBT(nbt), type);
		return tank;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setString("tankType", this.type.getname());
		return nbt;
	}
	
	
	
	
	
	
	
	
	
	
}



