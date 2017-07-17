package lach_01298.nuclear_engineering.compatability.jei;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.gui.GUIGrinder;
import lach_01298.nuclear_engineering.recipes.RecipeManagerGrinder;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;


import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidRegistry;

@JEIPlugin
public class Plugin extends BlankModPlugin 
{
	
	final static String GrinderID = NuclearEngineering.MOD_ID+".grinding";
	private static IJeiRuntime jeiRuntime = null;
	@Override
	public void register(@Nonnull IModRegistry registry) 
	{
		

		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

	
		registry.addRecipeCategories
		(
				new CategoryGrinder(guiHelper)
				
		);
		
		registry.addRecipeHandlers(
				new GrindingRecipeHandler()
				
		);
		
		registry.addRecipeClickArea(GUIGrinder.class, 77, 29, 27, 27, GrinderID);
		registry.addRecipes(GrinderRecipeMaker.getRecipes(jeiHelpers));
		
	}
	

	
	
}