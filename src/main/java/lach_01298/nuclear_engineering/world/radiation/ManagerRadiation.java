package lach_01298.nuclear_engineering.world.radiation;

import java.util.Map;

import com.google.common.collect.Maps;

import lach_01298.nuclear_engineering.util.Coordinate;
import lach_01298.nuclear_engineering.util.VectorRay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class ManagerRadiation extends WorldSavedData
{
	public static final String MANAGER_NAME = "NERadiation";
	private final Map<Coordinate, RadiationSource> radiationMap = Maps.newHashMap();
	private static ManagerRadiation instance = null;
	private final static int radiationMaxRange = 64;
	
	
	public ManagerRadiation()
	{
		super(MANAGER_NAME);
	}
	
	public ManagerRadiation(String name)
	{
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		NBTTagList radiationTag = compound.getTagList("radiation", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < radiationTag.tagCount(); i++)
		{
			NBTTagCompound tag = radiationTag.getCompoundTagAt(i);
			Coordinate coord = new Coordinate(new BlockPos(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z")), tag.getInteger("dimension"));
			RadiationSource source = new RadiationSource();
			source.readfromNBT(tag);
			radiationMap.put(coord, source);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagList radiationTag = new NBTTagList();
		for(Map.Entry<Coordinate, RadiationSource> entry : radiationMap.entrySet())
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("dimension", entry.getKey().getDimension());
			tag.setInteger("X", entry.getKey().getPos().getX());
			tag.setInteger("Y", entry.getKey().getPos().getY());
			tag.setInteger("Z", entry.getKey().getPos().getZ());
			entry.getValue().writeToNBT(tag);
			radiationTag.appendTag(tag);
		}
		compound.setTag("radiation", radiationTag);
		return compound;
	}

	
	
	
	public static ManagerRadiation getManager(World world)
	{
		if(world.isRemote)
		{
			return null;
		}
		if(instance != null)
		{
			return instance;
		}
		instance = (ManagerRadiation) world.getMapStorage().getOrLoadData(ManagerRadiation.class, MANAGER_NAME);
		if(instance == null)
		{
			instance = new ManagerRadiation(MANAGER_NAME);
			world.getMapStorage().setData(MANAGER_NAME, instance);
		}
		return instance;
	}

	public float GetAlphaRadiation(BlockPos pos, World world)
	{
		float totalRadiation = 0;
		for(Map.Entry<Coordinate, RadiationSource> entry : radiationMap.entrySet())
		{
			
			if(entry.getKey().getDimension() == Coordinate.getDimID(world))
			{
				if(entry.getKey().getDistance(pos) < radiationMaxRange)
				{
					
					totalRadiation += entry.getValue().getStrenghAlpha(new VectorRay(entry.getKey().getPos(),pos,world));
				}	
			}
		}
		
		return totalRadiation;
	}
	
	public float GetBetaRadiation(BlockPos pos, World world)
	{
		float totalRadiation = 0;
		for(Map.Entry<Coordinate, RadiationSource> entry : radiationMap.entrySet())
		{
			if(entry.getKey().getDimension() == Coordinate.getDimID(world))
			{
				if(entry.getKey().getDistance(pos) < radiationMaxRange)
				{
					totalRadiation += entry.getValue().getStrenghBeta(new VectorRay(entry.getKey().getPos(),pos,world));
				}	
			}
		}
		return totalRadiation;
	}

	public float GetGammaRadiation(BlockPos pos, World world)
	{
		float totalRadiation = 0;
		for(Map.Entry<Coordinate, RadiationSource> entry : radiationMap.entrySet())
		{
			if(entry.getKey().getDimension() == Coordinate.getDimID(world))
			{
				if(entry.getKey().getDistance(pos) < radiationMaxRange)
				{
					totalRadiation += entry.getValue().getStrenghGamma(new VectorRay(entry.getKey().getPos(),pos,world));
				}	
			}
		}
		return totalRadiation;
	}
	
	public float GetNeutronRadiation(BlockPos pos, World world)
	{
		float totalRadiation = 0;
		for(Map.Entry<Coordinate, RadiationSource> entry : radiationMap.entrySet())
		{
			if(entry.getKey().getDimension() == Coordinate.getDimID(world))
			{
				if(entry.getKey().getDistance(pos) < radiationMaxRange)
				{
					totalRadiation += entry.getValue().getStrenghNeutron(new VectorRay(entry.getKey().getPos(),pos,world));
				}	
			}
		}
		return totalRadiation;
	}
	
	
	public void addRadiationAt(BlockPos pos,World world, float alpha, float beta, float gamma, float neutron)
	{
		
		if(isRadiationSourceAt(pos, world))
		{
			RadiationSource rad = getRadiationSourceAt(pos, world);
			rad.setAlpha(rad.getAlpha()+alpha);
			rad.setBeta(rad.getBeta()+beta);
			rad.setGamma(rad.getGamma()+gamma);
			rad.setNeutron(rad.getNeutron()+neutron);
		}
		else
		{
			RadiationSource rad = new RadiationSource(alpha,beta,gamma,neutron);
			radiationMap.put(new Coordinate(pos,Coordinate.getDimID(world)), rad);
			
		}
		markDirty();
	}
	public void removeRadiationAt(BlockPos pos,World world)
	{
		radiationMap.remove(new Coordinate(pos,Coordinate.getDimID(world)));
		markDirty();
	}
	
	public boolean isRadiationSourceAt(BlockPos pos,World world)
	{
		Coordinate coord = new Coordinate(pos, Coordinate.getDimID(world));
		if(radiationMap.containsKey(coord))
		{
			return true;
		}
		
		return false;
	}
	
	public RadiationSource getRadiationSourceAt(BlockPos pos,World world)
	{
		Coordinate coord = new Coordinate(pos, Coordinate.getDimID(world));
		
		
		return radiationMap.get(coord);
	}
	
	public Map<Coordinate, RadiationSource> getRadiationMap()
	{
		return this.radiationMap;
	}
	
	public static int getMaxRadiationRange()
	{
		return radiationMaxRange;
	}
	
	
	
}
