package model.managers;

import java.util.ArrayList;

import model.data.AcceptanceData;
import model.data.District;
import model.util.ResourceInfo;
import application.Config;
import content.district.DistrictRef;

/**
 * Singleton district manager.
 * @author Roland van der Linden
 *
 */
public class DistrictManager
{
	public final static String districtOfferActionCommand = "District offer for ID: ";

	private static DistrictManager instance;
	protected District[] districts;
	protected double[] districtHappiness;
	
	private DistrictManager()
	{
		//Initiate all districts.
		districts = new District[Config.districtNames.length];
		districtHappiness = new double[Config.districtNames.length];
		for(int i = 0; i < Config.districtNames.length; i++)
		{
			districts[i] = new District(i, Config.districtNames[i]);
			districtHappiness[i] = Config.initialDistrictHappiness;
		}
	}
	
	public static DistrictManager getInstance()
	{
		if(instance == null)
			instance = new DistrictManager();
		
		return instance;
	}
	
	public double[] calculateHappinessChangeOfDistricts()
	{
		double[] result = new double[districts.length];
		
		for(int i = 0; i < districts.length; i++)
		{
			int acceptedNormal = 0;
			int acceptedSpecial = 0;
			int rejected = 0;
			
			ArrayList<AcceptanceData> districtAcceptanceData = TeamManager.getInstance().getRoundAcceptanceDataForDistrict(i);
			for(AcceptanceData ad : districtAcceptanceData)
			{
				if(ad.accepted)
				{
					if(ad.productOffer.getProduct().getImprovements().length == 0)
						acceptedNormal += ad.count;
					else
						acceptedSpecial += ad.count;
				}
				else
					rejected += ad.count;
			}
			
			double happinessLoss = rejected * Config.happinessLossRejected;
			if((acceptedNormal + acceptedSpecial + rejected) == 0)
				happinessLoss += Config.happinessLossNeglected;
			double happinessGain = (acceptedNormal * Config.happinessGainAcceptedNormal) + (acceptedSpecial * Config.happinessGainAcceptedSpecial);
			
			result[i] = happinessGain - happinessLoss;
		}

		return result;
	}
	
	public void processHappinessChangeOfDistricts()
	{
		double[] happinessChanges = calculateHappinessChangeOfDistricts();
		for(int i = 0; i < happinessChanges.length; i++)
			changeDistrictHappinessPointsByID(i,  happinessChanges[i]);
	}
	
	public void changeDistrictHappinessPointsByID(int ID, double happinessChange)
	{
		districtHappiness[ID] = Math.max(0, Math.min(100, districtHappiness[ID] + happinessChange));
	}
	
	public District getDistrictByID(int ID)
	{
		for(District d : districts)
			if(d.getID() == ID)
				return d;
		
		return null;
	}
	
	public double getDistrictHappinessPercentageByID(int ID)
	{
		return districtHappiness[ID] / 100.0;
	}
	
	public double getCityHappinessPercentage()
	{
		double percentage = 0;
		
		for(int i = 0; i < districtHappiness.length; i++)
		{
			if(i == Config.centrumDistrictIndex)
				percentage += 0.4 * districtHappiness[i];
			else
				percentage += 0.3 * districtHappiness[i];
		}
		
		return percentage / 100.0;
	}

	public District getDistrictByName(String name)
	{
		for(District d : districts)
			if(d.getName().equals(name))
				return d;
		
		return null;
	}
	
	public District[] getDistricts()
	{
		return districts;
	}
	
	public static ResourceInfo getDistrictResourceInfo(District district)
	{
		String imagename = district.getName() + ".jpg";
		
		return new ResourceInfo(imagename, DistrictRef.class);
	}
}
