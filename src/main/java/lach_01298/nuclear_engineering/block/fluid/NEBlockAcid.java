package lach_01298.nuclear_engineering.block.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

public class NEBlockAcid extends BlockFluidClassic
{

	public static DamageSource acid_burn = new DamageSource("acid_burn");

	public NEBlockAcid(Fluid fluid, Material material)
	{
		super(fluid, material);
		setRegistryName(fluid.getName());
	}

	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(!worldIn.isRemote)
		{
			entityIn.attackEntityFrom(acid_burn, 4.0F);
		
		}
	}

}
