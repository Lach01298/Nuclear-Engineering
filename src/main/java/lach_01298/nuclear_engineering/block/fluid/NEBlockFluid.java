package lach_01298.nuclear_engineering.block.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class NEBlockFluid extends BlockFluidClassic
{
	public NEBlockFluid(Fluid fluid, Material material)
	{
		super(fluid, material);
		setRegistryName(fluid.getName());
	}
}