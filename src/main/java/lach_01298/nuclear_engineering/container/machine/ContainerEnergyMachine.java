package lach_01298.nuclear_engineering.container.machine;

import javax.annotation.Nullable;

import lach_01298.nuclear_engineering.container.ContainerNE;
import lach_01298.nuclear_engineering.inventory.EnumSlotType;
import lach_01298.nuclear_engineering.inventory.NESlot;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class ContainerEnergyMachine extends  ContainerNE 
{

	private TileEntityEnergyMachine te;
	
	private int work;
	private int speed;
	private int runningEnergy;
	private IEnergyStorage energy;

	public ContainerEnergyMachine(IInventory playerInventory, TileEntityEnergyMachine te,int playerInvX, int playerInvY, int slotWidth)
	{
		super(playerInventory, te);
		this.te = te;
		this.energy = te.getEnergyStorage();
		this.work = te.getWork();
		this.speed =te.getSpeed();
		this.runningEnergy = te.getRunningEnergy();
		
		addPlayerSlots(playerInventory, playerInvX, playerInvY, slotWidth);
	}

	private void addPlayerSlots(IInventory playerInventory, int xOffset, int yOffset, int slotWidth)
	{
		// Slots for the main inventory
		
		
		for(int row = 0; row < 3; ++row)
		{
			for(int col = 0; col < 9; ++col)
			{
				int x = col * slotWidth + xOffset;
				int y = row * slotWidth + yOffset;
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9
						+ 9, x, y));
			}
		}

		// Slots for the hotbar
		for(int row = 0; row < 9; ++row)
		{
			int x = row * slotWidth + xOffset;
			int y = 58 + yOffset;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}
	}

	

	@Nullable
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(index < te.SIZE)
			{
				if(!this.mergeItemStack(itemstack1, te.SIZE, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemstack1, 0, te.SIZE, false))
			{
				return null;
			}

			if(itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return te.canInteractWith(playerIn);
	}
	
	
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        
        
        for (int i = 0; i < this.listeners.size(); ++i)
        {
        	IContainerListener icontainerlistener = (IContainerListener) listeners.get(i);
        	
        	icontainerlistener.sendProgressBarUpdate(this, 0, te.getEnergyStorage().getEnergyStored());
        	icontainerlistener.sendProgressBarUpdate(this, 1, te.getRunningEnergy());
        	icontainerlistener.sendProgressBarUpdate(this, 2, te.getWork());
        	icontainerlistener.sendProgressBarUpdate(this, 3, te.getSpeed());
        	
        	this.energy = te.getEnergyStorage();
        	this.runningEnergy = te.getRunningEnergy();
        	this.work = te.getWork();
        	this.speed = te.getSpeed();
        }
       
    }
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        te.setField(id, data);
    }
}
