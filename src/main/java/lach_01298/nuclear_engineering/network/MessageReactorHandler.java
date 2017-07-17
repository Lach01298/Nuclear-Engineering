package lach_01298.nuclear_engineering.network;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyFluidMachine;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblock;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityReactorCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageReactorHandler implements IMessageHandler<MessageReactor, IMessage>
{
	public MessageReactorHandler()
	{
		
	}

	@Override
	public IMessage onMessage(MessageReactor message, MessageContext ctx)
	{
		
		
		
		boolean formed = message.formed;
		int tempratue = message.tempratue;
		int fuelLeft = message.fuelLeft;
		int neutronflux = message.neutronflux;
		double controlRod = message.controlRod;
		double reflector = message.reflectorFactor;
		double moderator = message.moderatorFactor;
		String fuelName = message.fuelName;
		
		int x = message.tileX;
		int y = message.tileY;
		int z = message.tileZ;
		
		EntityPlayer player = NuclearEngineering.proxy.getClientPlayer();
		TileEntityReactorCore tile = (TileEntityReactorCore) player.worldObj.getTileEntity(new BlockPos(x,y,z));
		
		if(tile != null)
		{
				
				tile.set(formed,tempratue,fuelLeft,neutronflux,controlRod,reflector,moderator,fuelName);
			
		}
		 
		return null;
	}
}