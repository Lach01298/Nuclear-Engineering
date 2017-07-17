package lach_01298.nuclear_engineering.inventory;

import java.util.ArrayList;
import java.util.List;

import lach_01298.nuclear_engineering.item.RegisterItems;
import lach_01298.nuclear_engineering.recipes.IRecipeManagerNE;
import lach_01298.nuclear_engineering.util.UtilItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

public class NESlot extends SlotItemHandler
{
	
	private EnumSlotType type;
	private IRecipeManagerNE manager;
	private int id;
	
	 public NESlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, EnumSlotType type, IRecipeManagerNE manager)
	    {
	        super(itemHandler, index, xPosition, yPosition);
	       this.id = index;
	        this.type =type;
	        this.manager =manager;
	     
	    }


	 public boolean isItemValid(ItemStack stack)
	    {
	       
	        return super.isItemValid(stack) && isItemAllowed(stack,this.type,this.manager);
	    }


	private boolean isItemAllowed(ItemStack itemStack, EnumSlotType type, IRecipeManagerNE manager)
	{
		switch(type)
		{
			case OUTPUT: return false;
			case UPGRADE: return UtilItem.compareItemStacks( itemStack, new ItemStack(RegisterItems.itemUpgrade, 1, 0))||UtilItem.compareItemStacks( itemStack, new ItemStack(RegisterItems.itemUpgrade, 1, 1));
			
			default:
				break;
		}
	
		if(manager == null)
		{
			return true;
		}

		return manager.isResultForItemstack(itemStack,id);
	}
	 

}




