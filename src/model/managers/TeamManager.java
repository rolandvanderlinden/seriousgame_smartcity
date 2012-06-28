package model.managers;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;

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
