package lach_01298.nuclear_engineering.fission;

import io.netty.handler.logging.LogLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import lach_01298.nuclear_engineering.item.RegisterItems;
import lach_01298.nuclear_engineering.item.materials.ItemRadioactiveResource;
import lach_01298.nuclear_engineering.item.materials.ItemResource;
import lach_01298.nuclear_engineering.util.Log;

public class FissionFuel
{

	private String name;
	private int neutronsRelased;
	private double fissionEnergy;
	private double fuelCrossSection;
	private double startingFuelDensity;
	private ItemStack output;
	private static final List<FissionFuel> registeredfuels = new ArrayList<FissionFuel>();
	private static final Map<ItemStack,FissionFuel> fuelItems = new HashMap<ItemStack,FissionFuel>();
	
	/**
	 * *.
	 * @param neutronsRelased amount of neutrons released in a fission
	 * @param fissionEnergy energy released in a fission in 100 MeV
	 * @param fuelCrossSection
	 * @param startingFuelDensity
	 */
	public FissionFuel(String name ,int neutronsRelased,double fissionEnergy,double fuelCrossSection,double startingFuelDensity,ItemStack output)
	{
		this.name =name;
		this.neutronsRelased =neutronsRelased;
		this.fissionEnergy =fissionEnergy;
		this.fuelCrossSection =fuelCrossSection;
		this.startingFuelDensity = startingFuelDensity;
		this.output = output;
	}
	
	public static void registerFissionFuels()
	{
		addFuel("LEU", 3, 2.02, 0.1, 8, new ItemStack(RegisterItems.itemRadioactive, 1, ItemRadioactiveResource.EnumType.USED_LEU.ordinal()));
		addFuel("HEU", 3, 2.02, 0.1, 15, new ItemStack(RegisterItems.itemRadioactive, 1, ItemRadioactiveResource.EnumType.USED_HEU.ordinal()));
		addFuel("MOX", 3, 2.11, 0.11, 8, new ItemStack(RegisterItems.itemRadioactive, 1, ItemRadioactiveResource.EnumType.USED_MOX.ordinal()));
		addFuel("Pu-239", 3, 2.11, 0.11, 15, new ItemStack(RegisterItems.itemRadioactive, 1, ItemRadioactiveResource.EnumType.USED_PU.ordinal()));
		addFuel("Am-241", 3, 2.10, 0.017, 15, new ItemStack(RegisterItems.itemRadioactive, 1, ItemRadioactiveResource.EnumType.USED_AM.ordinal()));
		addFuel("Cm-245", 3, 2.55, 0.146, 15, new ItemStack(RegisterItems.itemRadioactive, 1, ItemRadioactiveResource.EnumType.USED_CM.ordinal()));
		registerFissionFuelItems();
	}
	
	public static void registerFissionFuelItems()
	{
		addFuelItem(new ItemStack(RegisterItems.itemRadioactive,1, ItemRadioactiveResource.EnumType.LEU.ordinal()),getFuel("LEU"));
		addFuelItem(new ItemStack(RegisterItems.itemRadioactive,1, ItemRadioactiveResource.EnumType.ENRICHED_URANIUM.ordinal()),getFuel("HEU"));
		addFuelItem(new ItemStack(RegisterItems.itemRadioactive,1, ItemRadioactiveResource.EnumType.MOX.ordinal()),getFuel("MOX"));
		addFuelItem(new ItemStack(RegisterItems.itemRadioactive,1, ItemRadioactiveResource.EnumType.PU_239.ordinal()),getFuel("Pu-239"));
		addFuelItem(new ItemStack(RegisterItems.itemRadioactive,1, ItemRadioactiveResource.EnumType.AM_241.ordinal()),getFuel("Am-241"));
		addFuelItem(new ItemStack(RegisterItems.itemRadioactive,1, ItemRadioactiveResource.EnumType.CM_245.ordinal()),getFuel("Cm-245"));
		
	}
	
	
	
	public static void addFuelItem(ItemStack item, FissionFuel fuel)
	{
		if(fuelItems.containsKey(item))
		{
			Log.log(Level.WARN, "Fission FuelItem "+item+" is already registered");
			return;
		}
		fuelItems.put(item, fuel);
	
	}
	
	public static FissionFuel getFuel(ItemStack stack)
	{
		if(stack != null)
		{
			ItemStack item = stack.copy();
			item.stackSize = 1;
			
			for(Entry<ItemStack, FissionFuel> entry : fuelItems.entrySet())
			{
				if(entry.getKey().isItemEqual(item))
				{
					return entry.getValue();
				}
			}
		}
		return null;
	}
	
	
	public ItemStack getFuelItem()
	{
			for(Entry<ItemStack, FissionFuel> entry : fuelItems.entrySet())
			{
				if(entry.getValue().equals(this))
				{
					return entry.getKey();
				}
			}
		return null;
	}
	
	
	
	public static void addFuel(String name ,int neutronsRelased,double fissionEnergy,double fuelCrossSection,double startingFuelDensity,ItemStack output)
	{
		for(FissionFuel fuel: registeredfuels)
		{
			if(fuel.name.equals(name))
			{
				Log.log(Level.WARN, "Fission Fuel "+name+" is already registered");
				return;
			}
		}
		registeredfuels.add(new FissionFuel(name, neutronsRelased, fissionEnergy, fuelCrossSection, startingFuelDensity,output));
		
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getNeutronsRelased()
	{
		return neutronsRelased;
	}
	
	public double getFissionEnergy()
	{
		return fissionEnergy;
	}
	
	public double getFuelCrossSection()
	{
		return fuelCrossSection;
	}
	
	public double getStartingFuelDensity()
	{
		return startingFuelDensity;
	}
	
	public ItemStack getOutput()
	{
		return output;
	}
	
	public static FissionFuel getFuel(String name)
	{
		for(FissionFuel fuel: registeredfuels)
		{
			if(fuel.name.equals(name))
			{
				return fuel;
			}
		}
		return null;
	}
}
