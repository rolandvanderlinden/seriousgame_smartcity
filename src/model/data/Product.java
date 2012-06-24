package model.data;


public class Product
{
	protected int ID;
	protected Technology technology;
	protected TechImprovement[] improvements;
	
	public Product(int ID, Technology technology, TechImprovement... improvements)
	{
		super();

		this.ID = ID;
		this.technology = technology;
		this.improvements = improvements;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Product)
			return equals((Product)other);
		else
			return false;
	}
	
	public boolean equals(Product other)
	{
		return other.hashCode() == hashCode();
	}
	
	@Override
	public int hashCode()
	{
		return this.ID;
	}
	
	@Override
	public String toString()
	{
		String result = technology.getName();
		
		for(int i = 0; i < improvements.length; i++)
		{
			if(i == 0)
				result += " with " + improvements[i].getName();
			else
				result += " and " + improvements[i].getName();
		}
		
		return result;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public Technology getTechnology()
	{
		return this.technology;
	}
	
	public TechImprovement[] getImprovements()
	{
		return this.improvements;
	}
}
