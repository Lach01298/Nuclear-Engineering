package lach_01298.nuclear_engineering;

import java.io.File;

import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.block.RegisterTileEntitys;
import lach_01298.nuclear_engineering.compatability.OreDictionaryNE;
import lach_01298.nuclear_engineering.event.NEEventHandler;
import lach_01298.nuclear_engineering.fission.FissionFuel;
import lach_01298.nuclear_engineering.fluid.NEFluids;
import lach_01298.nuclear_engineering.gui.NuclearEngineeringCreativeTab;
import lach_01298.nuclear_engineering.item.RegisterItems;
import lach_01298.nuclear_engineering.network.NEPacketHandler;
import lach_01298.nuclear_engineering.proxy.CommonProxy;
import lach_01298.nuclear_engineering.proxy.GUIProxy;
import lach_01298.nuclear_engineering.recipes.RecipesChemicalReactor;
import lach_01298.nuclear_engineering.recipes.RecipesGrinder;
import lach_01298.nuclear_engineering.recipes.RecipesSmelting;
import lach_01298.nuclear_engineering.world.radiation.RadiationBlocker;

import org.apache.logging.log4j.Level;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = NuclearEngineering.MOD_ID, name = "Nuclear Engineering", version = NuclearEngineering.VERSION, acceptedMinecraftVersions = NuclearEngineering.MCVERSION, dependencies = "")
public class NuclearEngineering
{

	public static final String MOD_ID = "nuclear_engineering";
	public static final String VERSION = "1.10.2-1.0";
	public static final String MCVERSION = "1.10.2";

	// The instance of your mod that Forge uses.
	@Instance("nuclear_engineering")
	public static NuclearEngineering instance;


	//creative tab
	public static final NuclearEngineeringCreativeTab NETab = new NuclearEngineeringCreativeTab();

	
	
	
	
	
	
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "lach_01298.nuclear_engineering.proxy.ClientProxy", serverSide = "lach_01298.nuclear_engineering.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{

		proxy.registerProxies();
		RegisterItems.init();
		RegisterBlocks.init();
		NEFluids.registerFluids();
		OreDictionaryNE.registerOres();
		NEEventHandler.registerEvents();
		
		
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(NuclearEngineering.instance, new GUIProxy());
		NEPacketHandler.RegisterNetworkHandlers();
		RecipesSmelting.register();
		RecipesGrinder.register();
		RecipesChemicalReactor.register();
		RegisterTileEntitys.init();
		RadiationBlocker.registerRadiationBlockers();
		FissionFuel.registerFissionFuels();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

}