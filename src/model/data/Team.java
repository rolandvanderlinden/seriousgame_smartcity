package model.data;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;

public class Team
{
	protected int ID;
	protected String name;
	protected Color teamColor;
	protected HashMap<ProductOffer, Integer> roundOffers;
	protected HashMap<ProductOffer, Integer> acceptedOffers;
	
	public Team(int ID, String name, Color teamColor)
	{
		super();
		
		this.ID = ID;
		this.name = name;
		this.teamColor = teamColor;
		
		this.roundOffers = new HashMap<ProductOffer, Integer>();
		this.acceptedOffers = new HashMap<ProductOffer, Integer>();
	}
	
	/**
	 * This will add a new offer to the roundoffers list.
	 */
	public void addRoundOffer(ProductOffer roundOffer)
	{
		if(roundOffers.containsKey(roundOffer))
		{
			int oldTimesOffered = roundOffers.get(roundOffer);
			int newTimesOffered = oldTimesOffered + 1;
			roundOffers.put(roundOffer, newTimesOffered);
		}
		else
			roundOffers.put(roundOffer, 1);
	}
	
	/**
	 * This will add all the roundoffers to the acceptedOffers.
	 */
	public void addRoundOffersToAcceptedOffers()
	{
		for(Entry<ProductOffer, Integer> entry : roundOffers.entrySet())
		{
			ProductOffer productOffer = entry.getKey();
			
			//Add to the existing number of products
			if(acceptedOffers.containsKey(productOffer))
			{
				int oldTimesAccepted = acceptedOffers.get(productOffer);
				int newTimesAccepted = oldTimesAccepted + entry.getValue();
				acceptedOffers.put(productOffer, newTimesAccepted);
			}
			//Inser the new number of this product.
			else
				acceptedOffers.put(productOffer, entry.getValue());
		}
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
	
	public Color getTeamColor()
	{
		return teamColor;
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
