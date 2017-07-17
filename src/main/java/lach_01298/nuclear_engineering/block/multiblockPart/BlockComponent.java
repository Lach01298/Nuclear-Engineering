package lach_01298.nuclear_engineering.block.multiblockPart;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.util.Constants;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockComponent extends BlockMultiblockPart implements ItemModelProvider
{

	private String name;

	public BlockComponent(String name, Material material)
	{
	
		super(material);
		this.name = name;
		setRegistryName(name);
		setCreativeTab(NuclearEngineering.NETab);
		setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
		setHardness(Constants.HARDNESS_METAL);
		setResistance(Constants.RESISTANCE_METAL);
	}

	@Override
	public void registerItemModel(Item itemBlock)
	{
		NuclearEngineering.proxy.registerItemRenderer(itemBlock,0 , this.name);
	}

}
