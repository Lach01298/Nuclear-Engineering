package lach_01298.nuclear_engineering.util;

import java.util.List;

import net.minecraft.item.ItemStack;

public class UtilItem
{
	public static boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		if(stack1 == null || stack2 == null)
		{
			if(stack1 == null && stack2 == null)
			{
				return true;
			}
			return false;
		}
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public static boolean compareItemStacksWithRespectToSize(ItemStack stack1, ItemStack stack2)
	{
		if(stack1 == null || stack2 == null)
		{
			if(stack1 == null && stack2 == null)
			{
				return true;
			}
			return false;
		}
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata())&& (stack1.stackSize == stack2.stackSize);
	}

	public static ItemStack setStackSize(ItemStack stack, int size)
	{
		ItemStack newStack = stack.copy();
		newStack.stackSize = size;
		return newStack;
	}

	public static boolean compareItemStacksLists(List<ItemStack> stacks1,List<ItemStack> stacks2)
	{
		if(stacks1 == null || stacks2 == null)
		{
			return false;		
		}
		else
		{

			if(stacks1.size() != stacks2.size())
			{
				return false;
			}
			else
			{
				for(int i = 0; i < stacks1.size(); i++)
				{
					if(stacks1.get(i) == null || stacks2.get(i)== null)
					{
						if(stacks1.get(i) == null && stacks2.get(i)== null)
						{
							return true;
						}
						else
						{
							return false;
						}
					}
					else
					{
						if(!stacks1.get(i).isItemEqual(stacks2.get(i)))
						{
							return false;
						}
					}
				}
			}
			return true;
		}
	}

}
