package lach_01298.nuclear_engineering.fission;

import lach_01298.nuclear_engineering.fluid.NEFluids;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class Moderators
{
	public static final double defualtModeratorFactor =0.1;

	




	public static double getModeratorFactor(Fluid fluid)
	{
		if(fluid == NEFluids.heavyWater)
		{
			return 0.7;
		}
		if(fluid == FluidRegistry.WATER)
		{
			return 0.5;
		}
		return defualtModeratorFactor;
	}




	
	
	
}

