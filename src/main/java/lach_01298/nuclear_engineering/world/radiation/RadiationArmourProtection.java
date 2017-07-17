package lach_01298.nuclear_engineering.world.radiation;

import lach_01298.nuclear_engineering.item.RegisterItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RadiationArmourProtection
{

	private static RegisterItems NEItems;
	
	
	public static double getArmourProtection(ItemStack item)
	{
		if(item == null)
		{
			return 0;	
		}
		
		
		//Hazmat Suit
		if(item.getItem() ==NEItems.hazmatHelmet)
		{
			return 0.2;
		}	
		if(item.getItem() ==NEItems.hazmatChest)
		{
			return 0.3;
		}
		
		if(item.getItem() ==NEItems.hazmatLegs)
		{
			return 0.2;
		}
		if(item.getItem() ==NEItems.hazmatBoots)
		{
			return 0.1;
		}
		
		
		//gold armour
		if(item.getItem() ==Items.GOLDEN_HELMET)
		{
			return 0.1;
		}	
		if(item.getItem() ==Items.GOLDEN_CHESTPLATE)
		{
			return 0.2;
		}
		
		if(item.getItem() ==Items.GOLDEN_LEGGINGS)
		{
			return 0.1;
		}
		if(item.getItem() ==Items.GOLDEN_BOOTS)
		{
			return 0.05;
		}
		
		//iron armour
		if(item.getItem() ==Items.IRON_HELMET)
		{
			return 0.05;
		}	
		if(item.getItem() ==Items.IRON_CHESTPLATE)
		{
			return 0.1;
		}
		
		if(item.getItem() ==Items.IRON_LEGGINGS)
		{
			return 0.05;
		}
		if(item.getItem() ==Items.IRON_BOOTS)
		{
			return 0.025;
		}
		
		
		
		
		
		
		return 0.01;
	}
	
	
	
	
}
