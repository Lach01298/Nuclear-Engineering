package lach_01298.nuclear_engineering.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lach_01298.nuclear_engineering.util.Log;
import lach_01298.nuclear_engineering.util.UtilFluid;
import lach_01298.nuclear_engineering.util.UtilItem;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class RecipeManagerChemicalReactor implements IRecipeManagerNE
{

	private static final RecipeManagerChemicalReactor RecipeManager = new RecipeManagerChemicalReactor();
	private final Map<NEIngredients, NEIngredients> recipes = Maps.<NEIngredients, NEIngredients> newHashMap();
	
	public static RecipeManagerChemicalReactor instance()
    {
        return RecipeManager;
    }

	
	
	public void addRecipe(List<ItemStack> inputItem, List<FluidStack> inputFluid, List<ItemStack> outputItem, List<FluidStack> outputFluid)
	{
		if(inputItem != null)
		{
			if(getItemResult(inputItem) != null && getFluidResult(inputFluid) != null)
			{
				Log.log(Level.ERROR, "Failed to add Chemical Reactor recipe:" + inputItem + " to " + outputItem +" becuse recipe already exists");
				return;
			}
			
			NEIngredients inputs = new NEIngredients(inputItem, inputFluid);
			NEIngredients outputs = new NEIngredients(outputItem, outputFluid);
			
			RecipeManager.recipes.put(inputs,outputs);
			return;

		}
		Log.log(Level.ERROR, "Failed to add Chemical Reactorr recipe:" + inputItem +" & "+ inputFluid+ " to " + outputItem + " & " + outputFluid);
	}
	
	
	
	
	
	
	@Override
	public List<ItemStack> getItemResult(List<ItemStack> inputItemStacks)
	{
		{
			
			for(Entry<NEIngredients, NEIngredients> entry : this.recipes.entrySet())
			{
				if(UtilItem.compareItemStacksLists(inputItemStacks,  entry.getKey().getItems()))
				{
					List<ItemStack> result = new ArrayList<ItemStack>();
					for(ItemStack stack : entry.getValue().getItems())
					{
					   
					    if(stack == null)
					    {
					    	 result.add(null);
					    }
					    else
					    {
						    ItemStack copy = stack.copy();
						    result.add(copy);
					    }
					}
					
					return result;
				}
			}
		}

		return null;
	}

	@Override
	public boolean isResultForItemstacks(List<ItemStack> inputItemStacks)
	{
		return getItemResult(inputItemStacks)!= null;
	}

	@Override
	public boolean isResultForItemstack(ItemStack inputItemStack, int slot)
	{
		if(inputItemStack != null)
		{
		
			for(Entry<NEIngredients, NEIngredients> entry : this.recipes.entrySet())
			{
				
				for( ItemStack entry2 : entry.getKey().getItems())
				{
					if(entry2.isItemEqual(inputItemStack)&& entry.getKey().getItems().indexOf(entry2) == slot)
					{
						return true;
					}	
				}	
			}
		}

		return false;
	}

	@Override
	public List<FluidStack> getFluidResult(List<FluidStack> inputFluidStacks)
	{
{
			
			for(Entry<NEIngredients, NEIngredients> entry : this.recipes.entrySet())
			{
				if(UtilFluid.doesFluidStacksContain(inputFluidStacks,  entry.getKey().getFluids()))
				{
					List<FluidStack> result = new ArrayList<FluidStack>();
					for(FluidStack stack : entry.getValue().getFluids())
					{
						FluidStack copy = stack.copy();
					    result.add(copy);
					}
					
					return result;
				}
			}
		}

		return null;
	}

	@Override
	public boolean isResultForFluidStacks(List<FluidStack> inputFluidStacks)
	{
		return getFluidResult(inputFluidStacks)!= null;
	}

	@Override
	public boolean isResultForFluidStack(FluidStack inputFluidStacks, int tank)
	{
		if(inputFluidStacks != null)
		{
		
			for(Entry<NEIngredients, NEIngredients> entry : this.recipes.entrySet())
			{
				
				for( FluidStack entry2 : entry.getKey().getFluids())
				{
					if(entry2.isFluidEqual(inputFluidStacks)&& entry.getKey().getFluids().indexOf(entry2) == tank)
					{
						return true;
					}	
				}	
			}
		}

		return false;
	}



	public void addRecipe(ItemStack inputI, FluidStack inputF, ItemStack outputI, FluidStack outputF)
	{
		List<ItemStack> inputIs = new ArrayList<ItemStack>();
		inputIs.add(inputI);
		
		List<ItemStack> outputIs = new ArrayList<ItemStack>();
		outputIs.add(outputI);
		
		List<FluidStack> inputFs = new ArrayList<FluidStack>();
		inputFs.add(inputF);
		
		List<FluidStack> outputFs = new ArrayList<FluidStack>();
		outputFs.add(outputF);
		
		addRecipe(inputIs, inputFs, outputIs, outputFs);

	}

	public void addOreRecipe(List<ItemStack> inputI,FluidStack inputF,ItemStack outputI, FluidStack outputF)
	{
		for(int i =0; i< inputI.size();i++)
		{
			addRecipe(inputI.get(i),inputF,outputI,outputF);
		}
	}
	
	
	
}
