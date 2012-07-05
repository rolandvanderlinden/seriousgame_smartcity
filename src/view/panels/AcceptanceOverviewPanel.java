package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.data.AcceptanceData;
import model.data.District;
import model.data.Team;
import model.managers.TeamManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.components.BufferedImageJPanel;
import content.Content;

public class AcceptanceOverviewPanel extends JPanel
{
	protected int roundNumber;
	
	private JLabel districtLabel, teamLabel;
	private DistrictAcceptancePanel[] districtPanels;
	private TeamAcceptancePanel[] teamPanels;
	private BufferedImageJPanel seperatorImage;
	
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
		FontMetrics hugeFontMetrics = this.getFontMetrics(Content.hugeBoldFont);
		
		//Text
		String districtLabelText = "District product acceptance";
		String teamLabelText = "Company product acceptance";
		
		//Sizes
		VectorF2 holdersize = new VectorF2(size.width, size.height);
		VectorF2 dlabelsize = SizeCalculator.calculateSize(new VectorF2(hugeFontMetrics.stringWidth(districtLabelText), 30), holdersize);
		VectorF2 tlabelsize = SizeCalculator.calculateSize(new VectorF2(hugeFontMetrics.stringWidth(teamLabelText), 30), holdersize);
		VectorF2 sepsize = SizeCalculator.calculateSize(new VectorF2(1, 1), new VectorF2(Integer.MAX_VALUE, 2), holdersize, 0.8f, 0.1f);
		VectorF2 dpanelsize = SizeCalculator.calculateSize(holdersize, 0.275f, 0.4f);
		VectorF2 tpanelsize = SizeCalculator.calculateSize(holdersize, 0.275f, 0.4f);
		
		//Locations
		VectorF2 dlabelpos = LocationCalculator.calculateLocation(dlabelsize, holdersize, LocationType.CENTER, 0.025f);
		VectorF2 tlabelpos = LocationCalculator.calculateLocation(tlabelsize, holdersize, LocationType.CENTER, 0.5f);
		VectorF2 seppos = LocationCalculator.calculateLocation(sepsize, holdersize, LocationType.CENTER, 0.49f);
		VectorF2[] dispposarray = new VectorF2[districts.length];
		for(int i = 0; i < districts.length; i++)
			dispposarray[i] = LocationCalculator.calculateLocation(dpanelsize, holdersize, 0.05f + (i * 0.3125f), 0.075f);
		VectorF2[] teampposarray = new VectorF2[teams.length];
		for(int i = 0; i < teams.length; i++)
			teampposarray[i] = LocationCalculator.calculateLocation(tpanelsize, holdersize, 0.05f + (i * 0.3125f), 0.55f);
		
		//Insert labels
		this.districtLabel = new JLabel(districtLabelText);
		ComponentUtil.setComponentBounds(districtLabel, dlabelsize, dlabelpos);
		this.districtLabel.setForeground(Color.white);
		this.districtLabel.setFont(Content.hugeBoldFont);
		this.add(districtLabel);
		this.teamLabel = new JLabel(teamLabelText);
		ComponentUtil.setComponentBounds(teamLabel, tlabelsize, tlabelpos);
		this.teamLabel.setForeground(Color.white);
		this.teamLabel.setFont(Content.hugeBoldFont);
		this.add(teamLabel);
		
		//Insert seperator
		this.seperatorImage = new BufferedImageJPanel(Content.black);
		ComponentUtil.setComponentBounds(seperatorImage, sepsize, seppos);
		//this.add(seperatorImage);
		
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
