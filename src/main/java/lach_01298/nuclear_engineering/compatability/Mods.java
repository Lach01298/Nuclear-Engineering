package lach_01298.nuclear_engineering.compatability;

import lach_01298.nuclear_engineering.util.Log;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

public class Mods 
{
	public static boolean enableJEI = false;

	
	
	public static void loadMods()
	{
		
	
	
		
		
		//IC2
		
		if (Loader.isModLoaded("JEI")) {
            try 
            {
            	enableJEI = true;
          
            	Log.log(Level.INFO, "Loaded JEI fetures");
            }

            catch (Exception e) 
            {
                Log.log(Level.ERROR, "Could not load JEI fetures",e);
               
            }
        }
	
	
	
		Log.log(Level.INFO, "Loaded  mod Compatability fetures");
	
	}
	
	
	
}