package lach_01298.nuclear_engineering.network;

import lach_01298.nuclear_engineering.NuclearEngineering;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NEPacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(NuclearEngineering.MOD_ID);

	public static int ID =0;
	
	public static void RegisterNetworkHandlers()
	{
		INSTANCE.registerMessage(MessageFluidHandler.class, MessageFluid.class, ID++, Side.CLIENT);
		INSTANCE.registerMessage(MessageMultiblockHandler.class, MessageMultiblock.class, ID++, Side.CLIENT);
		INSTANCE.registerMessage(MessageReactorHandler.class, MessageReactor.class, ID++, Side.CLIENT);
		INSTANCE.registerMessage(MessageMultiblockHandler.class, MessageMultiblock.class, ID++, Side.SERVER);
	}
	




}
