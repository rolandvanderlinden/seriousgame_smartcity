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
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof TechImprovement)
			return equals((TechImprovement)other);
		else
			return false;
	}
	
	public boolean equals(TechImprovement other)
	{
		return other.getID() == ID;
	}
	
	public TechImprovement clone()
	{
		return new TechImprovement(ID, new String(name));
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
