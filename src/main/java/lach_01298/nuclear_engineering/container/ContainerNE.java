package lach_01298.nuclear_engineering.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

import lach_01298.nuclear_engineering.tile.TileEntityNE;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyMachine;

public  abstract class ContainerNE extends Container {

    public TileEntityNE tile;

    public ContainerNE(IInventory playerInventory, TileEntityEnergyMachine te) 
    { 
    	this.tile = te;
    }

   

  

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileEntityEnergyMachine.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileEntityEnergyMachine.SIZE, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileEntityEnergyMachine.SIZE, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canInteractWith(playerIn);
    }

  
}
