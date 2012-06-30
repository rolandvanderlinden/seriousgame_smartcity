package view.screens;

import java.awt.Dimension;

import javax.swing.JButton;

import model.data.District;
import model.data.Team;
import model.managers.DistrictManager;
import model.managers.TeamManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.panels.AcceptanceOverviewPanel;
import controller.screens.EndOfRoundController;
import controller.screens.IScreenDisplayController;

public class EndOfRoundScreen extends AScreen
{
	protected EndOfRoundController endOfRoundController;
	
	protected int roundNumber;
	
	protected JButton cancelButton, unlockAdvanceButton, advanceButton;
	protected AcceptanceOverviewPanel acceptanceOverviewPanel;
	
	public EndOfRoundScreen(Dimension size, IScreenDisplayController screenDisplayController, int roundNumber)
	{
		super(size);
		
		this.roundNumber = roundNumber;
		this.endOfRoundController = new EndOfRoundController(screenDisplayController, this);
		
		initialize(size);
	}

	private void initialize(Dimension size)
	{
		this.setLayout(null);
		
		//Sizes
		VectorF2 holdersize = new VectorF2(size.width, size.height);
		VectorF2 cancelbuttonsize = new VectorF2(100, 30);
		VectorF2 unlockbuttonsize = new VectorF2(140, 30);
		VectorF2 advancebuttonsize = new VectorF2(180, 30);
		VectorF2 aopsize = SizeCalculator.calculateSize(holdersize, 1f, 0.935f);
		
		//Locations
		VectorF2 cancelbuttonpos = LocationCalculator.calculateLocation(cancelbuttonsize, holdersize, 0.015f, 0.015f);
		VectorF2 unlockbuttonpos = LocationCalculator.calculateLocation(unlockbuttonsize, holdersize, 0.69f, 0.015f);
		VectorF2 advancebuttonpos = LocationCalculator.calculateLocation(advancebuttonsize, holdersize, 0.8225f, 0.015f);
		VectorF2 aoppos = LocationCalculator.calculateLocation(aopsize, holdersize, LocationType.CENTER, 0.1f);
		
		//Insert buttons.
		this.cancelButton = new JButton("Cancel");
		ComponentUtil.setComponentBounds(cancelButton, cancelbuttonsize, cancelbuttonpos);
		this.cancelButton.setActionCommand(EndOfRoundController.cancelActionCommand);
		this.cancelButton.addActionListener(endOfRoundController);
		this.add(cancelButton);
		this.unlockAdvanceButton = new JButton("Unlock advance");
		ComponentUtil.setComponentBounds(unlockAdvanceButton, unlockbuttonsize, unlockbuttonpos);
		this.unlockAdvanceButton.setActionCommand(EndOfRoundController.unlockAdvanceActionCommand);
		this.unlockAdvanceButton.addActionListener(endOfRoundController);
		this.add(unlockAdvanceButton);
		this.advanceButton = new JButton("Advance to next year");
		ComponentUtil.setComponentBounds(advanceButton, advancebuttonsize, advancebuttonpos);
		this.advanceButton.setActionCommand(EndOfRoundController.advanceActionCommand);
		this.advanceButton.addActionListener(endOfRoundController);
		this.advanceButton.setEnabled(false);
		this.add(advanceButton);

		//Acceptance overview panel
		District[] districts = DistrictManager.getInstance().getDistricts();
		Team[] teams = TeamManager.getInstance().getTeams();
		this.acceptanceOverviewPanel = new AcceptanceOverviewPanel(new Dimension((int)aopsize.x, (int)aopsize.y), districts, teams, this.roundNumber);
		ComponentUtil.setComponentBounds(acceptanceOverviewPanel, aopsize, aoppos);
		this.add(acceptanceOverviewPanel);	
	}
	
	public JButton getCancelButton()
	{
		return this.cancelButton;
	}
	
	
	public JButton getUnlockAdvanceButton()
	{
		return this.unlockAdvanceButton;
	}
	
	
	public JButton getAdvanceButton()
	{
		return this.advanceButton;
	}
}
