package lach_01298.nuclear_engineering.fission;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class NeutronReflectors
{
	public static final double defualtReflectorFactor =0.9;
	public static final int defualtHeatLossFactor =10;
	
	public static boolean isNeutronReflector(IBlockState state)
	{
		
		if(state == Blocks.COAL_BLOCK.getDefaultState())
		{
			return true;
		}
		
		return false;
	}



	public static double getRefectorFactor(IBlockState state)
	{
		if(state == Blocks.COAL_BLOCK.getDefaultState())
		{
			return 0.5;
		}
		return defualtReflectorFactor;
	}



	public static int getHeatFactor(IBlockState reflector)
	{
		// TODO Auto-generated method stub
		return defualtHeatLossFactor;
	}
	
	
	
}
