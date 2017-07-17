package lach_01298.nuclear_engineering.item.armour;

import lach_01298.nuclear_engineering.NuclearEngineering;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ArmourMaterials
{

	public static final String hazmat ="hazmat";
	public static final int hazmatDurabilityFactor = 10;
	 /**
     * Holds the damage reduction (each 1 points is half a shield on gui) of each piece of armor (helmet, plate,
     * legs and boots)
     */
	public static final int[] hazmatProtection = {2,3,3,1};
	public static final int hazmatEncantability = 1;
	
	public static final ArmorMaterial HAZMAT = EnumHelper.addArmorMaterial(hazmat, NuclearEngineering.MOD_ID+hazmat,hazmatDurabilityFactor,hazmatProtection,hazmatEncantability,SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0f);

	
    
}
