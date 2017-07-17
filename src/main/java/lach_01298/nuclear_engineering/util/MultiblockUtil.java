package lach_01298.nuclear_engineering.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MultiblockUtil
{

	/**finds the distance to the edge of the block given in a given direction */
	public int detectEdge(TileEntityMultiblock te, EnumFacing dir, IBlockState state)
	{
		World world =te.getWorld();
		BlockPos pos =te.getPos();
		boolean edgeDetected =false;
		int n = 0;
		while(!edgeDetected)
		{
			
			if(world.getBlockState(pos.add(dir.getFrontOffsetX()*n, dir.getFrontOffsetY()*n, dir.getFrontOffsetZ()*n)) != state)
			{
				edgeDetected = true;
				continue;
			}
			n++;
		}
		

			return n-1;
	}
	
	/**checks if all blocks in the given volume is the given block */
	public boolean checkBlocks(TileEntityMultiblock te, BlockPos min, BlockPos max, IBlockState state)
	{
		
		
		BlockPos cash = min;
		
		if(max.getX() < min.getX())
		{
			min = new BlockPos(max.getX(), min.getY(), min.getZ());
			max = new BlockPos(cash.getX(), max.getY(), max.getZ());
			cash = min;
		}
		if(max.getY() < min.getY())
		{
			min = new BlockPos(min.getX(), max.getY(), min.getZ());
			max = new BlockPos(max.getX(), cash.getY(), max.getZ());
			cash = min;
			
		}
		if(max.getZ() < min.getZ())
		{
			min = new BlockPos(min.getX(), min.getY(), max.getZ());
			max = new BlockPos(max.getX(), max.getY(), cash.getZ());
			cash = min;
		}

		
		int length = max.getX() - min.getX();
		int hieght = max.getY() - min.getY();
		int width = max.getZ() - min.getZ();

		World world = te.getWorld();
		for(int x = 0; x <= length; x++)
		{
			for(int y = 0; y <= hieght; y++)
			{
				for(int z = 0; z <= width; z++)
				{
					
					if(world.getBlockState(min.add(x, y, z)) != state)
					{
						return false;
					}
					if(world.getTileEntity(min.add(x, y, z)) instanceof TileEntityMultiblock)
					{
						TileEntityMultiblock tile = (TileEntityMultiblock) world.getTileEntity(min.add(x, y, z));
						tile.setMasterTile(te.getMasterTile());
					}
				}

			}

		}
		return true;

	}
	/**checks if all blocks in the given volume is the given block or other given blocks */
	public boolean checkBlocks(TileEntityMultiblock te, BlockPos min, BlockPos max, IBlockState state, List<IBlockState> filter)
	{
		filter.add(state);
		
		BlockPos cash = min;
		
		if(max.getX() < min.getX())
		{
			min = new BlockPos(max.getX(), min.getY(), min.getZ());
			max = new BlockPos(cash.getX(), max.getY(), max.getZ());
			cash = min;
		}
		if(max.getY() < min.getY())
		{
			min = new BlockPos(min.getX(), max.getY(), min.getZ());
			max = new BlockPos(max.getX(), cash.getY(), max.getZ());
			cash = min;
			
		}
		if(max.getZ() < min.getZ())
		{
			min = new BlockPos(min.getX(), min.getY(), max.getZ());
			max = new BlockPos(max.getX(), max.getY(), cash.getZ());
			cash = min;
		}

		int length = max.getX() - min.getX();
		int hieght = max.getY() - min.getY();
		int width = max.getZ() - min.getZ();

		World world = te.getWorld();
		for(int x = 0; x <= length; x++)
		{
			for(int y = 0; y <= hieght; y++)
			{
				for(int z = 0; z <= width; z++)
				{
					if(!filter.contains(world.getBlockState(min.add(x, y, z))))
					{
						return false;
					}
					if(world.getTileEntity(min.add(x, y, z)) instanceof TileEntityMultiblock)
					{
						TileEntityMultiblock tile = (TileEntityMultiblock) world.getTileEntity(min.add(x, y, z));
						tile.setMasterTile(te.getMasterTile());
					}
				}

			}

		}
		return true;

	}
	/**checks if all blocks in the given hollow box volume is the given block */
	public boolean checkBlocksBox(TileEntityMultiblock te, BlockPos min, BlockPos max, IBlockState state , int thickness)
	{
		
		BlockPos conner1 = new BlockPos(max.getX(),min.getY(),max.getZ());
		BlockPos conner2 = new BlockPos(min.getX(),max.getY(),max.getZ());
		BlockPos conner3 = new BlockPos(max.getX(),min.getY(),min.getZ());
		BlockPos conner4 = new BlockPos(min.getX(),max.getY(),min.getZ());
		
		//top
		
		if(!checkBlocks(te, conner4, max, state))		//top 
		{
			return false;
		}
		
		if(!checkBlocks(te, min, conner1, state))		//bottom
		{
			return false;
		}
		
		if(!checkBlocks(te, conner4, conner3, state))	//north
		{
			return false;
		}
		
		if(!checkBlocks(te, conner2, conner1, state))	//south
		{
			return false;
		}
		
		if(!checkBlocks(te, conner3, max, state))		//east
		{
			return false;
		}
		if(!checkBlocks(te, min, conner2, state))		//west
		{
			return false;
		}		
				
		if(thickness <=1)
		{
			return true;
		}
		
		return checkBlocksBox(te, min.add(1, 1, 1), max.add(-1, -1, -1), state, thickness -1);
	
	}
	/**checks if all blocks in the given hollow box volume is the given block or given blocks*/
	public boolean checkBlocksBox(TileEntityMultiblock te, BlockPos min, BlockPos max, IBlockState state , int thickness,  List<IBlockState> filter)
	{
		
		BlockPos conner1 = new BlockPos(max.getX(),min.getY(),max.getZ());
		BlockPos conner2 = new BlockPos(min.getX(),max.getY(),max.getZ());
		BlockPos conner3 = new BlockPos(max.getX(),min.getY(),min.getZ());
		BlockPos conner4 = new BlockPos(min.getX(),max.getY(),min.getZ());
		//top
		
		if(!checkBlocks(te,conner4, max, state, filter))		//top 
		{
			return false;
		}
		
		if(!checkBlocks(te,min, conner1, state, filter))		//bottom
		{
			return false;
		}
		
		if(!checkBlocks(te,conner4, conner3, state, filter))	//north
		{
			return false;
		}
		
		if(!checkBlocks(te,conner2, conner1, state, filter))	//south
		{
			return false;
		}
		
		if(!checkBlocks(te,conner3, max, state, filter))		//east
		{
			return false;
		}
		if(!checkBlocks(te,min, conner2, state, filter))		//west
		{
			return false;
		}		
				
		if(thickness <=1)
		{
			return true;
		}
		
		return checkBlocksBox(te,min.add(1, 1, 1), max.add(-1, -1, -1), state, thickness -1,filter);
	
	}
	/**writes a list of block positions to NBT tag */
	public NBTTagCompound writeBlockPostions(NBTTagCompound c, Collection<BlockPos> poses)
	{
		int i = 0;
		for(BlockPos pos : poses)
		{
			
			c.setIntArray(Integer.toString(i), new int[]{pos.getX(),pos.getY(),pos.getZ()});
			i++;
		}
		
		return c;
	}
	
	/**reads a list of block positions from NBT tag */
	public List<BlockPos> readBlockPostions(NBTTagCompound c)
	{
		List<BlockPos> poses = new ArrayList<BlockPos>();
		for(int i = 0; i < c.getSize(); i++)
		{
			int[] coords = c.getIntArray(Integer.toString(i));
			BlockPos pos = new BlockPos(coords[0],coords[1],coords[2]);
			poses.add(pos);
			
		}
		
		return poses;
	}
	/**finds all the blocks of certain types in a hollow box */
	public Set<BlockPos> findBlocksBox(TileEntityMultiblock te,BlockPos min, BlockPos max, List<IBlockState> states, int thickness)
	{
		Set<BlockPos> blocks = new HashSet();
		
		for(IBlockState state: states)
		{
			blocks.addAll(findBlocksBox(te,min, max, state, thickness));
		}
		
		return blocks;
	}
	
	
	/**finds all the blocks of a certain type in a hollow box */
	public Set<BlockPos> findBlocksBox(TileEntityMultiblock te,BlockPos min, BlockPos max, IBlockState state, int thickness)
	{
		BlockPos conner1 = new BlockPos(max.getX(),min.getY(),max.getZ());
		BlockPos conner2 = new BlockPos(min.getX(),max.getY(),max.getZ());
		BlockPos conner3 = new BlockPos(max.getX(),min.getY(),min.getZ());
		BlockPos conner4 = new BlockPos(min.getX(),max.getY(),min.getZ());
		
		Set<BlockPos> blocks = new HashSet();
		
		blocks.addAll(findBlock(te, conner4, max, state));		//top 
		blocks.addAll(findBlock(te, min, conner1, state));		//bottom
		blocks.addAll(findBlock(te, conner4, conner3, state));	//north
		blocks.addAll(findBlock(te, conner2, conner1, state));	//south
		blocks.addAll(findBlock(te, conner3, max, state));		//east
		blocks.addAll(findBlock(te, min, conner2, state));		//west
		
				
		if(thickness <= 1)
		{
			return blocks;
		}
		else
		{
			blocks.addAll(findBlocksBox(te,min.add(1, 1, 1), max.add(-1, -1, -1), state, thickness -1));
			return blocks;
		}

	}
	
	/**finds all the blocks of a certain type in a volume */
	public Set<BlockPos> findBlock(TileEntityMultiblock te,BlockPos min, BlockPos max, IBlockState state)
	{
		Set<BlockPos> blocks = new HashSet();
		BlockPos cash = min;
		
		if(max.getX() < min.getX())
		{
			min = new BlockPos(max.getX(), min.getY(), min.getZ());
			max = new BlockPos(cash.getX(), max.getY(), max.getZ());
			cash = min;
		}
		if(max.getY() < min.getY())
		{
			min = new BlockPos(min.getX(), max.getY(), min.getZ());
			max = new BlockPos(max.getX(), cash.getY(), max.getZ());
			cash = min;
			
		}
		if(max.getZ() < min.getZ())
		{
			min = new BlockPos(min.getX(), min.getY(), max.getZ());
			max = new BlockPos(max.getX(), max.getY(), cash.getZ());
			cash = min;
		}

		
		int length = max.getX() - min.getX();
		int hieght = max.getY() - min.getY();
		int width = max.getZ() - min.getZ();

		World world = te.getWorld();
		for(int x = 0; x <= length; x++)
		{
			for(int y = 0; y <= hieght; y++)
			{
				for(int z = 0; z <= width; z++)
				{
					if(world.getBlockState(min.add(x, y, z)) == state)
					{
						blocks.add(min.add(x, y, z));
					}
				}

			}

		}
		return blocks;

	}
	
	public boolean checkBlocksBoxCylinder(TileEntityMultiblock te, BlockPos min, BlockPos max, IBlockState state ,EnumFacing.Axis axis, int thickness,  List<IBlockState> filter)
	{
		
		BlockPos conner1 = new BlockPos(max.getX(),min.getY(),max.getZ());
		BlockPos conner2 = new BlockPos(min.getX(),max.getY(),max.getZ());
		BlockPos conner3 = new BlockPos(max.getX(),min.getY(),min.getZ());
		BlockPos conner4 = new BlockPos(min.getX(),max.getY(),min.getZ());
		//top
		boolean checkX = true;
		boolean checkY = true;
		boolean checkZ = true;
		
		switch (axis)
		{
			case X:
				checkX = false;
				break;
			case Y:
				checkY = false;
				break;
			case Z:
				checkZ = false;
				break;
			default:
				return false;	
				
		}
		
		if(checkY)
		{
			if(!checkBlocks(te,conner4, max, state, filter))		//top 
			{
				return false;
			}
			
			if(!checkBlocks(te,min, conner1, state, filter))		//bottom
			{
				return false;
			}
		}
		
		if(checkZ)
		{
			if(!checkBlocks(te, conner4, conner3, state, filter)) // north
			{
				return false;
			}

			if(!checkBlocks(te, conner2, conner1, state, filter)) // south
			{
				return false;
			}
		}
		
		if(checkX)
		{
			if(!checkBlocks(te, conner3, max, state, filter)) // east
			{
				return false;
			}
			if(!checkBlocks(te, min, conner2, state, filter)) // west
			{
				return false;
			}
		}
		
		if(thickness <=1)
		{
			return true;
		}
		
		return checkBlocksBoxCylinder(te,min.add(1, 1, 1), max.add(-1, -1, -1), state, axis, thickness -1,filter);
	
	}
	
	
	public boolean checkBlocksBoxCylinder(TileEntityMultiblock te, BlockPos min, BlockPos max, IBlockState state ,EnumFacing.Axis axis, int thickness)
	{
		
		BlockPos conner1 = new BlockPos(max.getX(),min.getY(),max.getZ());
		BlockPos conner2 = new BlockPos(min.getX(),max.getY(),max.getZ());
		BlockPos conner3 = new BlockPos(max.getX(),min.getY(),min.getZ());
		BlockPos conner4 = new BlockPos(min.getX(),max.getY(),min.getZ());
		//top
		boolean checkX = true;
		boolean checkY = true;
		boolean checkZ = true;
		
		switch (axis)
		{
			case X:
				checkX = false;
				break;
			case Y:
				checkY = false;
				break;
			case Z:
				checkZ = false;
				break;
			default:
				return false;	
				
		}
		
		if(checkY)
		{
			if(!checkBlocks(te,conner4, max, state))		//top 
			{
				return false;
			}
			
			if(!checkBlocks(te,min, conner1, state))		//bottom
			{
				return false;
			}
		}
		
		if(checkZ)
		{
			if(!checkBlocks(te, conner4, conner3, state)) // north
			{
				return false;
			}

			if(!checkBlocks(te, conner2, conner1, state)) // south
			{
				return false;
			}
		}
		
		if(checkX)
		{
			if(!checkBlocks(te, conner3, max, state)) // east
			{
				return false;
			}
			if(!checkBlocks(te, min, conner2, state)) // west
			{
				return false;
			}
		}
		
		if(thickness <=1)
		{
			return true;
		}
		
		return checkBlocksBoxCylinder(te,min.add(1, 1, 1), max.add(-1, -1, -1), state, axis, thickness -1);
	
	}
	
	
	
	public boolean isBlock(TileEntityMultiblock te, BlockPos pos, IBlockState state)
	{
		World world = te.getWorld();
		
		if(world.getBlockState(pos)== state)
		{
			if(world.getTileEntity(pos) instanceof TileEntityMultiblock)
			{
				TileEntityMultiblock tile = (TileEntityMultiblock) world.getTileEntity(pos);
				tile.setMasterTile(te.getMasterTile());
			}
			return true;
		}
		return false;
	}

	public Set<BlockPos> findBlocksBoxCylinder(TileEntityMultiblock te, BlockPos min, BlockPos max, IBlockState state,EnumFacing.Axis axis, int thickness)
	{

		BlockPos conner1 = new BlockPos(max.getX(),min.getY(),max.getZ());
		BlockPos conner2 = new BlockPos(min.getX(),max.getY(),max.getZ());
		BlockPos conner3 = new BlockPos(max.getX(),min.getY(),min.getZ());
		BlockPos conner4 = new BlockPos(min.getX(),max.getY(),min.getZ());
		
		boolean checkX = true;
		boolean checkY = true;
		boolean checkZ = true;
		
		Set<BlockPos> blocks = new HashSet();
		
		switch (axis)
		{
			case X:
				checkX = false;
				break;
			case Y:
				checkY = false;
				break;
			case Z:
				checkZ = false;
				break;
			default:
				return null;	
				
		}
		
		if(checkY)
		{
			blocks.addAll(findBlock(te,conner4, max, state));		//top 
			blocks.addAll(findBlock(te,min, conner1, state));		//bottom
		}
		
		if(checkZ)
		{
			blocks.addAll(findBlock(te, conner4, conner3, state)); // north
			blocks.addAll(findBlock(te, conner2, conner1, state)); // south

		}
		
		if(checkX)
		{
			blocks.addAll(findBlock(te, conner3, max, state)); // east
		
			blocks.addAll(findBlock(te, min, conner2, state)); // west
		
		}
		
		if(thickness <=1)
		{
			return blocks;
		}
		
		blocks.addAll(findBlocksBoxCylinder(te,min.add(1, 1, 1), max.add(-1, -1, -1), state, axis, thickness -1));
		return blocks;
	}
	
	
	
}
