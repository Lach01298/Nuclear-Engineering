package lach_01298.nuclear_engineering.compatability.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lach_01298.nuclear_engineering.recipes.RecipeManagerGrinder;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class GrinderRecipeMaker
{

	public static List<GrindingRecipe> getRecipes(IJeiHelpers helpers)
	{
		IStackHelper stackHelper = helpers.getStackHelper();
		RecipeManagerGrinder manager = RecipeManagerGrinder.instance();
		Map<List<ItemStack>, List<ItemStack>> Recipes = manager.getRecipes();

		List<GrindingRecipe> recipes = new ArrayList<GrindingRecipe>();

		for(Entry<List<ItemStack>, List<ItemStack>> itemStackItemStackEntry : Recipes.entrySet())
		{
			List<ItemStack> input = itemStackItemStackEntry.getKey();
			List<ItemStack> output = itemStackItemStackEntry.getValue();

			
			GrindingRecipe recipe = new GrindingRecipe(input, output);
			recipes.add(recipe);
		}

		return recipes;
	}

}