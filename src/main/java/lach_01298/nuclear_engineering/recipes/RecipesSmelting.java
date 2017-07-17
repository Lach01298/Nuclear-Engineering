package lach_01298.nuclear_engineering.recipes;


import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.block.blocks.BlockMetal;
import lach_01298.nuclear_engineering.block.blocks.BlockOre;
import lach_01298.nuclear_engineering.item.RegisterItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesSmelting
{

	private static RegisterBlocks NEBlocks;
	private static RegisterItems NEItems;
	
	
	private static ItemStack oreCopper = new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.COPPER.ordinal());
	private static ItemStack oreTin = new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.TIN.ordinal());
	private static ItemStack oreAluminium = new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.ALUMINIUM.ordinal());
	private static ItemStack oreLead = new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.LEAD.ordinal());
	private static ItemStack oreUranium = new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.URANIUM.ordinal());
	private static ItemStack oreHafnium = new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.HAFNIUM.ordinal());
	
	private static ItemStack copper = new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.COPPER.ordinal());
	private static ItemStack tin = new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.TIN.ordinal());
	private static ItemStack aluminium = new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.ALUMINIUM.ordinal());
	private static ItemStack lead = new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.LEAD.ordinal());
	private static ItemStack uranium = new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.URANIUM.ordinal());
	private static ItemStack hafnium = new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.HAFNIUM.ordinal());
	
	private static ItemStack dustCopper = new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.COPPER.ordinal());
	private static ItemStack dustTin = new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.TIN.ordinal());
	private static ItemStack dustAluminium = new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.ALUMINIUM.ordinal());
	private static ItemStack dustLead = new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.LEAD.ordinal());
	private static ItemStack dustUranium = new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.URANIUM.ordinal());
	private static ItemStack dustHafnium = new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.HAFNIUM.ordinal());
	
	private static ItemStack dustIron = new ItemStack(NEItems.itemDust,1,6);
	private static ItemStack dustGold = new ItemStack(NEItems.itemDust,1,7);
	public static void register()
	{
		
		GameRegistry.addSmelting(oreCopper, copper, 0.7f);
		GameRegistry.addSmelting(oreTin, tin, 0.7f);
		GameRegistry.addSmelting(oreAluminium, aluminium, 0.7f);
		GameRegistry.addSmelting(oreLead, lead, 0.7f);
		GameRegistry.addSmelting(oreUranium, uranium, 0.7f);
		GameRegistry.addSmelting(oreHafnium, hafnium, 0.7f);
		
		GameRegistry.addSmelting(dustCopper, copper, 0.7f);
		GameRegistry.addSmelting(dustTin, tin, 0.7f);
		GameRegistry.addSmelting(dustAluminium, aluminium, 0.7f);
		GameRegistry.addSmelting(dustLead, lead, 0.7f);
		GameRegistry.addSmelting(dustUranium, uranium, 0.7f);
		GameRegistry.addSmelting(dustHafnium, hafnium, 0.7f);
		GameRegistry.addSmelting(dustIron, new ItemStack(Items.IRON_INGOT), 0.7f);
		GameRegistry.addSmelting(dustGold, new ItemStack(Items.GOLD_INGOT), 0.7f);
	}
	
	
}
