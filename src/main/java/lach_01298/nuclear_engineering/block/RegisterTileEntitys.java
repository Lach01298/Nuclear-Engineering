package lach_01298.nuclear_engineering.block;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.tile.generator.TileEntityRTG;
import lach_01298.nuclear_engineering.tile.machine.TileEntityChemicalReactor;
import lach_01298.nuclear_engineering.tile.machine.TileEntityGrinder;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityFluidHatch;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityHeatExchanger;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityItemHatch;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblock;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityPowerHatch;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityReactorController;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityReactorCore;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityTurbine;
//import lach_01298.nuclear_engineering.block.machine.TileEntityIso;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegisterTileEntitys
{

	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityGrinder.class, NuclearEngineering.MOD_ID+"."+"TileEntityGrinder");
		GameRegistry.registerTileEntity(TileEntityChemicalReactor.class, NuclearEngineering.MOD_ID+"."+"TileEntityChemicalReactor");
		GameRegistry.registerTileEntity(TileEntityRTG.class, NuclearEngineering.MOD_ID+"."+"TileEntityRTG");
		//GameRegistry.registerTileEntity(TileEntityIso.class, NuclearEngineering.MOD_ID+"."+"TileEntityIso");
		GameRegistry.registerTileEntity(TileEntityReactorCore.class, NuclearEngineering.MOD_ID+"."+"TileEntityReactorCore");
		GameRegistry.registerTileEntity(TileEntityMultiblock.class, NuclearEngineering.MOD_ID+"."+"TileEntityMultiblock");
		GameRegistry.registerTileEntity(TileEntityItemHatch.class, NuclearEngineering.MOD_ID+"."+"TileEntityItemHatch");
		GameRegistry.registerTileEntity(TileEntityFluidHatch.class, NuclearEngineering.MOD_ID+"."+"TileEntityFluidHatch");
		GameRegistry.registerTileEntity(TileEntityPowerHatch.class, NuclearEngineering.MOD_ID+"."+"TileEntityPowerHatch");
		GameRegistry.registerTileEntity(TileEntityReactorController.class, NuclearEngineering.MOD_ID+"."+"TileEntityReactorController");
		GameRegistry.registerTileEntity(TileEntityHeatExchanger.class, NuclearEngineering.MOD_ID+"."+"TileEntityHeatExchanger");
		GameRegistry.registerTileEntity(TileEntityTurbine.class, NuclearEngineering.MOD_ID+"."+"TileEntityTurbine");
	}
	
	
	
}
