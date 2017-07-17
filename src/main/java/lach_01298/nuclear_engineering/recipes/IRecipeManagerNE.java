package lach_01298.nuclear_engineering.recipes;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import lach_01298.nuclear_engineering.util.Log;
import lach_01298.nuclear_engineering.util.UtilItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.Maps;

public interface IRecipeManagerNE
{

	List <ItemStack> getItemResult(List <ItemStack> inputItemStacks);
	boolean isResultForItemstacks(List <ItemStack> inputItemStacks);
	boolean isResultForItemstack(ItemStack inputItemStack, int slot);
	
	List <FluidStack> getFluidResult(List <FluidStack> inputFluidStacks);
	boolean isResultForFluidStacks(List <FluidStack> inputFluidStacks);
	boolean isResultForFluidStack(FluidStack inputFluidStacks, int tank);
}
