package lach_01298.nuclear_engineering.container.machine;



import javax.annotation.Nullable;

import lach_01298.nuclear_engineering.container.ContainerNE;
import lach_01298.nuclear_engineering.inventory.EnumSlotType;
import lach_01298.nuclear_engineering.inventory.NESlot;
import lach_01298.nuclear_engineering.recipes.RecipeManagerGrinder;
import lach_01298.nuclear_engineering.tile.machine.TileEntityGrinder;
import lach_01298.nuclear_engineering.tile.machine.TileEntityIso;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerIso extends ContainerNE
{

	private TileEntityIso te;
	private int energy;
	private int work;
	private int speed;
	private int runningEnergy;

	public ContainerIso(IInventory playerInventory, TileEntityIso te)
	{
		
		super(playerInventory, te);
		this.te = te;
		this.energy =te.getEnergyStored(EnumFacing.DOWN);
		this.work = te.getWork();
		this.speed =te.getSpeed();
		this.runningEnergy = te.getRunningEnergy();
		
		// This container references items out of our own inventory (the 9 slots
		// we hold ourselves)
		// as well as the slots from the player inventory so that the user can
		// transfer items between
		// both inventories. The two calls below make sure that slots are
		// defined for both inventories.
		addOwnSlots();
		addPlayerSlots(playerInventory);
	}

	private void addPlayerSlots(IInventory playerInventory)
	{
		// Slots for the main inventory
		int xOffset = 8;
		int yOffset = 119;
		int slotWidth = 18;
		int slotHieght = 18;
		for(int row = 0; row < 3; ++row)
		{
			for(int col = 0; col < 9; ++col)
			{
				int x = col * slotWidth + xOffset;
				int y = row * slotHieght + yOffset;
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

	private void addOwnSlots()
	{
		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

		addSlotToContainer(new NESlot(itemHandler, 0, 53, 16, EnumSlotType.INPUT, RecipeManagerGrinder.instance()));
		addSlotToContainer(new NESlot(itemHandler, 1, 99, 16, EnumSlotType.INPUT, RecipeManagerGrinder.instance()));
		
		itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
		addSlotToContainer(new NESlot(itemHandler, 0, 53, 89,EnumSlotType.OUTPUT , null));
		addSlotToContainer(new NESlot(itemHandler, 1, 99, 89,EnumSlotType.OUTPUT , null));
		
		itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new NESlot(itemHandler, 0, 132, 7, EnumSlotType.UPGRADE, null));
		addSlotToContainer(new NESlot(itemHandler, 1, 150, 7, EnumSlotType.UPGRADE, null));
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

			if(index < TileEntityGrinder.SIZE)
			{
				if(!this.mergeItemStack(itemstack1, TileEntityGrinder.SIZE, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemstack1, 0, TileEntityGrinder.SIZE, false))
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
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.energy != this.te.getEnergyStored(EnumFacing.DOWN)|| this.energy == this.te.getMaxEnergyStored(EnumFacing.DOWN))
            {
                icontainerlistener.sendProgressBarUpdate(this, 0, this.te.getEnergyStored(EnumFacing.DOWN));
            }

            if (this.work != this.te.getWork())
            {
                icontainerlistener.sendProgressBarUpdate(this, 1, this.te.getWork());
            }
            
            if (this.speed != this.te.getSpeed())
            {
                icontainerlistener.sendProgressBarUpdate(this, 3, this.te.getSpeed());
            }

            if (this.runningEnergy != this.te.getRunningEnergy())
            {
                icontainerlistener.sendProgressBarUpdate(this, 2, this.te.getRunningEnergy());
            } 
        }
        this.energy = this.te.getEnergyStored(EnumFacing.DOWN);
        this.work = this.te.getWork();
        this.speed = this.te.getSpeed();
        this.runningEnergy = this.te.getRunningEnergy();
    }

	 @SideOnly(Side.CLIENT)
	    public void updateProgressBar(int id, int data)
	    {
	        this.te.setField(id, data);
	    }
	
	
}