package lach_01298.nuclear_engineering.tile.machine;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lach_01298.nuclear_engineering.item.RegisterItems;
import lach_01298.nuclear_engineering.item.materials.ItemUpgrade;
import lach_01298.nuclear_engineering.recipes.RecipeManagerGrinder;
import lach_01298.nuclear_engineering.recipes.RecipeManagerIso;
import lach_01298.nuclear_engineering.tile.TileEntityNE;
import lach_01298.nuclear_engineering.util.UtilItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityIso extends TileEntityNE implements ITickable
{

	public TileEntityIso(int size)
	{
		super(size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}
	
	

	
}