package lach_01298.nuclear_engineering.event;

import lach_01298.nuclear_engineering.world.radiation.RadiationTick;
import net.minecraftforge.common.MinecraftForge;

public class NEEventHandler
{

	
	
	public static void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new RadiationTick());
	}
	
	
}
