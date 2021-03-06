package model.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.data.District;
import model.data.ProductOffer;
import model.data.TechImprovement;
import model.data.Technology;
import util.HashMapUtil;
import util.Output;
import application.Config;

/**
 * Singleton manager that keeps track of which products are still allowed.
 * @author Roland van der Linden
 *
 */
public class AllowedProductManager
{
	private static AllowedProductManager instance;
	
	private HashMap<ProductOffer, Integer> allowedD1Map, allowedD2Map, allowedD3Map;
	private Technology[] d1Order, d2Order, d3Order;
	
	private AllowedProductManager()
	{
		super();
		
		initializeMaps();
		initializeOrders();
	}
	
	public static AllowedProductManager getInstance()
	{
		if(instance == null)
			instance = new AllowedProductManager();
		
		return instance;
	}
	
	/**
	 * Initialize the allowed productmaps.
	 */
	private void initializeMaps()
	{
		this.allowedD1Map = new HashMap<ProductOffer, Integer>();
		this.allowedD2Map = new HashMap<ProductOffer, Integer>();
		this.allowedD3Map = new HashMap<ProductOffer, Integer>();
		
		District district = DistrictManager.getInstance().getDistrictByName(Config.districtNames[0]);
		fillMap(allowedD1Map, district, 0, 2, 2, 2, 3, 5, 1, 1, 2);
		district = DistrictManager.getInstance().getDistrictByName(Config.districtNames[1]);
		fillMap(allowedD2Map, district, 2, 7, 0, 1, 3, 0, 2, 2, 0);
		district = DistrictManager.getInstance().getDistrictByName(Config.districtNames[2]);
		fillMap(allowedD3Map, district, 1, 0, 4, 2, 0, 2, 1, 0, 7);
	}
	
	private void initializeOrders()
	{
		Technology techA = ProductManager.getInstance().getTechnologyByName(Config.technologyNames[0]);
		Technology techB = ProductManager.getInstance().getTechnologyByName(Config.technologyNames[1]);
		Technology techC = ProductManager.getInstance().getTechnologyByName(Config.technologyNames[2]);
		
		this.d1Order = fillArray(techB, techC, techA);
		this.d2Order = fillArray(techA, techC, techB);
		this.d3Order = fillArray(techC, techA, techB);
	}
	
	/**
	 * This fills the maps from the horizontal rows of the table productX, productX + improv1, productX + improv2.
	 * @param map
	 * @param pa1
	 * @param pa2
	 * @param pa3
	 * @param pb1
	 * @param pb2
	 * @param pb3
	 * @param pc1
	 * @param pc2
	 * @param pc3
	 */
	private void fillMap(HashMap<ProductOffer, Integer> map, District district, int pa1, int pa2, int pa3, int pb1, int pb2, int pb3, int pc1, int pc2, int pc3)
	{
		Technology techA = ProductManager.getInstance().getTechnologyByName(Config.technologyNames[0]);
		Technology techB = ProductManager.getInstance().getTechnologyByName(Config.technologyNames[1]);
		Technology techC = ProductManager.getInstance().getTechnologyByName(Config.technologyNames[2]);
		TechImprovement improvA = ProductManager.getInstance().getImprovementByName(Config.improvementNames[0]);
		TechImprovement improvB = ProductManager.getInstance().getImprovementByName(Config.improvementNames[1]);
		
		map.put(new ProductOffer(ProductManager.getInstance().getProductByContent(techA), district), pa1);
		map.put(new ProductOffer(ProductManager.getInstance().getProductByContent(techA, improvA), district), pa2);
		map.put(new ProductOffer(ProductManager.getInstance().getProductByContent(techA, improvB), district), pa3);
		map.put(new ProductOffer(ProductManager.getInstance().getProductByContent(techB), district), pb1);
		map.put(new ProductOffer(ProductManager.getInstance().getProductByContent(techB, improvA), district), pb2);
		map.put(new ProductOffer(ProductManager.getInstance().getProductByContent(techB, improvB), district), pb3);
		map.put(new ProductOffer(ProductManager.getInstance().getProductByContent(techC), district), pc1);
		map.put(new ProductOffer(ProductManager.getInstance().getProductByContent(techC, improvA), district), pc2);
		map.put(new ProductOffer(ProductManager.getInstance().getProductByContent(techC, improvB), district), pc3);
	}
	
	private Technology[] fillArray(Technology... techs)
	{
		return techs;
	}
	
	/**
	 * This function calculates which productoffers are accepted and rejected, and returns both maps in a list.
	 * @param district
	 * @return
	 */
	public ArrayList<HashMap<ProductOffer, Integer>> calculateAcceptedAndRejectedRoundOffersForDistrict(int district)
	{
		HashMap<ProductOffer, Integer> accepted = new HashMap<ProductOffer, Integer>();
		HashMap<ProductOffer, Integer> rejected = new HashMap<ProductOffer, Integer>();
		
		//Get all the offers for the round of the given district.
		HashMap<ProductOffer, Integer> roundOffersMap = TeamManager.getInstance().getRoundOfferMapForDistrict(district);
		//Get all the offers that are still allowed based on accepted and order.
		HashMap<ProductOffer, Integer> allowedOffersMap = AllowedProductManager.getInstance().getAllowedProductOffersOfDistrict(district);
		//Order the offered round products on least amount of improvements.
		ArrayList<Entry<ProductOffer, Integer>> orderedRoundOfferEntries = HashMapUtil.sortEntriesOnAmountOfImprovements(HashMapUtil.deepClone(roundOffersMap));
		
		//For each entry, first try to fill the products with the least improvements, and then try to fill it in. 
		//Add the number of rejected and accepted to the correct map.
		for(Entry<ProductOffer, Integer> entry : orderedRoundOfferEntries)
		{
			ProductOffer roundOffer = entry.getKey();
			int offeredAmount = entry.getValue();
			
			//No improvements
			if(roundOffer.getProduct().getImprovements().length == 0)
			{
				//We only need to look at the technology product how much is allowed.
				int rejectedAmount = subtractAndCalculateRest(allowedOffersMap, roundOffer, offeredAmount);
				int acceptedAmount = offeredAmount - rejectedAmount;
				if(rejectedAmount > 0)
					HashMapUtil.addToHashMap(rejected, roundOffer, rejectedAmount);
				if(acceptedAmount > 0)
					HashMapUtil.addToHashMap(accepted, roundOffer, acceptedAmount);
			}
			
			//One improvement
			else if(roundOffer.getProduct().getImprovements().length == 1)
			{
				//We should first try out whether it can be subtracted from the technology product, and then the rest from the improvement product.
				ProductOffer techOffer = new ProductOffer(ProductManager.getInstance().getProductByContent(roundOffer.getProduct().getTechnology()), roundOffer.getDistrict());
				int rejectedAmount1 = subtractAndCalculateRest(allowedOffersMap, techOffer, offeredAmount);
				int totalRejectedAmount = rejectedAmount1;
				int totalAcceptedAmount = offeredAmount - totalRejectedAmount;
				
				//Also do it for the offer with the improvement, since we are not done yet.
				if(rejectedAmount1 > 0)
				{
					int rejectedAmount2 = subtractAndCalculateRest(allowedOffersMap, roundOffer, rejectedAmount1);
					totalRejectedAmount = rejectedAmount2;
					totalAcceptedAmount = offeredAmount - totalRejectedAmount;
				}
				if(totalRejectedAmount > 0)
					HashMapUtil.addToHashMap(rejected, roundOffer, totalRejectedAmount);
				if(totalAcceptedAmount > 0)
					HashMapUtil.addToHashMap(accepted, roundOffer, totalAcceptedAmount);
			}
			
			//Two improvements.
			else if(roundOffer.getProduct().getImprovements().length == 2)
			{
				//We should first try to subtract it from the technology product, and then the rest from the improvement product.
				ProductOffer techOffer = new ProductOffer(ProductManager.getInstance().getProductByContent(roundOffer.getProduct().getTechnology()), roundOffer.getDistrict());
				int rejectedAmount1 = subtractAndCalculateRest(allowedOffersMap, techOffer, offeredAmount);
				int totalRejectedAmount = rejectedAmount1;
				int totalAcceptedAmount = offeredAmount - totalRejectedAmount;
				
				//Also do it for the offer with the first improvement.
				if(rejectedAmount1 > 0)
				{
					ProductOffer improv1Offer = new ProductOffer(ProductManager.getInstance().getProductByContent(roundOffer.getProduct().getTechnology(), roundOffer.getProduct().getImprovements()[0]), roundOffer.getDistrict());
					int rejectedAmount2 = subtractAndCalculateRest(allowedOffersMap, improv1Offer, rejectedAmount1);
					totalRejectedAmount = rejectedAmount2;
					totalAcceptedAmount = offeredAmount - totalRejectedAmount;
					
					//Now try it with the second improvement.
					if(rejectedAmount2 > 0)
					{
						ProductOffer improv2Offer = new ProductOffer(ProductManager.getInstance().getProductByContent(roundOffer.getProduct().getTechnology(), roundOffer.getProduct().getImprovements()[1]), roundOffer.getDistrict());
						int rejectedAmount3 = subtractAndCalculateRest(allowedOffersMap, improv2Offer, rejectedAmount2);
						totalRejectedAmount = rejectedAmount3;
						totalAcceptedAmount = offeredAmount - totalRejectedAmount;
					}
				}
				if(totalRejectedAmount > 0)
					HashMapUtil.addToHashMap(rejected, roundOffer, totalRejectedAmount);
				if(totalAcceptedAmount > 0)
					HashMapUtil.addToHashMap(accepted, roundOffer, totalAcceptedAmount);
			}
			
			else 
				throw new UnsupportedOperationException();
		}

		//Insert the accepted and rejected maps to the list.
		ArrayList<HashMap<ProductOffer, Integer>> result = new ArrayList<HashMap<ProductOffer, Integer>>(2);
		result.add(accepted);
		result.add(rejected);
		return result;
	}
	
	
	/**
	 * This method calculates a map of the number of productoffers that are still allowed, based on:
	 * - The initial amounts of allowed offers.
	 * - The accepted amounts of allowed offers.
	 * - The order in which offers should occur.
	 * - The fact that products with improvements can be a stand in for products without improvements.
	 * 
	 * @param district
	 * @return
	 */
	public HashMap<ProductOffer, Integer> getAllowedProductOffersOfDistrict(int district)
	{
		HashMap<ProductOffer, Integer> initiallyAllowed = getAllowedMap(district);
		HashMap<ProductOffer, Integer> alreadyAccepted = TeamManager.getInstance().getAcceptedOfferMapForDistrict(district);
		
		//Subtract the alreadyAccepted from the initiallyAllowed.
		HashMap<ProductOffer, Integer> allowedAfterSubtraction = subtractAcceptedOffers(initiallyAllowed, alreadyAccepted);
		
		//Remove things that are not allowed yet due to the order.
		HashMap<ProductOffer, Integer> allowedAfterOrder = reduceOrderOffers(district, allowedAfterSubtraction);
		
		return allowedAfterOrder;
	}
	
	/**
	 * This will subtract all the offers of B from A, but in such a way that the offers with improvements are first subtracted from the offers without improvements (as those are just bonus).
	 * @param A
	 * @param B
	 * @return
	 */
	private HashMap<ProductOffer, Integer> subtractAcceptedOffers(HashMap<ProductOffer, Integer> A, HashMap<ProductOffer, Integer> B)
	{
		HashMap<ProductOffer, Integer> result = HashMapUtil.deepClone(A);
		ArrayList<Entry<ProductOffer, Integer>> sortedEntries = HashMapUtil.sortEntriesOnAmountOfImprovements(B);
		
		//In the first run we just subtract offers that can be subtracted directly. 
		//Note that we always remove the offers that have the least amount of matching improvements.
		for(Entry<ProductOffer, Integer> acceptedEntry : sortedEntries)
		{
			ProductOffer offer = acceptedEntry.getKey();
			int acceptedAmount = acceptedEntry.getValue();
			
			//No improvements
			if(offer.getProduct().getImprovements().length == 0)
			{
				//We just need to subtract this from the technology product.
				int stillToSubtract = subtractAndCalculateRest(result, offer, acceptedAmount);
				if(stillToSubtract > 0)
					throw new UnsupportedOperationException();
			}
			
			//One improvement
			else if(offer.getProduct().getImprovements().length == 1)
			{
				//We should first try to subtract it from the technology product, and then the rest from the improvement product.
				ProductOffer techOffer = new ProductOffer(ProductManager.getInstance().getProductByContent(offer.getProduct().getTechnology()), offer.getDistrict());
				int stillToSubtract1 = subtractAndCalculateRest(result, techOffer, acceptedAmount);
				
				//Also do it for the offer with the improvement, since we are not done yet.
				if(stillToSubtract1 > 0)
				{
					int stillToSubtract2 = subtractAndCalculateRest(result, offer, stillToSubtract1);
					if(stillToSubtract2 > 0)
						throw new UnsupportedOperationException();
				}
			}
			
			//Two improvements.
			else if(offer.getProduct().getImprovements().length == 2)
			{
				//We should first try to subtract it from the technology product, and then the rest from the improvement product.
				ProductOffer techOffer = new ProductOffer(ProductManager.getInstance().getProductByContent(offer.getProduct().getTechnology()), offer.getDistrict());
				int stillToSubtract1 = subtractAndCalculateRest(result, techOffer, acceptedAmount);
				
				//Also do it for the offer with the first improvement.
				if(stillToSubtract1 > 0)
				{
					ProductOffer improv1Offer = new ProductOffer(ProductManager.getInstance().getProductByContent(offer.getProduct().getTechnology(), offer.getProduct().getImprovements()[0]), offer.getDistrict());
					int stillToSubtract2 = subtractAndCalculateRest(result, improv1Offer, stillToSubtract1);
					
					//Now try it with the second improvement.
					if(stillToSubtract2 > 0)
					{
						ProductOffer improv2Offer = new ProductOffer(ProductManager.getInstance().getProductByContent(offer.getProduct().getTechnology(), offer.getProduct().getImprovements()[1]), offer.getDistrict());
						int stillToSubtract3 = subtractAndCalculateRest(result, improv2Offer, stillToSubtract2);
						if(stillToSubtract3 > 0)
							throw new UnsupportedOperationException();
					}
				}
			}
			
			else 
				throw new UnsupportedOperationException();
		}
		
		return result;
	}
	
	/**
	 * This function returns the map of offers that are still allowed after taking into account the order of the districts.
	 * @param allowedMap
	 * @return
	 */
	private HashMap<ProductOffer, Integer> reduceOrderOffers(int district, HashMap<ProductOffer, Integer> allowedMap)
	{
		Technology[] orderOfTechnologies = this.getTechnologyOrderByIndex(district);
		
		//Add the type of technologies to the list that are allowed to be offered.
		ArrayList<Technology> allowedTechnologies = new ArrayList<Technology>(orderOfTechnologies.length);
		for(int i = 0; i < orderOfTechnologies.length; i++)
		{
			//The first one is always allowed.
			if(i == 0)
				allowedTechnologies.add(orderOfTechnologies[i]);
			else
			{
				//If the previous tech version was fully filled, this one is also allowed.
				ProductOffer previousTechOffer = new ProductOffer(ProductManager.getInstance().getProductByContent(orderOfTechnologies[i - 1]), DistrictManager.getInstance().getDistrictByID(district));
				int allowedOffers = allowedMap.get(previousTechOffer);
				if(allowedOffers == 0)
					allowedTechnologies.add(orderOfTechnologies[i]);
				else
					break;
			}
		}
		
		//Reduce the offers that are not yet allowed to zero.
		HashMap<ProductOffer, Integer> result = new HashMap<ProductOffer, Integer>();
		for(Entry<ProductOffer, Integer> entry : allowedMap.entrySet())
		{
			ProductOffer po = entry.getKey();
			int allowedAmount = entry.getValue();
			
			if(allowedTechnologies.contains(po.getProduct().getTechnology()))
				result.put(po, allowedAmount);
			else
				result.put(po, 0);		
		}
		
		return result;
	}
	
	/**
	 * This function subtracts the actual amount of accepted offers of the given type from the map. 
	 * It may happen that not all can be subtracted, in which case it returns the number that should still be subtracted.
	 * @param allowedMap
	 * @param offer
	 * @param acceptedAmount
	 * @return
	 */
	private int subtractAndCalculateRest(HashMap<ProductOffer, Integer> allowedMap, ProductOffer offer, int acceptedAmount)
	{
		int result = 0;
		
		int allowedAmount = allowedMap.get(offer);
		int subtracted = allowedAmount - acceptedAmount;
		if(subtracted < 0)
		{
			result = Math.abs(subtracted);
			subtracted = 0;
		}
		allowedMap.put(offer,  subtracted);
		
		return result;
	}
	
	private HashMap<ProductOffer, Integer> getAllowedMap(int districtIndex)
	{
		if(districtIndex == 0)
			return this.allowedD1Map;
		else if(districtIndex == 1)
			return this.allowedD2Map;
		else if(districtIndex == 2)
			return this.allowedD3Map;
		else
			throw new UnsupportedOperationException();
	}
	
	/**
	 * Returns the technology order of the given district.
	 * @param districtIndex
	 * @return
	 */
	private Technology[] getTechnologyOrderByIndex(int districtIndex)
	{
		if(districtIndex == 0)
			return this.d1Order;
		else if(districtIndex == 1)
			return this.d2Order;
		else if(districtIndex == 2)
			return this.d3Order;
		else
			throw new UnsupportedOperationException();
	}
}
