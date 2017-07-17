package lach_01298.nuclear_engineering.block.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class NEBlockToxicGas extends BlockFluidClassic
{
	public static DamageSource gas_toxic = new DamageSource("gas_toxic");

	public NEBlockToxicGas(Fluid fluid, Material material)
	{
		super(fluid, material);
		setRegistryName(fluid.getName());
	}

	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity)
	{
		if(!worldIn.isRemote)
		{
			if(entity instanceof EntityLivingBase)
			{
				entity.attackEntityFrom(gas_toxic, 4.0F);
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 1));
			}

		}
	}

}
