package lach_01298.nuclear_engineering.block.fluid;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

public class MaterialGas extends Material
{
	public MaterialGas(MapColor color)
	{
		super(color);
		this.setReplaceable();
		this.setNoPushMobility();
	}

	

	public boolean blocksMovement()
	{
		return false;
	}

	public boolean isSolid()
	{
		return false;
	}

	public boolean blocksLight()
	{
		return false;
	}
	
    public static final Material Gas = (new MaterialGas(MapColor.AIR)).setNoPushMobility();






}
