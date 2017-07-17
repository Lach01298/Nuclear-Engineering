package lach_01298.nuclear_engineering.block;


import javax.annotation.Nullable;

import com.google.common.base.Function;

import lach_01298.nuclear_engineering.block.blocks.BlockMetal;
import lach_01298.nuclear_engineering.block.blocks.BlockOre;
import lach_01298.nuclear_engineering.block.generator.BlockRTG;
import lach_01298.nuclear_engineering.block.machine.BlockChemicalReactor;
import lach_01298.nuclear_engineering.block.machine.BlockGrinder;
import lach_01298.nuclear_engineering.block.machine.BlockIsoSeparator;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockCasing;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockComponent;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockComponentDirectional;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockFluidHatch;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockHeatExchager;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockItemHatch;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockPowerHatch;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockReactorController;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockReactorCore;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockTurbine;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RegisterBlocks
{

	public static Block metal;
	public static Block ore;
	public static Block grinder;
	public static Block isoSeparator;
	public static Block chemicalReactor;
	public static Block plutoniumRTG;
	
	
	// multiblock parts
	public static Block reactorCore;
	public static Block reactorController;
	public static Block reactorCase;
	public static Block itemHatch;
	public static Block fluidHatch;
	public static Block powerHatch;
	public static Block machineCase;
	public static Block turbine;
	public static Block heatExchanger;
	public static Block radiator;
	public static Block motor;
	
	public static void init()
	{
		// ores
		ore = new BlockOre("ore");
		ore.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_STONE, ore.getStateFromMeta(BlockMetal.MetalType.COPPER.ordinal()));
		ore.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_STONE, ore.getStateFromMeta(BlockMetal.MetalType.TIN.ordinal()));
		ore.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_STONE, ore.getStateFromMeta(BlockMetal.MetalType.ALUMINIUM.ordinal()));
		ore.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_STONE, ore.getStateFromMeta(BlockMetal.MetalType.LEAD.ordinal()));
		ore.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_IRON, ore.getStateFromMeta(BlockMetal.MetalType.HAFNIUM.ordinal()));
		ore.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_IRON, ore.getStateFromMeta(BlockMetal.MetalType.URANIUM.ordinal()));
		ItemBlock itemBlockOre = (new ItemMultiTexture(ore, ore, new Function<ItemStack, String>()
		{
			@Nullable
			public String apply(@Nullable ItemStack stack)
			{
				return BlockMetal.MetalType.byMetadata(stack.getMetadata()).getUnlocalizedName();
			}
		}));
		
		itemBlockOre.setRegistryName(ore.getRegistryName());
		registerBlock(ore, itemBlockOre);
		
		//metal blocks
		metal = new BlockMetal("metal");
		metal.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_STONE, ore.getStateFromMeta(BlockMetal.MetalType.COPPER.ordinal()));
		metal.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_STONE, ore.getStateFromMeta(BlockMetal.MetalType.TIN.ordinal()));
		metal.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_STONE, ore.getStateFromMeta(BlockMetal.MetalType.ALUMINIUM.ordinal()));
		metal.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_STONE, ore.getStateFromMeta(BlockMetal.MetalType.LEAD.ordinal()));
		metal.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_IRON, ore.getStateFromMeta(BlockMetal.MetalType.HAFNIUM.ordinal()));
		metal.setHarvestLevel("pickaxe", Constants.MINING_LEVEL_IRON, ore.getStateFromMeta(BlockMetal.MetalType.URANIUM.ordinal()));
		ItemBlock itemBlockMetal = (new ItemMultiTexture(metal, metal, new Function<ItemStack, String>()
		{
			@Nullable
			public String apply(@Nullable ItemStack stack)
			{
				return BlockMetal.MetalType.byMetadata(stack.getMetadata()).getUnlocalizedName();
			}
		}));
		
		itemBlockMetal.setRegistryName(metal.getRegistryName());
		registerBlock(metal, itemBlockMetal);
		
	
		
	
		
		//machines
		
		grinder = registerBlock(new BlockGrinder());
		chemicalReactor = registerBlock(new BlockChemicalReactor());
		isoSeparator = registerBlock(new BlockIsoSeparator());
		plutoniumRTG = registerBlock(new BlockRTG("plutonium"));
		
		
		// multiblock parts
		reactorCore = registerBlock(new BlockReactorCore("reactorCore"));
		reactorController = registerBlock(new BlockReactorController("reactorController"));
		reactorCase = registerBlock(new BlockCasing("reactorCasing"));
		
		itemHatch = new BlockItemHatch("itemHatch");
		ItemBlock itemBlockItemHatch = (new ItemMultiTexture(itemHatch, itemHatch, new Function<ItemStack, String>()
				{
					@Nullable
					public String apply(@Nullable ItemStack stack)
					{
						return BlockItemHatch.HatchType.byMetadata(stack.getMetadata()).getUnlocalizedName();
					}
				}));
		itemBlockItemHatch.setRegistryName(itemHatch.getRegistryName());
		registerBlock(itemHatch,itemBlockItemHatch);
		
		fluidHatch = new BlockFluidHatch("fluidHatch");
		ItemBlock itemBlockfluidHatch = (new ItemMultiTexture(fluidHatch, fluidHatch, new Function<ItemStack, String>()
				{
					@Nullable
					public String apply(@Nullable ItemStack stack)
					{
						return BlockItemHatch.HatchType.byMetadata(stack.getMetadata()).getUnlocalizedName();
					}
				}));
				
		itemBlockfluidHatch.setRegistryName(fluidHatch.getRegistryName());
		registerBlock(fluidHatch,itemBlockfluidHatch);
		
		powerHatch = new BlockPowerHatch("powerHatch");
		ItemBlock itemBlockpowerHatch = (new ItemMultiTexture(powerHatch, powerHatch, new Function<ItemStack, String>()
				{
					@Nullable
					public String apply(@Nullable ItemStack stack)
					{
						return BlockItemHatch.HatchType.byMetadata(stack.getMetadata()).getUnlocalizedName();
					}
				}));
				
		itemBlockpowerHatch.setRegistryName(powerHatch.getRegistryName());
		registerBlock(powerHatch,itemBlockpowerHatch);
		
		machineCase = registerBlock(new BlockCasing("machineCasing"));
		radiator = registerBlock(new BlockComponent("radiator",Material.IRON));
		motor = registerBlock(new BlockComponentDirectional("motor",Material.IRON));
		heatExchanger = registerBlock(new BlockHeatExchager("heatExchanger"));
		turbine = registerBlock(new BlockTurbine("turbine"));

	}

	private static <T extends Block> T registerBlock(T block)
	{
		ItemBlock itemBlock = new ItemBlock(block) {public int getMetadata(int meta) {return meta;}};
		itemBlock.setRegistryName(block.getRegistryName());
		
		return registerBlock(block, itemBlock);
	}

	private static <T extends Block> T registerBlock(T block, ItemBlock itemBlock)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);

		if (block instanceof ItemModelProvider)
		{
			((ItemModelProvider) block).registerItemModel(itemBlock);
		}

		return block;
	}

	

	
	
	
	
}
