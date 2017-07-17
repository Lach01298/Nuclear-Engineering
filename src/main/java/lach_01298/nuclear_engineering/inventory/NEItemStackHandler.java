package lach_01298.nuclear_engineering.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class NEItemStackHandler extends ItemStackHandler
{

	private EnumSlotType type;
	
	public NEItemStackHandler(EnumSlotType type)
    {
		super(1);
		this.type= type;
    }

    public NEItemStackHandler(int size,EnumSlotType type)
    {
        super(size);
        this.type= type;
    }

    public NEItemStackHandler(ItemStack[] stacks,EnumSlotType type)
    {
        super(stacks);
        this.type= type;
    }

public EnumSlotType getSlotType()
{
	return type;
}


public ItemStack extractItem(int slot, int amount, boolean simulate)
{
	if(this.type == EnumSlotType.OUTPUT || this.type == EnumSlotType.STORAGE )
	{
		return super.extractItem(slot, amount, simulate);
	}
	return null;
	
}

public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
{
	if(this.type == EnumSlotType.INPUT || this.type == EnumSlotType.STORAGE)
	{
		return super.insertItem(slot, stack, simulate);
	}
	return stack;
}
	
public NBTTagCompound serializeNBT()
{
	NBTTagCompound c =super.serializeNBT();
	c.setString("slotType", this.type.getname());
			
	return c;
}


public void deserializeNBT(NBTTagCompound nbt)
{
	this.type = EnumSlotType.valueOf(nbt.getString("slotType").toUpperCase());
	super.deserializeNBT(nbt);	
}

	
}
