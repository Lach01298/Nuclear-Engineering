package lach_01298.nuclear_engineering.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class NEIngredients
{

	private List<ItemStack> items;
	private List<FluidStack> fluids;
	
	public NEIngredients(List<ItemStack> items, List<FluidStack> fluids)
	{
		this.items = items;
		this.fluids = fluids;
	}
	
	public List<ItemStack> getItems()
	{
		return items;
	}
	public List<FluidStack> getFluids()
	{
		return fluids;
	}
	
}
