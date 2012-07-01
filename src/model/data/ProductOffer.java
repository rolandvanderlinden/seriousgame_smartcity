package model.data;

public class ProductOffer
{
	protected Product product;
	protected District district;
	
	public ProductOffer(Product product, District district)
	{
		super();
		
		this.product = product;
		this.district = district;
	}
	
	@Override
	public int hashCode()
	{
		return (100 * product.hashCode()) + district.hashCode();
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof ProductOffer)
			return equals((ProductOffer)other);
		else
			return false;
	}
	
	public boolean equals(ProductOffer other)
	{
		return other.hashCode() == hashCode();
	}
	
	public ProductOffer clone()
	{
		return new ProductOffer(product.clone(), district.clone());
	}
	
	public Product getProduct()
	{
		return product;
	}
	
	public District getDistrict()
	{
		return district;
	}
}
