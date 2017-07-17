package lach_01298.nuclear_engineering.recipes;

import io.netty.handler.logging.LogLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import lach_01298.nuclear_engineering.util.Log;
import lach_01298.nuclear_engineering.util.OreDicPreferences;
import lach_01298.nuclear_engineering.util.UtilItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.Maps;

public class RecipeManagerGrinder implements IRecipeManagerNE
{
	private static final RecipeManagerGrinder RecipeManager = new RecipeManagerGrinder();
	private final Map<List<ItemStack>, List<ItemStack>> recipes = Maps.<List<ItemStack>, List<ItemStack>> newHashMap();

	public static RecipeManagerGrinder instance()
    {
        return RecipeManager;
    }
	
	public void addRecipe(List<ItemStack> input, List<ItemStack> output)
	{
		if(input != null && output != null)
		{
			if(getItemResult(input) != null)
			{
				Log.log(Level.ERROR, "Failed to add Grinder recipe:" + input + " to " + output +" becuse recipe already exists");
				return;
			}
			RecipeManager.recipes.put(input, output);
			return;

		}
		Log.log(Level.ERROR, "Failed to add Grinder recipe:" + input + " to " + output);
	}

	public void addRecipe(ItemStack input, ItemStack output)
	{
		List<ItemStack> inputs = new ArrayList<ItemStack>();
		inputs.add(input);
		
		List<ItemStack> outputs = new ArrayList<ItemStack>();
		outputs.add(output);
		addRecipe(inputs,outputs);
	}
	
	public void addOreRecipe(List<ItemStack> input, ItemStack output)
	{
		for(int i =0; i< input.size();i++)
		{
			List<ItemStack> inputs = new ArrayList<ItemStack>();
			inputs.add(input.get(i));
			
			List<ItemStack> outputs = new ArrayList<ItemStack>();
			outputs.add(output);
			addRecipe(inputs,outputs);
		}
	}
	
	
	
	
	
	public Map<List<ItemStack>, List<ItemStack>> getRecipes()
	    {
	        return this.recipes;
	    }

	@Override
	public List<ItemStack> getItemResult(List<ItemStack> stacks)
	{
		if(stacks != null)
		{
		
			for(Entry<List<ItemStack>, List<ItemStack>> entry : this.recipes.entrySet())
			{
				if(UtilItem.compareItemStacksLists(stacks,  entry.getKey()))
				{
					List<ItemStack> result = new ArrayList<ItemStack>();
					for(ItemStack stack : entry.getValue())
					{
					    ItemStack copy = stack.copy();
					    result.add(copy);
					}
					
					return result;
				}
			}
		}

		return null;
	}

	@Override
	public boolean isResultForItemstacks(List<ItemStack> inputStacks)
	{
		return getItemResult(inputStacks)!= null;
	}

	@Override
	public List<FluidStack> getFluidResult(List<FluidStack> inputFluidStacks)
	{
		return null;
	}

	@Override
	public boolean isResultForFluidStacks(List<FluidStack> inputFluidStacks)
	{
		return false;
	}

	@Override
	public boolean isResultForItemstack(ItemStack stack, int slot)
	{	
		if(stack != null)
		{
		
			for(Entry<List<ItemStack>, List<ItemStack>> entry : this.recipes.entrySet())
			{
				
				for( ItemStack entry2 : entry.getKey())
				{
					if(entry2.isItemEqual(stack)&& entry.getKey().indexOf(entry2) == slot)
					{
						return true;
					}	
				}	
			}
		}

		return false;
	}

	@Override
	public boolean isResultForFluidStack(FluidStack inputFluidStacks, int tank)
	{
		return false;
	}
	
	
}
