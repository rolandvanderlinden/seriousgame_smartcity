package model.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.data.Product;
import model.data.TechImprovement;
import model.data.Technology;
import model.util.ResourceInfo;
import util.ArrayListUtil;
import util.Output;
import application.Config;
import content.product.ProductRef;
import content.techimprovements.TechImprovementRef;
import content.technology.TechnologyRef;

public class ProductManager
{
	private static ProductManager instance;
	protected Technology[] technologies;
	protected TechImprovement[] improvements;
	protected Product[] products;
	
	private ProductManager()
	{
		//Initiate all technologies
		technologies = new Technology[Config.technologyNames.length];
		for(int i = 0; i < Config.technologyNames.length; i++)
			technologies[i] = new Technology(i, Config.technologyNames[i]);
		
		//Initiate all improvements
		improvements = new TechImprovement[Config.improvementNames.length];
		for(int i = 0; i < Config.improvementNames.length; i++)
			improvements[i] = new TechImprovement(i, Config.improvementNames[i]);
		
		List<List<TechImprovement>> improvementPowerset = ArrayListUtil.powerset(Arrays.asList(improvements));
		
		//Initiate all products
		ArrayList<Product> tempProducts = new ArrayList<Product>();
		int productIndex = 0;
		for(int i = 0; i < technologies.length; i++)
		{
			//Add the technology without improvements.
			tempProducts.add(new Product(productIndex++, technologies[i]));
			
			//Add the technology with all possible combinations of products.
			for(List<TechImprovement> improvementSet : improvementPowerset)
				if(improvementSet.size() > 0)
					tempProducts.add(new Product(productIndex++, technologies[i], improvementSet.toArray(new TechImprovement[improvementSet.size()])));
		}
		products = tempProducts.toArray(new Product[tempProducts.size()]);
	}
	
	public static ProductManager getInstance()
	{
		if(instance == null)
			instance = new ProductManager();
		
		return instance;
	}
	
	public Technology getTechnologyByID(int ID)
	{
		for(Technology t : technologies)
			if(t.getID() == ID)
				return t;
		
		return null;
	}
	
	public TechImprovement getImprovementByID(int ID)
	{
		for(TechImprovement ti : improvements)
			if(ti.getID() == ID)
				return ti;
		
		return null;
	}
	
	public Product getProductByID(int ID)
	{
		for(Product p : products)
			if(p.getID() == ID)
				return p;
		
		return null;
	}
	
	public Technology getTechnologyByName(String name)
	{
		for(Technology t : technologies)
			if(t.getName().equals(name))
				return t;
		
		return null;
	}
	
	public TechImprovement getImprovementByName(String name)
	{
		for(TechImprovement ti : improvements)
			if(ti.getName().equals(name))
				return ti;
		
		return null;
	}
	
	public Product getProductByContent(Technology technology, TechImprovement... improvements)
	{
		for(Product p : products)
			if(p.contentEquals(technology, improvements))
				return p;
		
		return null;
	}
	
	public Technology[] getTechnologies()
	{
		return technologies;
	}
	
	public TechImprovement[] getImprovements()
	{
		return improvements;
	}
	
	public Product[] getProducts()
	{
		return products;
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
