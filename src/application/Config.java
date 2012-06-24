package application;

import java.awt.Dimension;

/**
 * This class contains project wide configuration settings.
 * @author Roland van der Linden
 *
 */
public class Config
{
	public final static String appname = "Serious Gaming SmartCity prototype";
	public final static Dimension appsize = new Dimension(1200, 900);
	public final static int outerBorderSize = 2;
	
	//Startup data
	public final static String[] districtNames = {"d1", "d2", "d3"};
	public final static String dependendDistrictName = districtNames[0]; //TODO pick the correct districtname
	public final static String[] technologyNames = {"t1", "t2", "t3"}; 
}
