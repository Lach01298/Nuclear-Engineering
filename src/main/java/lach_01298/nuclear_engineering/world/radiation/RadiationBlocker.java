package lach_01298.nuclear_engineering.world.radiation;


import java.util.List;
import java.util.Map;

import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.block.blocks.BlockMetal;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RadiationBlocker
{

	private static final Map<IBlockState, RadiationBlocker> blockers = Maps.newHashMap();
	
	private double alphaCoefient;
	private double betaCoefient;
	private double gammaCoefient;
	private double neutronCoefient;
	
	
	private final static double defualtAlpha = 2.5;
	private final static double airAlpha = 0.5; 
	
	private final static double defualtBeta = 0.1; 
	private final static double airBeta = 0.01; 
	
	private final static double defualtGamma = 0.01;
	private final static double airGamma = 0; 
	
	private final static double defualtNeutron = 0.001; 
	private final static double airNeutron = 0;
	
	private static RegisterBlocks NEBlocks;
	
	public RadiationBlocker(double alpha, double beta, double gamma, double neutron)
	{
		this.alphaCoefient = alpha;
		this.betaCoefient = beta;
		this.gammaCoefient = gamma;
		this.neutronCoefient = neutron;
	}
	
	public static void registerRadiationBlockers()
	{
		
		
		List<ItemStack> list =OreDictionary.getOres("blockLead");
		for(ItemStack item : list)
		{
			if(item.getItem() instanceof ItemBlock)
			{
				int meta = item.getMetadata();
				ItemBlock block = (ItemBlock) item.getItem();
				registerRadiationBlocker(block.getBlock().getStateFromMeta(meta),new RadiationBlocker(defualtAlpha*25,defualtBeta*25,defualtGamma*25,defualtNeutron*6));
			}
			
		}
		
		for(int i = 0; i < 16; i++)
		{
			
			registerRadiationBlocker(Blocks.STONE.getStateFromMeta(i),new RadiationBlocker(defualtAlpha*2,defualtBeta*2,defualtGamma*2,defualtNeutron*2));
		}
		registerRadiationBlocker(Blocks.WATER.getDefaultState(),new RadiationBlocker(defualtAlpha,defualtBeta,defualtGamma,defualtNeutron*100));
		registerRadiationBlocker(Blocks.IRON_BLOCK.getDefaultState(),new RadiationBlocker(defualtAlpha*10,defualtBeta*10,defualtGamma*10,defualtNeutron*2));
		registerRadiationBlocker(Blocks.GOLD_BLOCK.getDefaultState(),new RadiationBlocker(defualtAlpha*20,defualtBeta*20,defualtGamma*20,defualtNeutron*4));
		
	}
	
	
	public static void registerRadiationBlocker(IBlockState block, RadiationBlocker value)
	{
		blockers.put(block, value);
	}
	
	
	
	public static double getAlphaCoefient(IBlockState block)
	{
		if(blockers.containsKey(block))
		{
			return blockers.get(block).alphaCoefient;
		}
		if(block.getMaterial().isSolid())
		{
			return defualtAlpha;
		}
		else
		{
			return airAlpha;
		}
	}
	
	public static double getBetaCoefient(IBlockState block)
	{
		if(blockers.containsKey(block))
		{
			return blockers.get(block).betaCoefient;
		}
		if(block.getMaterial().isSolid())
		{
			return defualtBeta;
		}
		else
		{
			return airBeta;
		}
	}
	
	public static double getGammaCoefient(IBlockState block)
	{
		if(blockers.containsKey(block))
		{
			
			return blockers.get(block).gammaCoefient;
		}
		if(block.getMaterial().isSolid())
		{
			
			return defualtGamma;
		}
		else
		{
			
			return airGamma;
		}
	}
	
	public static double getNeutronCoefient(IBlockState block)
	{
		if(blockers.containsKey(block))
		{
			return blockers.get(block).neutronCoefient;
		}
		if(block.getMaterial().isSolid())
		{
			return defualtNeutron;
		}
		else
		{
			return airNeutron;
		}
	}

}
