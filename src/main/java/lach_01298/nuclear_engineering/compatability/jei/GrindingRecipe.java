package lach_01298.nuclear_engineering.compatability.jei;

import java.awt.Color;
import java.util.Collections;
import java.util.List;





import java.util.Map.Entry;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class GrindingRecipe extends BlankRecipeWrapper 
{
	private final List<List<ItemStack>> input;
	private final List<List<ItemStack>> output;
	
	public GrindingRecipe(List<ItemStack> inputs, List<ItemStack> output)
	{
		this.input = Collections.singletonList(inputs);
		this.output = Collections.singletonList(output);
	}

	public List<List<ItemStack>> getInputs() {
		return input;
	}

	public List<List<ItemStack>> getOutputs() {
		return output;
	}


	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInputLists(ItemStack.class, input);
		
		for(List<ItemStack> entry : this.output)
		{
			ingredients.setOutputs(ItemStack.class, entry);	
		}
		
	}
	
	
}