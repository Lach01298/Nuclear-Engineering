package lach_01298.nuclear_engineering.container.machine;



import javax.annotation.Nullable;

import lach_01298.nuclear_engineering.inventory.EnumSlotType;
import lach_01298.nuclear_engineering.inventory.NESlot;
import lach_01298.nuclear_engineering.recipes.RecipeManagerGrinder;
import lach_01298.nuclear_engineering.tile.machine.TileEntityGrinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
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
import net.minecraftforge.items.SlotItemHandler;

public class ContainerGrinder extends ContainerEnergyMachine
{

	private TileEntityGrinder te;

	private static final int playerInvX = 8;
	private static final int playerInvY = 84;
	private static final int slotWidth = 18;
	
	
	public ContainerGrinder(IInventory playerInventory, TileEntityGrinder te)
	{
		super(playerInventory, te, playerInvX, playerInvY, slotWidth);
		this.te = te;
		addOwnSlots();
		
		
		
	
	}


	private void addOwnSlots()
	{
		
		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

		addSlotToContainer(new NESlot(itemHandler, 0, 56, 34, EnumSlotType.INPUT, RecipeManagerGrinder.instance()));
		
		itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
		addSlotToContainer(new NESlot(itemHandler, 0, 116, 35,EnumSlotType.OUTPUT , null));
		
		itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new NESlot(itemHandler, 0, 132, 7, EnumSlotType.UPGRADE, null));
		addSlotToContainer(new NESlot(itemHandler, 1, 150, 7, EnumSlotType.UPGRADE, null));
	}

	

	

}