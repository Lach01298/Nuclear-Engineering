package lach_01298.nuclear_engineering.network;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.fluid.NETank;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyFluidMachine;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblock;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityReactorCore;
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

public class MessageReactor<T extends TileEntityReactorCore> extends MessageMultiblock implements IMessage
{
	



	boolean formed;
	int tempratue;
	int fuelLeft;
	int neutronflux;
	double controlRod;
	double reflectorFactor;
	double moderatorFactor;
	String fuelName;
	
	
	// A default constructor is always required
	public MessageReactor()
	{
	}

	

	public MessageReactor(TileEntityReactorCore tile)
	{
		
		
		super(tile);
		this.formed = tile.isMultiBlockFormed();
		this.tempratue = tile.getTemprature();
		this.fuelLeft = tile.getFuelLeft();
		this.neutronflux = tile.getNeutronNumber();
		this.controlRod = tile.getControlrodLevel();
		this.reflectorFactor = tile.getReflectorFactor();
		this.moderatorFactor = tile.getModeratorFactor();
		this.fuelName = tile.getFuelName();
		
		
		
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(formed);
		buf.writeInt(tempratue);
		buf.writeInt(fuelLeft);
		buf.writeInt(neutronflux);
		buf.writeDouble(controlRod);
		buf.writeDouble(reflectorFactor);
		buf.writeDouble(moderatorFactor);
		ByteBufUtils.writeUTF8String(buf, fuelName);
		super.toBytes(buf);
		
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.formed = buf.readBoolean();
		this.tempratue = buf.readInt();
		this.fuelLeft = buf.readInt();
		this.neutronflux = buf.readInt();
		this.controlRod = buf.readDouble();
		this.reflectorFactor = buf.readDouble();
		this.moderatorFactor = buf.readDouble();
		this.fuelName = ByteBufUtils.readUTF8String(buf);
		super.fromBytes(buf);
		 
	}

	
	
	
	
	
	
}
