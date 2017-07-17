package lach_01298.nuclear_engineering.proxy;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.gui.GUI_ID;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy
{

	public void registerProxies()
	{

	}

	public void registerItemRenderer(Item item, int meta, String id)
	{
	}

	public EntityPlayer getClientPlayer()
	{
		return null;
	}
}
