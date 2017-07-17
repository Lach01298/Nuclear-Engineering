package lach_01298.nuclear_engineering.recipes;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.fluid.NEFluids;
import lach_01298.nuclear_engineering.item.RegisterItems;
import lach_01298.nuclear_engineering.item.materials.ItemResource;

public class RecipesChemicalReactor
{

	private static RegisterBlocks NEBlocks;
	private static RegisterItems NEItems;
	
	private static List<ItemStack> uranium = OreDictionary.getOres("ingotUranium");

	
	public static void register()
	{
		RecipeManagerChemicalReactor.instance().addRecipe(new ItemStack(NEItems.itemResource,1,ItemResource.EnumType.FLUORITE.ordinal()), new FluidStack(FluidRegistry.WATER, 100), null, new FluidStack(NEFluids.HF_Acid, 100));
		RecipeManagerChemicalReactor.instance().addOreRecipe(uranium, new FluidStack(NEFluids.HF_Acid, 100), null, new FluidStack(NEFluids.gas_UF6, 500));
	}
}
	
	
