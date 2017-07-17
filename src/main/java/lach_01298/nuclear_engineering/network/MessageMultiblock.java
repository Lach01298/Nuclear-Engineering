package lach_01298.nuclear_engineering.network;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.fluid.NETank;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyFluidMachine;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblock;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMultiblock<T extends TileEntityMultiblock> implements IMessage
{
	
	NBTTagCompound nbt;


	int tileX;
	int tileY;
	int tileZ;
	int mTileX;
	int mTileY;
	int mTileZ;
	
	// A default constructor is always required
	public MessageMultiblock()
	{
	}

	

	public MessageMultiblock(TileEntityMultiblock tile)
	{
	
	
		this.tileX = tile.getPos().getX();
		this.tileY = tile.getPos().getY();
		this.tileZ = tile.getPos().getZ();
		if(tile.hasMaster())
		{
			this.mTileX = tile.getMasterTile().getPos().getX();
			this.mTileY = tile.getMasterTile().getPos().getY();
			this.mTileZ = tile.getMasterTile().getPos().getZ();
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(tileX);
		buf.writeInt(tileY);
		buf.writeInt(tileZ);
		
		buf.writeInt(mTileX);
		buf.writeInt(mTileY);
		buf.writeInt(mTileZ);
		
		
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		tileX = buf.readInt();
		tileY = buf.readInt();
		tileZ = buf.readInt();
	
		mTileX = buf.readInt();
		mTileY = buf.readInt();
		mTileZ = buf.readInt();
	}

	
	
	
	
	
	
}
