package lach_01298.nuclear_engineering.compatability.jei;

import lach_01298.nuclear_engineering.NuclearEngineering;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;


public class CategoryGrinder<T extends GrindingRecipe> extends BlankRecipeCategory<T>{

	protected static final int inputSlot = 0;
	protected static final int fuelSlot = 1;
	protected static final int outputSlot = 2;
	private final IDrawable background;
	private final String localizedName;
	
	public CategoryGrinder(IGuiHelper guiHelper)
	{
		ResourceLocation location = new ResourceLocation( NuclearEngineering.MOD_ID, "textures/gui/grinder.png");
		background = guiHelper.createDrawable(location, 49, 24, 94, 39);
		localizedName = Translator.translateToLocal(NuclearEngineering.MOD_ID+".grinding");
	}

	@Override
	public String getUid()
	{
		return NuclearEngineering.MOD_ID+".grinding";
	}

	@Override
	public String getTitle()
	{
		return localizedName ;
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, T recipeWrapper, IIngredients ingredients)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 6, 9);
		guiItemStacks.init(outputSlot, false, 66, 9);
		guiItemStacks.set(ingredients);
		
		
	}
	
	
}