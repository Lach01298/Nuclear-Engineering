package lach_01298.nuclear_engineering.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class IsoRecipe
{

	private final FluidStack fluid;
	private final List<ItemStack> input;
	private final List<ItemStack> output;
	
	public IsoRecipe(List<ItemStack> input,FluidStack fluid, List<ItemStack> output)
	{
		this.input =input;
		this.fluid =fluid;
		this.output =output;
	}
	
	
	
	FluidStack getFluid()
	{
		return fluid;
	}
	List<ItemStack> getInput()
	{
		return input;
	}
	List<ItemStack> getOutput()
	{
		return output;
	}
	
	
	
}
