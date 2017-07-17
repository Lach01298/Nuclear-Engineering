package lach_01298.nuclear_engineering.gui;

import lach_01298.nuclear_engineering.NuclearEngineering;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;


public class NuclearEngineeringCreativeTab extends CreativeTabs {

	public NuclearEngineeringCreativeTab() {
		super(NuclearEngineering.MOD_ID);
	}

	@Override
	public Item getTabIconItem() {
		return Items.IRON_INGOT;
	}

}