package model.managers;

import model.data.Product;
import model.data.TechImprovement;
import model.util.ResourceInfo;
import content.product.ProductRef;

public class ProductManager
{
	private static ProductManager instance;
	
	private ProductManager()
	{

	}
	
	public static ProductManager getInstance()
	{
		if(instance == null)
			instance = new ProductManager();
		
		return instance;
	}
	
	public static ResourceInfo getProductResourceInfo(Product product)
	{
		String name = product.getTechnology().getName();
		for(TechImprovement ti : product.getImprovements())
			name += "-" + ti.getName();
		name += ".jpg";
		
		return new ResourceInfo(name, ProductRef.class);
	}
}
