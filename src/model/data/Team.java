package model.data;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;

public class Team
{
	protected int ID;
	protected String name;
	protected HashMap<ProductOffer, Integer> roundOffers;
	protected HashMap<ProductOffer, Integer> acceptedOffers;
	
	public Team(int ID, String name)
	{
		super();
		
		this.ID = ID;
		this.name = name;
		
		this.roundOffers = new HashMap<ProductOffer, Integer>();
		this.acceptedOffers = new HashMap<ProductOffer, Integer>();
	}
	
	/**
	 * This will add a new offer to the roundoffers list.
	 */
	public void addRoundOffer(ProductOffer roundOffer, int quantity)
	{
		if(roundOffers.containsKey(roundOffer))
		{
			int oldTimesOffered = roundOffers.get(roundOffer);
			int newTimesOffered = oldTimesOffered + quantity;
			roundOffers.put(roundOffer, newTimesOffered);
		}
		else
			roundOffers.put(roundOffer, quantity);
	}
	
	/**
	 * Empty the roundoffers.
	 */
	public void clearRoundOffers()
	{
		roundOffers.clear();
	}
	
	/**
	 * Empty the acceptedoffers.
	 */
	public void clearAcceptedOffers()
	{
		acceptedOffers.clear();
	}
	
	public int getID()
	{
		return ID;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public HashMap<ProductOffer, Integer> getRoundOffers()
	{
		return this.roundOffers;
	}
	
	public HashMap<ProductOffer, Integer> getAcceptedOffers()
	{
		return this.acceptedOffers;
	}
	
	public HashMap<ProductOffer, Integer> getRoundOffersForDistrict(int district)
	{
		return getOffersForDistrict(roundOffers, district);
	}
	
	public HashMap<ProductOffer, Integer> getAcceptedOffersForDistrict(int district)
	{
		return getOffersForDistrict(acceptedOffers, district);	
	}
	
	private HashMap<ProductOffer, Integer> getOffersForDistrict(HashMap<ProductOffer, Integer> map, int district)
	{
		HashMap<ProductOffer, Integer> result = new HashMap<ProductOffer, Integer>(map.size());
		
		for(Entry<ProductOffer, Integer> entry : map.entrySet())
			if(entry.getKey().getDistrict().getID() == district)
				result.put(entry.getKey(), entry.getValue());
		
		return result;
	}
}
