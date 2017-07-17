package lach_01298.nuclear_engineering.network;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyFluidMachine;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMultiblockHandler implements IMessageHandler<MessageMultiblock, IMessage>
{
	public MessageMultiblockHandler()
	{
		
	}

	@Override
	public IMessage onMessage(MessageMultiblock message, MessageContext ctx)
	{
		
		int x = message.tileX;
		int y = message.tileY;
		int z = message.tileZ;
		if(ctx.side.isServer())
		{
			World world =ctx.getServerHandler().playerEntity.worldObj;
			TileEntityMultiblock tile = (TileEntityMultiblock) world.getTileEntity(new BlockPos(x,y,z));
			if(tile != null)
			{	
				tile.syncData();
			}
		}
		else
		{
			
			
			int mx = message.mTileX;
			int my = message.mTileY;
			int mz = message.mTileZ;
			
			EntityPlayer player = NuclearEngineering.proxy.getClientPlayer();
			TileEntityMultiblock tile = (TileEntityMultiblock) player.worldObj.getTileEntity(new BlockPos(x,y,z));
			if(tile != null)
			{
				
					tile.setMasterTile((TileEntityMultiblock) player.worldObj.getTileEntity(new BlockPos(mx, my, mz)));
				
			}
		}
		
		 
		return null;
	}
}