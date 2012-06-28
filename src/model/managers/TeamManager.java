package model.managers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.data.AcceptanceData;
import model.data.District;
import model.data.ProductOffer;
import model.data.Team;
import application.Config;

/**
 * Singleton player manager.
 * @author Roland van der Linden
 *
 */
public class TeamManager
{
	private static TeamManager instance;
	protected Team[] teams;
	
	private TeamManager()
	{
		//Initiate all teams
		teams = new Team[Config.teamNames.length];
		for(int i = 0; i < Config.teamNames.length; i++)
			teams[i] = new Team(i, Config.teamNames[i], Color.pink);
	}
	
	public static TeamManager getInstance()
	{
		if(instance == null)
			instance = new TeamManager();
		
		return instance;
	}
	
	public Team getTeamByID(int ID)
	{
		for(Team t : teams)
			if(t.getID() == ID)
				return t;
		
		return null;
	}

	/**
	 * A team can also be retreived by its technology id - which should be equal to the team's id.
	 * @param techID
	 * @return
	 */
	public Team getTeamByTechnologyID(int techID)
	{
		return getTeamByID(techID);
	}
	
	public Team getTeamByName(String name)
	{
		for(Team t : teams)
			if(t.getName().equals(name))
				return t;
		
		return null;
	}
	
	public Team[] getTeams()
	{
		return teams;
	}
	
	public void resetRoundOffers()
	{
		for(Team t : teams)
			t.clearRoundOffers();
	}
	
	/**
	 * Show which offers are accepted and rejected for the given district.
	 * @param district
	 * @return
	 */
	public ArrayList<AcceptanceData> getRoundAcceptanceDataForDistrict(int district)
	{
		ArrayList<AcceptanceData> result = new ArrayList<AcceptanceData>();
		
		HashMap<ProductOffer, Integer> districtRoundOffers = getRoundOfferMapForDistrict(district);
		for(Entry<ProductOffer, Integer> entry : districtRoundOffers.entrySet())
		{
			int numberOffered = entry.getValue();
			int numberAllowed = allowedProductOfferCount(entry.getKey());
			int numberAccepted = Math.min(numberOffered, numberAllowed);
			int numberRejected = Math.max(0, numberOffered - numberAllowed);
			
			if(numberAccepted > 0)
				result.add(new AcceptanceData(entry.getKey(), numberAccepted, true));
			if(numberRejected > 0)
				result.add(new AcceptanceData(entry.getKey(), numberRejected, false));
		}
		
		return result;
	}
	
	/**
	 * Show which offers are accepted and rejected for the given team.
	 * @param team
	 * @return
	 */
	public ArrayList<AcceptanceData> getRoundAcceptanceDataForTeam(int team)
	{
		ArrayList<AcceptanceData> result = new ArrayList<AcceptanceData>();
		
		HashMap<ProductOffer, Integer> teamRoundOffers = getTeamByID(team).getRoundOffers();
		for(Entry<ProductOffer, Integer> entry : teamRoundOffers.entrySet())
		{
			ProductOffer po = entry.getKey();
			int numberOffered = entry.getValue();
			int numberAllowed = allowedProductOfferCount(po);
			int numberAccepted = Math.min(numberOffered, numberAllowed);
			int numberRejected = Math.max(0, numberOffered - numberAllowed);
			
			if(numberAccepted > 0)
				result.add(new AcceptanceData(po, numberAccepted, true));
			if(numberRejected > 0)
				result.add(new AcceptanceData(po, numberRejected, false));
		}
		
		return result;
	}
	
	/**
	 * This returns the funds gain for the given team based on the accepted data this round.
	 * @param team
	 * @return
	 */
	public int getRoundFundsChangeForTeam(int team)
	{
		int result = 0;
		
		ArrayList<AcceptanceData> teamData = getRoundAcceptanceDataForTeam(team);
		for(AcceptanceData ad : teamData)
			if(ad.accepted)
				result += (ad.count * Config.fundsGainPerAcceptedProduct);
		
		return result;
	}
	
	/**
	 * This returns the map with round offers for the given district.
	 * @param district
	 * @return
	 */
	public HashMap<ProductOffer, Integer> getRoundOfferMapForDistrict(int district)
	{
		HashMap<ProductOffer, Integer> result = new HashMap<ProductOffer, Integer>();
		
		for(Team t : teams)
		{
			HashMap<ProductOffer, Integer> temp = t.getRoundOffersForDistrict(district);
			for(Entry<ProductOffer, Integer> entry : temp.entrySet())
			{
				if(result.containsKey(entry.getKey()))
				{
					int oldCount = result.get(entry.getKey());
					int newCount = oldCount + entry.getValue();
					result.put(entry.getKey(), newCount);					
				}
				else
					result.put(entry.getKey(), entry.getValue());
			}
		}
		
		return result;
	}
	
	/**
	 * This returns the map with accepted offers for the given district.
	 * @param district
	 * @return
	 */
	public HashMap<ProductOffer, Integer> getAcceptedOfferMapForDistrict(int district)
	{
		HashMap<ProductOffer, Integer> result = new HashMap<ProductOffer, Integer>();
		
		for(Team t : teams)
		{
			HashMap<ProductOffer, Integer> temp = t.getAcceptedOffersForDistrict(district);
			for(Entry<ProductOffer, Integer> entry : temp.entrySet())
			{
				if(result.containsKey(entry.getKey()))
				{
					int oldCount = result.get(entry.getKey());
					int newCount = oldCount + entry.getValue();
					result.put(entry.getKey(), newCount);					
				}
				else
					result.put(entry.getKey(), entry.getValue());
			}
		}
		
		return result;
	}
	
	/**
	 * This tells us how much of the given product are allowed in the district of the productoffer.
	 * @param productOffer
	 * @return
	 */
	public int allowedProductOfferCount(ProductOffer productOffer)
	{
		District[] districts = DistrictManager.getInstance().getDistricts();
		
		ArrayList<HashMap<ProductOffer, Integer>> districtMaps = new ArrayList<HashMap<ProductOffer, Integer>>(districts.length);
		for(District district : districts)
		{
			HashMap<ProductOffer, Integer> districtMap = getAcceptedOfferMapForDistrict(district.getID());
			districtMaps.add(districtMap);			
		}
		
		//TODO !!!!!!!
		//TODO make sure it is actually allowed to accept this offer. Note that we need to return a number!.
		 
		return 5;
	}
}
