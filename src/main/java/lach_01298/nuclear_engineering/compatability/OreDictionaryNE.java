package lach_01298.nuclear_engineering.compatability;

import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.block.blocks.BlockMetal;
import lach_01298.nuclear_engineering.block.blocks.BlockOre;
import lach_01298.nuclear_engineering.item.RegisterItems;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryNE
{
	private static RegisterBlocks NEBlocks;
	private static RegisterItems NEItems;
	
	
	public static void registerOres()
	{
		
		//ores
		OreDictionary.registerOre("oreCopper", new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.COPPER.ordinal()));
		OreDictionary.registerOre("oreTin", new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.TIN.ordinal()));
		OreDictionary.registerOre("oreAluminum", new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.ALUMINIUM.ordinal()));
		OreDictionary.registerOre("oreLead", new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.LEAD.ordinal()));
		OreDictionary.registerOre("oreUranium", new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.URANIUM.ordinal()));
		OreDictionary.registerOre("oreHafnium", new ItemStack(NEBlocks.ore,1,BlockMetal.MetalType.HAFNIUM.ordinal()));
		
		//metal blocks
		OreDictionary.registerOre("blockCopper", new ItemStack(NEBlocks.metal,1,BlockMetal.MetalType.COPPER.ordinal()));
		OreDictionary.registerOre("blockTin", new ItemStack(NEBlocks.metal,1,BlockMetal.MetalType.TIN.ordinal()));
		OreDictionary.registerOre("blockAluminum", new ItemStack(NEBlocks.metal,1,BlockMetal.MetalType.ALUMINIUM.ordinal()));
		OreDictionary.registerOre("blockLead", new ItemStack(NEBlocks.metal,1,BlockMetal.MetalType.LEAD.ordinal()));
		OreDictionary.registerOre("blockUranium", new ItemStack(NEBlocks.metal,1,BlockMetal.MetalType.URANIUM.ordinal()));
		OreDictionary.registerOre("blockHafnium", new ItemStack(NEBlocks.metal,1,BlockMetal.MetalType.HAFNIUM.ordinal()));
		
		//ingots
		OreDictionary.registerOre("ingotCopper", new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.COPPER.ordinal()));
		OreDictionary.registerOre("ingotTin", new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.TIN.ordinal()));
		OreDictionary.registerOre("ingotAluminum", new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.ALUMINIUM.ordinal()));
		OreDictionary.registerOre("ingotLead", new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.LEAD.ordinal()));
		OreDictionary.registerOre("ingotUranium", new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.URANIUM.ordinal()));
		OreDictionary.registerOre("ingotHafnium", new ItemStack(NEItems.itemIngot,1,BlockMetal.MetalType.HAFNIUM.ordinal()));
		
		// dusts
		OreDictionary.registerOre("dustCopper", new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.COPPER.ordinal()));
		OreDictionary.registerOre("dustTin", new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.TIN.ordinal()));
		OreDictionary.registerOre("dustAluminum", new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.ALUMINIUM.ordinal()));
		OreDictionary.registerOre("dustLead", new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.LEAD.ordinal()));
		OreDictionary.registerOre("dustUranium", new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.URANIUM.ordinal()));
		OreDictionary.registerOre("dustHafnium", new ItemStack(NEItems.itemDust,1,BlockMetal.MetalType.HAFNIUM.ordinal()));
	
		OreDictionary.registerOre("dustIron", new ItemStack(NEItems.itemDust,1,6));
		OreDictionary.registerOre("dustGold", new ItemStack(NEItems.itemDust,1,7));
	}
	
	

	

	
	
}
