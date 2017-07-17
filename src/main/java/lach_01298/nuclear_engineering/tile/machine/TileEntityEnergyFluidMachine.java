package lach_01298.nuclear_engineering.tile.machine;

import java.util.ArrayList;
import java.util.List;

import lach_01298.nuclear_engineering.fluid.NETank;
import lach_01298.nuclear_engineering.item.RegisterItems;
import lach_01298.nuclear_engineering.recipes.IRecipeManagerNE;
import lach_01298.nuclear_engineering.tile.ITankTile;
import lach_01298.nuclear_engineering.util.UtilItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityEnergyFluidMachine extends TileEntityEnergyMachine implements ITickable, ITankTile
{
	List<NETank> tanks;
	
	public TileEntityEnergyFluidMachine(int inputSlotSize, int outputSlotSize, int upgradeSlotSize, int energyCapacity, int baseRunningEnergy, int processTime,int tanksize, IRecipeManagerNE recipeManager )
	{
		super(inputSlotSize, outputSlotSize , upgradeSlotSize, energyCapacity, baseRunningEnergy, processTime, recipeManager);
		tanks = new ArrayList<NETank>();
	}

	
	public NETank getTank(int id)
	{
		return tanks.get(id);
	}
	
}
