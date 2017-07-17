
package lach_01298.nuclear_engineering.compatability.jei;

import org.apache.logging.log4j.Level;

import lach_01298.nuclear_engineering.util.Log;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;


public class GrindingRecipeHandler implements IRecipeHandler<GrindingRecipe> {

	@Override
	public Class<GrindingRecipe> getRecipeClass() {
		return GrindingRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return Plugin.GrinderID;
	}

	@Override
	public String getRecipeCategoryUid(GrindingRecipe recipe) {
		return Plugin.GrinderID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(GrindingRecipe recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(GrindingRecipe recipe) {
		if (recipe.getInputs().isEmpty()) {
			Log.log(Level.ERROR, "Recipe has no inputs. {}");
		}
		if (recipe.getOutputs().isEmpty()) {
			Log.log(Level.ERROR, "Recipe has no outputs. {}");
		}
		return true;
	}

}
