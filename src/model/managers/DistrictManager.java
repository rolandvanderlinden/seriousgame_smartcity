package model.managers;

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
	
	private DistrictManager()
	{
		//Initiate all districts.
		districts = new District[Config.districtNames.length];
		for(int i = 0; i < Config.districtNames.length; i++)
			districts[i] = new District(i, Config.districtNames[i]);
	}
	
	public static DistrictManager getInstance()
	{
		if(instance == null)
			instance = new DistrictManager();
		
		return instance;
	}
	
	public District getDistrictByID(int ID)
	{
		for(District d : districts)
			if(d.getID() == ID)
				return d;
		
		return null;
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
		String imagename = "district-" + district.getName() + ".jpg";
		
		return new ResourceInfo(imagename, DistrictRef.class);
	}
}
