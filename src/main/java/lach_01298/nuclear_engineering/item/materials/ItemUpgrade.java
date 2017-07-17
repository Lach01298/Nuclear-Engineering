package lach_01298.nuclear_engineering.item.materials;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;

public class ItemUpgrade extends Item implements ItemModelProvider
{

	public String Types[] = { "Speed", "Efficiency"};
	protected String name;

	public ItemUpgrade(String name)
	{
		this.name= name;
		setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
		setRegistryName(name);
		setCreativeTab(NuclearEngineering.NETab);
		setHasSubtypes(true);
		setMaxStackSize(16);
	}

	public String getUnlocalizedName(ItemStack stack)
	{
		int i = stack.getMetadata();
		return super.getUnlocalizedName() + Types[i];
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab,
			List<ItemStack> subItems)
	{
		for (int i = 0; i < Types.length; ++i)
		{
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public void registerItemModel(Item item)
	{
		for (int i=0; i < Types.length; i++)
		{
			NuclearEngineering.proxy.registerItemRenderer(this, i, name +Types[i]);
		}

	}


}
