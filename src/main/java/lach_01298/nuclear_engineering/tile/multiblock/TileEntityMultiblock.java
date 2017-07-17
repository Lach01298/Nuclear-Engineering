package lach_01298.nuclear_engineering.tile.multiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import lach_01298.nuclear_engineering.network.MessageMultiblock;
import lach_01298.nuclear_engineering.network.NEPacketHandler;
import lach_01298.nuclear_engineering.tile.TileEntityNE;

public class TileEntityMultiblock extends TileEntityNE
{

	private TileEntityMultiblock master;
	protected boolean isMultiblockFormed;
	public BlockPos masterPos;
	
	public TileEntityMultiblock()
	{
		isMultiblockFormed = false;
		
	}
	
	@SideOnly(Side.CLIENT)
	public void checkMultiBlock(EntityPlayer player)
	{
		this.isMultiblockFormed = this.isValidMultiBlock(player);
		
	}

	public boolean isMultiBlockFormed()
	{
		return this.isMultiblockFormed;
	}
	
	
	public boolean isValidMultiBlock(EntityPlayer player)
	{
	
		return false;
	}


	public boolean hasMaster()
	{
		updateTile();
		return master != null;
	}
	


	public boolean isMasterTile()
	{
		return this == master;
	}
	
	public boolean isMasterTilePos()
	{
		if(this.masterPos != null)
		{
		
			return this.pos.getX() == this.masterPos.getX()&&this.pos.getY() == this.masterPos.getY()&&this.pos.getZ() == this.masterPos.getZ();
		}
		return false;
	}
	
	public TileEntityMultiblock getMasterTile()
	{
		
		if(master == null)
		{
			
			if(masterPos == null)
			{
				return null;
			}
			master = (TileEntityMultiblock) this.getWorld().getTileEntity(masterPos);
		}
		
		
		
		return master;
	}
	
	public boolean setMasterTile(TileEntityMultiblock master)
	{
		if(master != null)
		{
			this.master = master;
			this.masterPos = this.master.getPos();
			return true;
		}
		return false;
	}
	public void removeMasterTile()
	{	
			this.master = null;
	}


	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{

		super.readFromNBT(compound);
		if(compound.hasKey("masterX"))
		{
			
			int x =compound.getInteger("masterX");	
			int y =compound.getInteger("masterY");
			int z =compound.getInteger("masterZ");
			
			this.masterPos = new BlockPos(x,y,z);
		}
		else
		{
			
			
			
			
		}
		
		
		
			
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{

		super.writeToNBT(compound);
		if(this.master != null)
		{
			compound.setInteger("masterX", this.master.getPos().getX());
			compound.setInteger("masterY", this.master.getPos().getY());
			compound.setInteger("masterZ", this.master.getPos().getZ());
			
		}
		
		return compound;
	}

	public void syncData()
	{
		MessageMultiblock message = new MessageMultiblock(this);
		NEPacketHandler.INSTANCE.sendToAll(message);
	}
	
	
	
	
	public void updateTile()
	{
		
		if(this.master == null && this.masterPos != null)
		{
			
			if(this.getWorld().getTileEntity(masterPos) instanceof TileEntityMultiblock)
			{
				this.master = (TileEntityMultiblock) this.getWorld().getTileEntity(masterPos);
			}
			
		}
		else if(this.master != null && this.master.getPos() != null)
		{
			
			if(!(this.getWorld().getTileEntity(this.master.getPos()) instanceof TileEntityMultiblockController))
			{
				
				this.removeMasterTile();
			}
		}
		
		
	}

	
}
