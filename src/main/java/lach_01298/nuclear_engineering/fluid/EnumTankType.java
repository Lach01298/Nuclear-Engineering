package lach_01298.nuclear_engineering.fluid;

public enum EnumTankType
{

	INPUT("input",false),
	OUTPUT("output",false),
	STORAGE("storage",false),
	FAKE("fake",true);
	
	
	private final boolean isfake;
    private final String name;
	
	private EnumTankType(String name, boolean isfake)
	{
		this.name = name;
		this.isfake = isfake;
	}
	
	
	public String getname()
	{
		return this.name;
	}
	
	public boolean getIsFake()
	{
		return this.isfake;
	}
		

}
