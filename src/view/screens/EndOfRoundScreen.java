package view.screens;

import java.awt.Dimension;

import model.data.District;
import model.data.Team;
import model.managers.DistrictManager;
import model.managers.TeamManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.Output;
import util.SizeCalculator;
import view.panels.AcceptanceOverviewPanel;

public class EndOfRoundScreen extends AScreen
{
	protected int roundNumber;
	
	protected AcceptanceOverviewPanel acceptanceOverviewPanel;
	
	public EndOfRoundScreen(Dimension size, int roundNumber)
	{
		super(size);
		
		this.roundNumber = roundNumber;
		
		initialize(size);
	}

	private void initialize(Dimension size)
	{
		this.setLayout(null);
		
		//Sizes
		VectorF2 holdersize = new VectorF2(size.width, size.height);
		VectorF2 aopsize = SizeCalculator.calculateSize(holdersize, 1f, 0.9f);
		
		//Locations
		VectorF2 aoppos = LocationCalculator.calculateLocation(aopsize, holdersize, LocationType.CENTER, 0.1f);
		
		//Acceptance overview panel
		District[] districts = DistrictManager.getInstance().getDistricts();
		Team[] teams = TeamManager.getInstance().getTeams();
		this.acceptanceOverviewPanel = new AcceptanceOverviewPanel(new Dimension((int)aopsize.x, (int)aopsize.y), districts, teams, this.roundNumber);
		ComponentUtil.setComponentBounds(acceptanceOverviewPanel, aopsize, aoppos);
		this.add(acceptanceOverviewPanel);	
	}
}
