package model.data;

public class TechImprovement
{
	protected int ID;
	protected String name;

	public TechImprovement(int ID, String name)
	{
		super();
		
		this.ID = ID;
		this.name = name;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public String getName()
	{
		return this.name;
	}
}
