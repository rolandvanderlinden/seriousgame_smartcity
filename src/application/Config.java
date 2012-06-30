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
	public final static Dimension appsize = new Dimension(1100, 800);
	public final static int outerBorderSize = 2;
	
	public final static int fundsGainPerAcceptedProduct = 2;
	
	//Startup data
	public final static String[] teamNames = { "QuadCore Inc", "HexaTech", "CircaCorp" };
	public final static String[] districtNames = { "Estermondt", "Grote Beek", "De Hoven" };
	public final static String dependendDistrictName = districtNames[0]; //TODO pick the correct districtname
	public final static String[] technologyNames = { "QuadCore Technology", "HexaTech Technology", "CircaCorp Technology" }; 
	public final static String[] improvementNames = { "Userfriendlyness", "Moneysaver" };
}
