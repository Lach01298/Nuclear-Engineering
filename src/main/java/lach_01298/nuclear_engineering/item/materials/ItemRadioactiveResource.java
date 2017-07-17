package lach_01298.nuclear_engineering.item.materials;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;

public class ItemRadioactiveResource extends Item implements ItemModelProvider
{

	protected String name;

	public ItemRadioactiveResource(String name)
	{
		this.name= name;
		setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
		setRegistryName(name);
		setCreativeTab(NuclearEngineering.NETab);
		setHasSubtypes(true);
	}

	public String getUnlocalizedName(ItemStack stack)
	{
		int i = stack.getMetadata();
		return super.getUnlocalizedName() + EnumType.values()[i].getName();
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab,
			List<ItemStack> subItems)
	{
		for (EnumType type : EnumType.values())
		{
			subItems.add(new ItemStack(itemIn, 1, type.getMetadata()));
		}
	}

	@Override
	public void registerItemModel(Item item)
	{
		for (int i=0; i < EnumType.values().length; i++)
		{
			NuclearEngineering.proxy.registerItemRenderer(this, i,EnumType.values()[i].getName());
		}

	}
	  public static enum EnumType implements IStringSerializable
	    {
	       
	        DEPLETED_URANIUM("depletedUranium"),
	        ENRICHED_URANIUM("enrichedUranium"),
	        LEU("LEU"),
	        USED_HEU("usedHEU"),
	        USED_LEU("usedLEU"),
	        MOX("MOX"),
	        USED_MOX("usedMOX"),
	        PU_239("Pu_239"),
	        PU_238("Pu_238"),
	        USED_PU("used_Pu"),
	        AM_241("Am_241"),
	        USED_AM("Used_Am"),
	        CM_245("Cm_245"),
	        USED_CM("Used_Cm");
	        
	        private final String name;
	        private final String unlocalizedName;

	        private EnumType(String name)
	        {
	        
	            this.name = name;
	            this.unlocalizedName = name;
	           
	        }

	        /**
	         * Returns the EnumType's metadata value.
	         */
	        public int getMetadata()
	        {
	            return this.ordinal();
	        }

	    	@Override
			public String getName()
			{
				return this.name;
			}

	       

	       
	        public String getUnlocalizedName()
	        {
	            return this.unlocalizedName;
	        }

		

	        
	    }
}
