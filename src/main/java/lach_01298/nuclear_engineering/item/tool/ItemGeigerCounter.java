package lach_01298.nuclear_engineering.item.tool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.world.radiation.ManagerRadiation;

public class ItemGeigerCounter extends Item implements ItemModelProvider
{
	protected String name = "itemGeiger";
	
	public ItemGeigerCounter()
	{
		setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
		setRegistryName(name);
		setCreativeTab(NuclearEngineering.NETab);
		setMaxStackSize(1);
	}
		
	
	
	
	@Override
	public void registerItemModel(Item item)
	{
		NuclearEngineering.proxy.registerItemRenderer(this, 0, name);
	}
		
	
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(!world.isRemote)
		{
			ManagerRadiation radManager = ManagerRadiation.getManager(world);
			
			float alpha = radManager.GetAlphaRadiation(pos, world);
			float beta = radManager.GetBetaRadiation(pos, world);
			float gamma = radManager.GetGammaRadiation(pos, world);
			float neutron = radManager.GetNeutronRadiation(pos, world);

			player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".radiationAlpha", String.format("%.2f", alpha)));
			player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".radiationBeta", String.format("%.2f", beta)));
			player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".radiationGamma", String.format("%.2f", gamma)));
			player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".radiationNeutron", String.format("%.2f", neutron)));
		}
		return EnumActionResult.SUCCESS;
    }
	

}
