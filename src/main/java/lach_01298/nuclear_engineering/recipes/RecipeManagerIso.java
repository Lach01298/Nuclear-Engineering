package lach_01298.nuclear_engineering.recipes;


import io.netty.handler.logging.LogLevel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import lach_01298.nuclear_engineering.util.Log;
import lach_01298.nuclear_engineering.util.OreDicPreferences;
import lach_01298.nuclear_engineering.util.UtilItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RecipeManagerIso implements IRecipeManagerNE
{
	private static final RecipeManagerIso RecipeManager = new RecipeManagerIso();
	private static final Set<IsoRecipe> recipes = new HashSet<IsoRecipe>();


	public static RecipeManagerIso instance()
    {
        return RecipeManager;
    }

	public void addRecipe(List<ItemStack> input, FluidStack fluid ,List<ItemStack> output)
	{
		if(input != null && output != null)
		{
			if(getResult(input, fluid) != null)
			{
				Log.log(Level.ERROR, "Failed to add Grinder recipe:" + input
						+ " to " + output);
				return;
			}
			IsoRecipe recipe = new IsoRecipe(input, fluid, output);
			RecipeManager.recipes.add(recipe);
			return;

		}
		Log.log(Level.ERROR, "Failed to add Grinder recipe:" + input + " to "
				+ output);
	}


		
	

	
	
	
	public List<ItemStack> getResult(List<ItemStack> input, FluidStack fluid )
	{
		if(input != null)
		{
			Iterator<IsoRecipe> set = RecipeManager.recipes.iterator();
			while(set.hasNext())
			{
				IsoRecipe current = set.next();
				if(current.getInput()== input && current.getFluid()== fluid)
				{
					return current.getOutput();
				}
			}
			return null;	
		}

		return null;
	}

	public ItemStack getResult(List<ItemStack> input, FluidStack fluid, int slot )
	{
		if(input != null)
		{
			Iterator<IsoRecipe> set = RecipeManager.recipes.iterator();
			while(set.hasNext())
			{
				IsoRecipe current = set.next();
				if(current.getInput()== input && current.getFluid()== fluid)
				{
					return current.getOutput().get(slot);
				}
			}
			return null;	
		}

		return null;
	}

	@Override
	public ItemStack getResult(ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
