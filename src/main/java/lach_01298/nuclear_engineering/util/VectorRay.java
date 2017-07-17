package lach_01298.nuclear_engineering.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;



import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class VectorRay extends Vec3i
{

	private Map<IBlockState, Double> rayPathBlocks = Maps.newHashMap();
	private Coordinate tail;
	private Coordinate head;
	private World world;
	
	public VectorRay(double x, double y, double z, World world)
	{
		super(x, y, z);
		this.tail = new Coordinate(new BlockPos(0, 0, 0), Coordinate.getDimID(world));
		this.head = new Coordinate(new BlockPos(x, y, z), Coordinate.getDimID(world));
		this.world = world;
		this.rayPathBlocks = getRayBlocks();
	}

	public VectorRay(double x, double y, double z, double tailX, double tailY, double tailZ, World world)
	{
		super(x - tailX, y - tailY, z - tailZ);
		this.tail = new Coordinate(new BlockPos(tailX, tailY, tailZ), Coordinate.getDimID(world));
		this.head = new Coordinate(new BlockPos(x, y, z), Coordinate.getDimID(world));
		this.world = world;
		this.rayPathBlocks = getRayBlocks();
	}

	public VectorRay(BlockPos head, BlockPos tail, World world)
	{
		this(head.getX(), head.getY(), head.getZ(), tail.getX(), tail.getY(), tail.getZ(), world);
	}
	
	
	public List<IBlockState> getBlocksInRayPath()
	{
		List<IBlockState> blocks = new ArrayList();
		for(Entry<IBlockState, Double> path : rayPathBlocks.entrySet())
		{
			blocks.add(path.getKey());
		}
		return blocks;
	}
	
	public Map<IBlockState, Double> getBlocksInRayPathWithDistance()
	{
		return rayPathBlocks;
	}
	
	public double getLength()
	{
		return this.tail.getDistance(this.head.getPos());
	}
	
	public double getHeaddistanceToOrigin()
	{
		return this.getDistance(this.head.getPos().getX(), this.head.getPos().getY(), this.head.getPos().getZ());
	}
	
	public double getTaildistanceToOrigin()
	{
		return this.getDistance(this.tail.getPos().getX(), this.tail.getPos().getY(), this.tail.getPos().getZ());
	}
	
	public double getUnitVectorX()
	{
		if(this.getLength() == 0)
		{
			return 0;
		}
		return this.getX()/this.getLength();
	}
	
	public double getUnitVectorY()
	{
		if(this.getLength() == 0)
		{
			return 0;
		}
		return this.getY()/this.getLength();
	}
	
	public double getUnitVectorZ()
	{
		if(this.getLength() == 0)
		{
			return 0;
		}
		return this.getZ()/this.getLength();
	}
	
	private Map<IBlockState, Double> getRayBlocks()
	{
		Map<IBlockState, Double> temp = Maps.newHashMap();
		BlockPos current = this.tail.getPos();
		List<IBlockState> blocks = new ArrayList();
		for(int i = 0; i <= this.getLength()*5; i++)
		{
			double j = i*0.2;
			blocks.add(world.getBlockState(new BlockPos(current.getX()+0.5+ j*this.getUnitVectorX(),current.getY()+0.5+ j*this.getUnitVectorY(),current.getZ()+0.5+ j*this.getUnitVectorZ())));
			
		}
		
		while(!blocks.isEmpty())
		{
			IBlockState block = blocks.get(0);
			int amount = Collections.frequency(blocks, block);
			double value = (amount * 0.2);
			
			temp.put(block, value);
			blocks.removeAll(Collections.singleton(block));
		}
		return temp;
	}
	
}
