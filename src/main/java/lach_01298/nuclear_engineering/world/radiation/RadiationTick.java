package lach_01298.nuclear_engineering.world.radiation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import lach_01298.nuclear_engineering.util.Coordinate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class RadiationTick
{

	private static final double alphaLevel1 = 100;
	private static final double alphaLevel2 = 500;
	private static final double alphaLevel3 = 5000;

	private static final double betaLevel1 = 500;
	private static final double betaLevel2 = 5000;
	private static final double betaLevel3 = 10000;

	private static final double gammaLevel1 = 1000;
	private static final double gammaLevel2 = 10000;
	private static final double gammaLevel3 = 20000;

	private static final double neutronLevel1 = 10;
	private static final double neutronLevel2 = 100;

	private final int updateTime = 300;
	private int timer = updateTime;

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
		{
			return;
		}

		if(timer <= 0)
		{
			radiationTick(event.world);
			timer = updateTime;
		}

		timer--;

	}

	public void radiationTick(World world)
	{

		ManagerRadiation radMan = ManagerRadiation.getManager(world);
		if(world != null)
		{

			Set<EntityLivingBase> entitylist = new HashSet();

			for(Map.Entry<Coordinate, RadiationSource> entry : radMan.getRadiationMap().entrySet())
			{
				if(world.getChunkFromBlockCoords(entry.getKey().getPos()).isLoaded())
				{ 
				
				
					entitylist.addAll(world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entry.getKey().getPos().getX()
							- (ManagerRadiation.getMaxRadiationRange() / 4), entry.getKey().getPos().getY()
							- (ManagerRadiation.getMaxRadiationRange() / 4), entry.getKey().getPos().getZ()
							- (ManagerRadiation.getMaxRadiationRange() / 4), entry.getKey().getPos().getX()
							+ (ManagerRadiation.getMaxRadiationRange() / 4), entry.getKey().getPos().getY()
							+ (ManagerRadiation.getMaxRadiationRange() / 4), entry.getKey().getPos().getZ()
							+ (ManagerRadiation.getMaxRadiationRange() / 4))));
				}
			}
			
			for(EntityLivingBase entity : entitylist)
			{
				ItemStack head = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
				ItemStack chest = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
				ItemStack legs = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
				ItemStack feet = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET);

				double protection = 1-RadiationArmourProtection.getArmourProtection(head)-RadiationArmourProtection.getArmourProtection(chest)-RadiationArmourProtection.getArmourProtection(legs)-RadiationArmourProtection.getArmourProtection(feet);
				
				
				if(radMan.GetAlphaRadiation(entity.getPosition(), world)*protection > alphaLevel1)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 301, 0));
				}
				if(radMan.GetAlphaRadiation(entity.getPosition(), world)*protection > alphaLevel2)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 301, 0));
				}
				if(radMan.GetAlphaRadiation(entity.getPosition(), world)*protection > alphaLevel3)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 301, 1));
					
				}


				if(radMan.GetBetaRadiation(entity.getPosition(), world)*protection > betaLevel1)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 301, 0));
					entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 301, 0));
				}
				if(radMan.GetBetaRadiation(entity.getPosition(), world)*protection > betaLevel2)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 301, 0));
				}
				if(radMan.GetBetaRadiation(entity.getPosition(), world)*protection > betaLevel3)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 301, 1));
				
				}

				if(radMan.GetGammaRadiation(entity.getPosition(), world)*protection > gammaLevel1)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));

				}
				if(radMan.GetGammaRadiation(entity.getPosition(), world)*protection > gammaLevel2)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 0));
					entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 0));
				}
				if(radMan.GetGammaRadiation(entity.getPosition(), world)*protection > gammaLevel3)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 2));
					entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200, 0));
				}
				
			}
			

		}
	}
}











