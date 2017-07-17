package lach_01298.nuclear_engineering.util;

import lach_01298.nuclear_engineering.NuclearEngineering;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class Log
{

	public static void log(Level logLevel, String message)
	{
		LogManager.getLogger(NuclearEngineering.MOD_ID).log(logLevel, message);
	}

	public static void log(Level logLevel, String message, Object e)
	{
		LogManager.getLogger(NuclearEngineering.MOD_ID).log(logLevel, message,
				e);
	}

}
