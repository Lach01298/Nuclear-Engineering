package lach_01298.nuclear_engineering.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class Coordinate implements Comparable<Coordinate>
{

	private int x;
	private int y;
	private int z;
	private int dimension;

	public Coordinate(BlockPos pos, int dimesion)
	{
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.dimension = dimesion;
	}

	public float getDistance(BlockPos pos)
	{
		return (float) Math.pow((Math.pow(pos.getX()-x, 2)+Math.pow(pos.getY()-y, 2)+Math.pow(pos.getZ()-z, 2)), 0.5);
	}

	public boolean isSameDimesion(Coordinate coord)
	{
		return coord.dimension == this.dimension;
	}

	public BlockPos getPos()
	{
		return new BlockPos(this.x, this.y, this.z);
	}

	public int getDimension()
	{
		return this.dimension;
	}
	
	public static int getDimID(World world)
	{
		if(world == null)
		{
			throw new IllegalArgumentException("Cannot fetch the Dimension-ID from a null world!");
		}
		if(world.provider == null)
		{
			for(Integer i : DimensionManager.getIDs())
			{
				if(DimensionManager.getWorld(i) == world) return i;
			}
			throw new RuntimeException("Unable to determine the dimension of world: "
					+ world);
		}
		return world.provider.getDimension();
	}

	



	@Override
	public int compareTo(Coordinate o)
	{
		if(this == o)
		{
			return 0;
		}
		if(o == null || getClass() != o.getClass())
		{
			return -1;
		}

		Coordinate that = (Coordinate) o;

		if(dimension != that.dimension)
		{
			return -1;
		}
		if(x != that.x)
		{
			return -1;
		}
		if(y != that.y)
		{
			return -1;
		}
		if(z != that.z)
		{
			return -1;
		}

		return 0;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		else if(!(obj instanceof Coordinate))
		{
			return false;
		}
		else
		{
			Coordinate coord = (Coordinate) obj;
			return this.dimension != coord.getDimension() ? false
					: this.x!= coord.getPos().getX() ? false
							: (this.y != coord.getPos().getY() ? false
									: this.z == coord.getPos().getZ());
		}
	}
	public int hashCode()
    {
        return (((this.y + this.z * 31) * 31 + this.x) + this.dimension)*31;
    }
}
