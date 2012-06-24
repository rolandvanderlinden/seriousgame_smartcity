package model.managers;

import model.data.Product;
import model.data.TechImprovement;
import model.data.Technology;
import model.util.ResourceInfo;
import content.product.ProductRef;
import content.techimprovements.TechImprovementRef;
import content.technology.TechnologyRef;

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
	
	public static ResourceInfo getTechnologyResourceInfo(Technology technology)
	{
		String imageName = technology.getName() + ".jpg";
		
		return new ResourceInfo(imageName, TechnologyRef.class);
	}
	
	public static ResourceInfo getTechImprovementResourceInfo(TechImprovement ti)
	{
		String imageName = ti.getName() + ".jpg";
		
		return new ResourceInfo(imageName, TechImprovementRef.class);
	}
}
