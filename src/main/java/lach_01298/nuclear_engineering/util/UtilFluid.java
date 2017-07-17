package lach_01298.nuclear_engineering.util;

import java.util.List;

import net.minecraftforge.fluids.FluidStack;



public class UtilFluid
{
	public static boolean compareFluidStacks(FluidStack stack1, FluidStack stack2)
	{
		return stack1.isFluidEqual(stack2);
	}
	
	public static boolean compareFluidStacksWithRespectToSize(FluidStack stack1, FluidStack stack2)
	{
		return stack1.isFluidStackIdentical(stack2);
	}

	
	public static boolean compareFluidStacksLists(List<FluidStack> stacks1,List<FluidStack> stacks2)
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
						if(!stacks1.get(i).isFluidEqual(stacks2.get(i)))
						{
							return false;
						}
					}
				}
			}
			return true;
		}
	}
	public static boolean compareFluidStacksListsWithRespectToSize(List<FluidStack> stacks1,List<FluidStack> stacks2)
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
						if(!stacks1.get(i).isFluidStackIdentical(stacks2.get(i)))
						{
							return false;
						}
					}
				}
			}
			return true;
		}
	}
	
	
	public static boolean doesFluidStacksContain(List<FluidStack> stacks1,List<FluidStack> stacks2)
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
						if((stacks1.get(i).isFluidEqual(stacks2.get(i)) && (stacks1.get(i).amount >= stacks2.get(i).amount)))
						{
							return true;
						}
					}
				}
			}
			return false;
		}
	}
	
	

}
