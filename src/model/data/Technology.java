package model.data;

public class Technology
{
	protected int ID;
	protected String name;
	
	public Technology(int ID, String name)
	{
		super();
		
		this.ID = ID;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Technology)
			return equals((Technology)other);
		else
			return false;
	}
	
	public boolean equals(Technology other)
	{
		return other.getID() == ID;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public String getName()
	{
		return name;
	}
}
