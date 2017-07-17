package lach_01298.nuclear_engineering.network;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.tile.ITankTile;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyFluidMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageFluidHandler implements IMessageHandler<MessageFluid, IMessage>
{
	public MessageFluidHandler()
	{
		
	}

	@Override
	public IMessage onMessage(MessageFluid message, MessageContext ctx)
	{
		
		FluidStack temp = new FluidStack(FluidRegistry.WATER, 1);
		temp.loadFluidStackFromNBT(message.nbt);
		
		FluidStack stack = temp.loadFluidStackFromNBT(message.nbt);
		int tankID = message.tankID;
		int x = message.tileX;
		int y = message.tileY;
		int z = message.tileZ;
		
		EntityPlayer player = NuclearEngineering.proxy.getClientPlayer();
		ITankTile tile = (ITankTile) player.worldObj.getTileEntity(new BlockPos(x,y,z));
		if(tile != null )
		{
			if( tile.getTank(tankID) != null)
			{
				tile.getTank(tankID).setFluid(stack);
			}
		}
		
		
		 
		return null;
	}
}