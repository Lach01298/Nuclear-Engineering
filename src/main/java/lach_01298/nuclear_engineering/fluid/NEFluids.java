package lach_01298.nuclear_engineering.fluid;

import java.util.HashSet;
import java.util.Set;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.block.fluid.MaterialGas;
import lach_01298.nuclear_engineering.block.fluid.NEBlockAcid;
import lach_01298.nuclear_engineering.block.fluid.NEBlockFluid;
import lach_01298.nuclear_engineering.block.fluid.NEBlockToxicGas;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class NEFluids
{

	private static RegisterBlocks NEBlocks;
	private final static String texturePath = NuclearEngineering.MOD_ID+":blocks/";
	
	public static Fluid HF_Acid;
	public static Fluid gas_UF6;
	public static Fluid heavyWater;
	public static Fluid superHeatedHeavyWater;
	public static Fluid steam;
	
	public static NEBlockAcid blockHF_Acid;
	public static NEBlockToxicGas blockGas_UF6;
	public static BlockFluidClassic blockHeavyWater;
	public static BlockFluidClassic blockSuperHeatedHeavyWater;
	public static BlockFluidClassic blockSteam;
	
	public static Set<Fluid> fluids = new HashSet<Fluid>();
	
	public static void registerFluids()
	{
		HF_Acid = createFluid("hf_acid", "hf_acid", false).setDensity(1150).setViscosity(1500);
		blockHF_Acid = registerFluidBlock(new NEBlockAcid(HF_Acid, Material.WATER));
		HF_Acid.setBlock(blockHF_Acid);
		FluidRegistry.addBucketForFluid(HF_Acid);
		fluids.add(HF_Acid);
		
		gas_UF6 = createFluid("gas_uf6", "gas_uf6", false).setDensity(100).setViscosity(1000);
		blockGas_UF6 = registerFluidBlock(new NEBlockToxicGas(gas_UF6, MaterialGas.Gas));
		gas_UF6.setBlock(blockGas_UF6);
		FluidRegistry.addBucketForFluid(gas_UF6);
		fluids.add(gas_UF6);
		
		heavyWater = createFluid("heavyWater", "heavyWater", false).setDensity(1000).setViscosity(1000);
		blockHeavyWater = registerFluidBlock(new NEBlockFluid(heavyWater, Material.WATER));
		heavyWater.setBlock(blockHeavyWater);
		FluidRegistry.addBucketForFluid(heavyWater);
		fluids.add(heavyWater);
		
		superHeatedHeavyWater = createFluid("superHeatedHeavyWater", "heavyWater", false).setDensity(1000).setViscosity(1000).setTemperature(500);
		blockSuperHeatedHeavyWater = registerFluidBlock(new NEBlockFluid(superHeatedHeavyWater, Material.WATER));
		superHeatedHeavyWater.setBlock(blockSuperHeatedHeavyWater);
		FluidRegistry.addBucketForFluid(superHeatedHeavyWater);
		fluids.add(superHeatedHeavyWater);
		
		steam = createFluid("steam", "steam", false).setDensity(-1000).setViscosity(100);
		blockSteam = registerFluidBlock(new NEBlockFluid(steam, Material.WATER));
		steam.setBlock(blockSteam);
		FluidRegistry.addBucketForFluid(steam);
		fluids.add(steam);
		
		
		
		registerRenders();
	}

	private static Fluid createFluid(String name, String textureName, boolean hasFlowIcon)
	{
		ResourceLocation still = new ResourceLocation(texturePath+textureName + "_still");
		ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePath+textureName+ "_flow"): still;

		Fluid fluid = new Fluid(name, still, flowing);
		if(!FluidRegistry.registerFluid(fluid))
		{
			throw new IllegalStateException(String.format("Unable to register fluid %s", fluid.getName()));
		}

		
		return fluid;
	}

	private static <T extends Block & IFluidBlock> T registerFluidBlock(T block)
	{
		String fluidName = block.getFluid().getUnlocalizedName();
		block.setUnlocalizedName(fluidName);
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);
		

		return block;
	}

	public static void registerRenders()
	{
		fluids.forEach(NEFluids::registerRender);
	}

	private static void registerRender(Fluid item)
	{
		Item i = Item.getItemFromBlock(item.getBlock());
		String loc = NuclearEngineering.MOD_ID + ":" + item.getName();
		ModelLoader.setCustomMeshDefinition(i, stack -> new ModelResourceLocation(loc, "fluid"));
		ModelLoader.setCustomStateMapper(item.getBlock(), new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation(loc, "fluid");
			}
		});
	}
	
	
	
}
