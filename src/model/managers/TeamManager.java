package model.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.data.AcceptanceData;
import model.data.District;
import model.data.ProductOffer;
import model.data.Team;
import util.HashMapUtil;
import util.Output;
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
			teams[i] = new Team(i, Config.teamNames[i]);
	}
	
	public static TeamManager getInstance()
	{
		if(instance == null)
			instance = new TeamManager();
		
		return instance;
	}

	public void addAcceptanceDataToAcceptedOffersOfTeam(int teamID, ArrayList<AcceptanceData> data)
	{
		Team t = getTeamByID(teamID);
		
		for(AcceptanceData ad : data)
			if(ad.accepted)
				HashMapUtil.addToHashMap(t.getAcceptedOffers(), ad.productOffer, ad.count);
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
		
		ArrayList<HashMap<ProductOffer, Integer>> acceptedRejectedList = AllowedProductManager.getInstance().calculateAcceptedAndRejectedRoundOffersForDistrict(district);
		HashMap<ProductOffer, Integer> accepted = acceptedRejectedList.get(0);
		HashMap<ProductOffer, Integer> rejected = acceptedRejectedList.get(1);
		
		for(Entry<ProductOffer, Integer> entry : accepted.entrySet())
			result.add(new AcceptanceData(entry.getKey(), entry.getValue(), true));
		for(Entry<ProductOffer, Integer> entry : rejected.entrySet())
			result.add(new AcceptanceData(entry.getKey(), entry.getValue(), false));
		
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
		
		//Put all the acceptancedata from the districts of the given team in the result.
		District[] districts = DistrictManager.getInstance().getDistricts();
		for(int i = 0; i < districts.length; i++)
		{
			ArrayList<AcceptanceData> districtData = getRoundAcceptanceDataForDistrict(districts[i].getID());
			for(AcceptanceData ad : districtData)
			{
				Team adTeam = TeamManager.getInstance().getTeamByTechnologyID(ad.productOffer.getProduct().getTechnology().getID());
				if(adTeam.getID() == team)
					result.add(ad);
			}
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
}
