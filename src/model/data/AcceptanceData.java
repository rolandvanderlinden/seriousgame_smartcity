package model.data;

public class AcceptanceData
{
	public ProductOffer productOffer;
	public int count;
	public boolean accepted;
	
	public AcceptanceData(ProductOffer po, int count, boolean accepted)
	{
		super();
		
		this.productOffer = po;
		this.count = count;
		this.accepted = accepted;
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(object instanceof AcceptanceData)
		{
			AcceptanceData other = (AcceptanceData)object;
			return equals(other);
		}
		
		return false;
	}
	
	public boolean equals(AcceptanceData other)
	{
		return productOffer.equals(other.productOffer) && count == other.count && accepted == other.accepted;
	}
	
	@Override
	public String toString()
	{
		return "ProductOffer = " + productOffer.toString() + ", count = " + count + ", accepted = " + accepted;
	}
}
