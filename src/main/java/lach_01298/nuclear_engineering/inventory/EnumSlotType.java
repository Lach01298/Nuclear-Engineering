package lach_01298.nuclear_engineering.inventory;

public enum EnumSlotType
{

	INPUT("input",false),
	OUTPUT("output",false),
	UPGRADE("upgrade",false),
	STORAGE("storage",false),
	FAKE("fake",true);
	
	
	private final boolean isfake;
    private final String name;
	
	private EnumSlotType(String name, boolean isfake)
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
