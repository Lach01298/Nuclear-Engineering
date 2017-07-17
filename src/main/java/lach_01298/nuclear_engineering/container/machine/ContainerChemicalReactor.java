package lach_01298.nuclear_engineering.container.machine;

import lach_01298.nuclear_engineering.fluid.NETank;
import lach_01298.nuclear_engineering.inventory.EnumSlotType;
import lach_01298.nuclear_engineering.inventory.NESlot;
import lach_01298.nuclear_engineering.recipes.RecipeManagerChemicalReactor;
import lach_01298.nuclear_engineering.recipes.RecipeManagerGrinder;
import lach_01298.nuclear_engineering.tile.machine.TileEntityChemicalReactor;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyMachine;
import lach_01298.nuclear_engineering.tile.machine.TileEntityGrinder;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerChemicalReactor extends ContainerEnergyMachine
{

	private TileEntityChemicalReactor te;
	private static final int playerInvX = 8;
	private static final int playerInvY = 107;
	private static final int slotWidth = 18;
	
	
	public ContainerChemicalReactor(IInventory playerInventory, TileEntityChemicalReactor te)
	{
		super(playerInventory, te, playerInvX, playerInvY, slotWidth);
		this.te = te;
		
		addOwnSlots();
	}

	private void addOwnSlots()
	{
		
		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

		addSlotToContainer(new NESlot(itemHandler, 0, 53, 16, EnumSlotType.INPUT, RecipeManagerChemicalReactor.instance()));
		
		itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
		addSlotToContainer(new NESlot(itemHandler, 0, 97, 16,EnumSlotType.OUTPUT , null));
		
		itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new NESlot(itemHandler, 0, 132, 7, EnumSlotType.UPGRADE, null));
		addSlotToContainer(new NESlot(itemHandler, 1, 150, 7, EnumSlotType.UPGRADE, null));
	}
	
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        

    }
	
	
}
