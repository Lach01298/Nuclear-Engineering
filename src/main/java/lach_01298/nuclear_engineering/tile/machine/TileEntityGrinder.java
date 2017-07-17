package lach_01298.nuclear_engineering.tile.machine;

import lach_01298.nuclear_engineering.recipes.IRecipeManagerNE;
import lach_01298.nuclear_engineering.recipes.RecipeManagerGrinder;




public class TileEntityGrinder extends TileEntityEnergyMachine
{

	private static final int sizeInput = 1;
	private static final int sizeOutput = 1;
	private static final int sizeUpgrade = 2;
	private static final int maxEnergy = 100000;
	private static final int defualtRunningEnergy = 20;
	private static final int totalWork = 100;
	final static IRecipeManagerNE recipeManager = RecipeManagerGrinder.instance();
	
	
	public TileEntityGrinder()
	{
		super(sizeInput, sizeOutput, sizeUpgrade, maxEnergy, defualtRunningEnergy, totalWork, recipeManager);
		
	}
	
	
	

	
}