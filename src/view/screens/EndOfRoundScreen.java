package view.screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JButton;
import javax.swing.JLabel;

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
import content.Content;
import controller.screens.EndOfRoundController;
import controller.screens.IScreenDisplayController;

public class EndOfRoundScreen extends AScreen
{
	protected EndOfRoundController endOfRoundController;
	
	protected int roundNumber;
	
	protected JLabel roundLabel;
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
		FontMetrics hugeFontMetrics = this.getFontMetrics(Content.hugeFont);
		
		//Sizes
		VectorF2 holdersize = new VectorF2(size.width, size.height);
		VectorF2 rlabelsize = SizeCalculator.calculateSize(new VectorF2(1, 1), holdersize); //size set on text set.
		VectorF2 cancelbuttonsize = new VectorF2(100, 30);
		VectorF2 unlockbuttonsize = new VectorF2(140, 30);
		VectorF2 advancebuttonsize = new VectorF2(180, 30);
		VectorF2 aopsize = SizeCalculator.calculateSize(holdersize, 1f, 0.935f);
		
		//Locations
		VectorF2 rlabelpos = LocationCalculator.calculateLocation(rlabelsize, holdersize, LocationType.CENTER, 0.009f); //location set on text set
		VectorF2 cancelbuttonpos = LocationCalculator.calculateLocation(cancelbuttonsize, holdersize, 0.015f, 0.015f);
		VectorF2 unlockbuttonpos = LocationCalculator.calculateLocation(unlockbuttonsize, holdersize, 0.69f, 0.015f);
		VectorF2 advancebuttonpos = LocationCalculator.calculateLocation(advancebuttonsize, holdersize, 0.8225f, 0.015f);
		VectorF2 aoppos = LocationCalculator.calculateLocation(aopsize, holdersize, LocationType.CENTER, 0.1f);
		
		//Insert label
		this.roundLabel = new JLabel();
		ComponentUtil.setComponentBounds(roundLabel, rlabelsize, rlabelpos);
		this.roundLabel.setForeground(Color.white);
		this.roundLabel.setFont(Content.hugeFont);
		this.add(roundLabel);
		
		//Insert buttons.
		this.cancelButton = new JButton("Cancel");
		ComponentUtil.setComponentBounds(cancelButton, cancelbuttonsize, cancelbuttonpos);
		this.cancelButton.setActionCommand(EndOfRoundController.cancelActionCommand);
		this.cancelButton.addActionListener(endOfRoundController);
		this.cancelButton.setToolTipText("The year was not finished yet; Go back.");
		this.add(cancelButton);
		this.unlockAdvanceButton = new JButton("Unlock advance");
		ComponentUtil.setComponentBounds(unlockAdvanceButton, unlockbuttonsize, unlockbuttonpos);
		this.unlockAdvanceButton.setActionCommand(EndOfRoundController.unlockAdvanceActionCommand);
		this.unlockAdvanceButton.addActionListener(endOfRoundController);
		this.unlockAdvanceButton.setToolTipText("Unlock/Lock advancing to the next year.");
		this.add(unlockAdvanceButton);
		this.advanceButton = new JButton("Advance to next year");
		ComponentUtil.setComponentBounds(advanceButton, advancebuttonsize, advancebuttonpos);
		this.advanceButton.setActionCommand(EndOfRoundController.advanceActionCommand);
		this.advanceButton.addActionListener(endOfRoundController);
		this.advanceButton.setEnabled(false);
		this.advanceButton.setToolTipText("Advance to the next year. Wait for a sign before using.");
		this.add(advanceButton);

		//Acceptance overview panel
		District[] districts = DistrictManager.getInstance().getDistricts();
		Team[] teams = TeamManager.getInstance().getTeams();
		this.acceptanceOverviewPanel = new AcceptanceOverviewPanel(new Dimension((int)aopsize.x, (int)aopsize.y), districts, teams, this.roundNumber);
		ComponentUtil.setComponentBounds(acceptanceOverviewPanel, aopsize, aoppos);
		this.add(acceptanceOverviewPanel);	
		
		//Start up some components
		this.setRound(this.roundNumber);
	}
	
	/**
	 * This will set the current round number.
	 * @param round
	 */
	public void setRound(int round)
	{
		FontMetrics hugeFontMetrics = this.getFontMetrics(Content.hugeFont);
		
		//Calculate the new size and location of the label based on the text size.
		String text = "End of year " + round;
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		VectorF2 size = SizeCalculator.calculateSize(new VectorF2(hugeFontMetrics.stringWidth(text), 30), holdersize);
		VectorF2 location = LocationCalculator.calculateLocation(size, holdersize, LocationType.CENTER, 0.0175f);
		
		//Change text and position/location of the label.
		this.roundLabel.setText(text);
		ComponentUtil.setComponentBounds(roundLabel, size, location);
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
