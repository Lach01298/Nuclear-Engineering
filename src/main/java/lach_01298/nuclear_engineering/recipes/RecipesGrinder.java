package lach_01298.nuclear_engineering.recipes;

import java.util.List;

import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.block.blocks.BlockOre;
import lach_01298.nuclear_engineering.item.RegisterItems;
import lach_01298.nuclear_engineering.util.OreDicPreferences;
import lach_01298.nuclear_engineering.util.UtilItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesGrinder
{

	private static RegisterBlocks NEBlocks;
	private static RegisterItems NEItems;
	
	//ores
	private static List <ItemStack> oreCopper = OreDictionary.getOres("oreCopper");
	private static List<ItemStack> oreTin = OreDictionary.getOres("oreTin");
	private static List<ItemStack> oreAluminium = OreDictionary.getOres("oreAluminum");
	private static List<ItemStack> oreLead = OreDictionary.getOres("oreLead");
	private static List<ItemStack> oreUranium = OreDictionary.getOres("oreUranium");
	private static List<ItemStack> oreHafnium = OreDictionary.getOres("oreHafnium");
	
	//ingots
	private static List<ItemStack> copper = OreDictionary.getOres("ingotCopper");
	private static List<ItemStack> tin = OreDictionary.getOres("ingotTin");
	private static List<ItemStack> aluminium = OreDictionary.getOres("ingotAluminum");
	private static List<ItemStack> lead = OreDictionary.getOres("ingotLead");
	private static List<ItemStack> uranium = OreDictionary.getOres("ingotUranium");
	private static List<ItemStack> hafnium = OreDictionary.getOres("ingotHafnium");
	
	private static List<ItemStack> dustCopper = OreDictionary.getOres("dustCopper");
	private static List<ItemStack> dustTin = OreDictionary.getOres("dustTin");
	private static List<ItemStack> dustAluminium = OreDictionary.getOres("dustAluminum");
	private static List<ItemStack> dustLead = OreDictionary.getOres("dustLead");
	private static List<ItemStack> dustUranium = OreDictionary.getOres("dustUranium");
	private static List<ItemStack> dustHafnium = OreDictionary.getOres("dustHafnium");
	
	private static List<ItemStack> dustIron = OreDictionary.getOres("dustIron");
	private static List<ItemStack> dustGold = OreDictionary.getOres("dustGold");
	
	
	public static void register()
	{
		
		// dust
		RecipeManagerGrinder.instance().addOreRecipe(oreCopper, OreDicPreferences.get("dustCopper", 2));
		RecipeManagerGrinder.instance().addOreRecipe(oreTin, OreDicPreferences.get("dustTin", 2));
		RecipeManagerGrinder.instance().addOreRecipe(oreAluminium, OreDicPreferences.get("dustAluminum", 2));
		RecipeManagerGrinder.instance().addOreRecipe(oreLead, OreDicPreferences.get("dustLead", 2));
		RecipeManagerGrinder.instance().addOreRecipe(oreUranium, OreDicPreferences.get("dustUranium", 2));
		RecipeManagerGrinder.instance().addOreRecipe(oreHafnium, OreDicPreferences.get("dustHafnium", 2));

		RecipeManagerGrinder.instance().addRecipe(new ItemStack(Blocks.IRON_ORE), OreDicPreferences.get("dustIron", 2));
		RecipeManagerGrinder.instance().addRecipe(new ItemStack(Blocks.GOLD_ORE), OreDicPreferences.get("dustGold", 2));
	
		RecipeManagerGrinder.instance().addOreRecipe(copper, OreDicPreferences.get("dustCopper", 1));
	
		RecipeManagerGrinder.instance().addOreRecipe(tin, OreDicPreferences.get("dustTin", 1));
		RecipeManagerGrinder.instance().addOreRecipe(aluminium, OreDicPreferences.get("dustAluminum", 1));
		RecipeManagerGrinder.instance().addOreRecipe(lead, OreDicPreferences.get("dustLead", 1));
		RecipeManagerGrinder.instance().addOreRecipe(uranium, OreDicPreferences.get("dustUranium", 1));
		RecipeManagerGrinder.instance().addOreRecipe(hafnium, OreDicPreferences.get("dustHafnium", 1));

		RecipeManagerGrinder.instance().addRecipe(new ItemStack(Items.IRON_INGOT), OreDicPreferences.get("dustIron", 1));
		RecipeManagerGrinder.instance().addRecipe(new ItemStack(Items.GOLD_INGOT), OreDicPreferences.get("dustGold", 1));
		
		//resources
		RecipeManagerGrinder.instance().addRecipe(new ItemStack(Blocks.STONE,1,1), new ItemStack(NEItems.itemResource,1,0));
		
		
		// misc
		RecipeManagerGrinder.instance().addRecipe(new ItemStack(Blocks.COBBLESTONE,1,0), new ItemStack(Blocks.SAND,1,0));
		RecipeManagerGrinder.instance().addRecipe(new ItemStack(Blocks.GRAVEL,1,0), new ItemStack(Items.FLINT,1,0));
		RecipeManagerGrinder.instance().addRecipe(new ItemStack(Blocks.GLOWSTONE,1,0), new ItemStack(Items.GLOWSTONE_DUST,4,0));
	
	}
	
	
	
	
}
