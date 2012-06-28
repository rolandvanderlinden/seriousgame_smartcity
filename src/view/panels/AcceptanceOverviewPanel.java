package view.panels;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.data.AcceptanceData;
import model.data.District;
import model.data.Team;
import model.managers.TeamManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.Output;
import util.SizeCalculator;

public class AcceptanceOverviewPanel extends JPanel
{
	protected int roundNumber;
	private DistrictAcceptancePanel[] districtPanels;
	private TeamAcceptancePanel[] teamPanels;
	
	public AcceptanceOverviewPanel(Dimension size, District[] districts, Team[] teams, int roundNumber)
	{
		super();
		
		this.roundNumber = roundNumber;
		
		initialize(size, districts, teams);
	}
	
	private void initialize(Dimension size, District[] districts, Team[] teams)
	{
		this.setLayout(null);
		this.setOpaque(false);
		this.setSize(size);
		
		//Sizes
		VectorF2 holdersize = new VectorF2(size.width, size.height);
		VectorF2 dpanelsize = SizeCalculator.calculateSize(holdersize, 0.275f, 0.4f);
		VectorF2 tpanelsize = SizeCalculator.calculateSize(holdersize, 0.275f, 0.4f);
		
		//Locations
		VectorF2[] dispposarray = new VectorF2[districts.length];
		for(int i = 0; i < districts.length; i++)
			dispposarray[i] = LocationCalculator.calculateLocation(dpanelsize, holdersize, 0.05f + (i * 0.3125f), 0.05f);
		VectorF2[] teampposarray = new VectorF2[teams.length];
		for(int i = 0; i < teams.length; i++)
			teampposarray[i] = LocationCalculator.calculateLocation(tpanelsize, holdersize, 0.05f + (i * 0.3125f), 0.505f);
		
		//Insert districtpanels
		this.districtPanels = new DistrictAcceptancePanel[districts.length];
		for(int i = 0; i < districts.length; i++)
		{
			ArrayList<AcceptanceData> districtData = TeamManager.getInstance().getRoundAcceptanceDataForDistrict(districts[i].getID());
			DistrictAcceptancePanel dap = new DistrictAcceptancePanel(new Dimension((int)dpanelsize.x, (int)dpanelsize.y), districts[i], districtData);
			ComponentUtil.setComponentBounds(dap, dpanelsize, dispposarray[i]);
			districtPanels[i] = dap;
			this.add(dap);			
		}		
		//Insert teampanels
		this.teamPanels = new TeamAcceptancePanel[teams.length];
		for(int i = 0; i < teams.length; i++)
		{
			ArrayList<AcceptanceData> teamData = TeamManager.getInstance().getRoundAcceptanceDataForTeam(teams[i].getID());
			int fundsChange = TeamManager.getInstance().getRoundFundsChangeForTeam(teams[i].getID());
			TeamAcceptancePanel tap = new TeamAcceptancePanel(new Dimension((int)tpanelsize.x, (int)tpanelsize.y), teams[i], teamData, fundsChange);
			ComponentUtil.setComponentBounds(tap, tpanelsize, teampposarray[i]);
			teamPanels[i] = tap;
			this.add(tap);			
		}		
	}
}
