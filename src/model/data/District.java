package model.data;

public class District
{
	protected int ID;
	protected String name;
	
	public District(int ID, String name)
	{
		super();
		
		this.ID = ID;
		this.name = name;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public String getName()
	{
		return this.name;
	}
}
