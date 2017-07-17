package lach_01298.nuclear_engineering.network;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.fluid.NETank;
import lach_01298.nuclear_engineering.tile.ITankTile;
import lach_01298.nuclear_engineering.tile.TileEntityNE;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyFluidMachine;
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

public class MessageFluid<T extends TileEntity> implements IMessage
{
	
	NBTTagCompound nbt;
	int tankID;
	int tileX;
	int tileY;
	int tileZ;
	
	// A default constructor is always required
	public MessageFluid()
	{
	}

	

	public <X extends TileEntity & ITankTile> MessageFluid(FluidStack fluid,int tankID,  X tile)
	{
		nbt = new NBTTagCompound();
		
		if(fluid != null)
		{
			fluid.writeToNBT(nbt);
		}
		this.tankID = tankID;
	
		this.tileX = tile.getPos().getX();
		this.tileY = tile.getPos().getY();
		this.tileZ = tile.getPos().getZ();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(tileX);
		buf.writeInt(tileY);
		buf.writeInt(tileZ);
		
		buf.writeInt(tankID);
		ByteBufUtils.writeTag(buf, nbt);
		
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		tileX = buf.readInt();
		tileY = buf.readInt();
		tileZ = buf.readInt();
		tankID = buf.readInt();
		nbt = ByteBufUtils.readTag(buf);
		
	}

	
	
	
	
	
	
}


