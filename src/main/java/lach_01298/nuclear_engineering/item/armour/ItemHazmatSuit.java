package lach_01298.nuclear_engineering.item.armour;

import lach_01298.nuclear_engineering.NuclearEngineering;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ItemHazmatSuit extends ItemArmor
{

	
	public ItemHazmatSuit(String name,int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(ArmourMaterials.HAZMAT, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
		setRegistryName(name);
		setCreativeTab(NuclearEngineering.NETab);
	}

	
	
	
	
	
}
