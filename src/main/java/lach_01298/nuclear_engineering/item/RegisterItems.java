package lach_01298.nuclear_engineering.item;

import lach_01298.nuclear_engineering.item.armour.ItemHazmatSuit;
import lach_01298.nuclear_engineering.item.materials.ItemDust;
import lach_01298.nuclear_engineering.item.materials.ItemIngot;
import lach_01298.nuclear_engineering.item.materials.ItemPlate;
import lach_01298.nuclear_engineering.item.materials.ItemRadioactiveResource;
import lach_01298.nuclear_engineering.item.materials.ItemResource;
import lach_01298.nuclear_engineering.item.materials.ItemUpgrade;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.item.tool.ItemGeigerCounter;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RegisterItems
{
	
	//materials
	public static Item itemIngot;
	public static Item itemDust;
	public static Item itemPlate;
	public static Item itemUpgrade;
	public static Item itemResource;
	public static Item itemRadioactive;
	public static Item geiger;
	public static ItemArmor hazmatHelmet;
	public static ItemArmor hazmatChest;
	public static ItemArmor hazmatLegs;
	public static ItemArmor hazmatBoots;
	
	public static void init()
	{

		
		itemIngot = registerItem(new ItemIngot("ingot"));
		itemDust = registerItem(new ItemDust("dust"));
		itemPlate = registerItem(new ItemPlate("plate"));
		itemUpgrade = registerItem(new ItemUpgrade("upgrade"));
		itemResource = registerItem(new ItemResource("resource"));
		itemRadioactive = registerItem(new ItemRadioactiveResource("radioactive"));
		geiger = registerItem(new ItemGeigerCounter());
		hazmatHelmet = registerItem(new ItemHazmatSuit("hazmatHelmet", 0, EntityEquipmentSlot.HEAD));
		hazmatChest = registerItem(new ItemHazmatSuit("hazmatChest", 0, EntityEquipmentSlot.CHEST));
		hazmatLegs = registerItem(new ItemHazmatSuit("hazmatLegs", 0, EntityEquipmentSlot.LEGS));
		hazmatBoots = registerItem(new ItemHazmatSuit("hazmatBoots", 0, EntityEquipmentSlot.FEET));
		
	}

	private static <T extends Item> T registerItem(T item)
	{
		GameRegistry.register(item);

		if (item instanceof ItemModelProvider)
		{
			
			
			
			((ItemModelProvider) item).registerItemModel(item);
		}

		return item;
	}

	
	
	
	
	
	
	
	
}
