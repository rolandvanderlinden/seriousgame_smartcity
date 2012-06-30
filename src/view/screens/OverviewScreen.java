package view.screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JButton;
import javax.swing.JLabel;

import model.data.District;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.panels.DistrictPanel;
import content.Content;
import controller.screens.IScreenDisplayController;
import controller.screens.OverviewController;


public class OverviewScreen extends AScreen
{
	protected OverviewController overviewController;
	
	private JLabel roundLabel;
	private JButton nextRoundButton, unlockUndoButton, undoRoundButton, previousResultsButton;
	private DistrictPanel[] districtPanels;
	private int roundNumber;
	
	public OverviewScreen(Dimension size, IScreenDisplayController screenDisplayController, District[] districts, int roundNumber)
	{
		super(size);
		
		this.overviewController = new OverviewController(screenDisplayController, this);
		this.roundNumber = roundNumber;
		
		initialize(districts);
	}
	
	private void initialize(District[] districts)
	{
		int width = this.getWidth();
		int height = this.getHeight();
		
		//Sizes
		VectorF2 holdersize = new VectorF2(width, height);
		VectorF2 rlabelsize = SizeCalculator.calculateSize(new VectorF2(1, 1), holdersize); //size set on text set.
		VectorF2 nextbuttonsize = SizeCalculator.calculateSize(new VectorF2(120, 28), holdersize);
		VectorF2 unlockundobuttonsize = SizeCalculator.calculateSize(new VectorF2(120, 28), holdersize);
		VectorF2 undobuttonsize = SizeCalculator.calculateSize(new VectorF2(120, 28), holdersize);
		VectorF2 previousbuttonsize = SizeCalculator.calculateSize(new VectorF2(170, 28), holdersize);
		VectorF2 dpanelsize = SizeCalculator.calculateSize(holdersize, 0.25f, 0.7f);
		
		//Locations
		VectorF2 rlabelpos = LocationCalculator.calculateLocation(rlabelsize, holdersize, LocationType.CENTER, 0.01f); //location set on text set
		VectorF2 nextbuttonpos = LocationCalculator.calculateLocationWithMargins(nextbuttonsize, holdersize, LocationType.END, 0.015f, new VectorF2(18,0));
		VectorF2 unlockundobuttonpos = LocationCalculator.calculateLocation(undobuttonsize, holdersize, 0.6f, 0.015f);
		VectorF2 undobuttonpos = LocationCalculator.calculateLocation(undobuttonsize, holdersize, 0.7f, 0.015f);
		VectorF2 previousbuttonpos = LocationCalculator.calculateLocationWithMargins(previousbuttonsize, holdersize, LocationType.BEGIN, 0.015f, new VectorF2(18,0));
		VectorF2[] dispposarray = new VectorF2[districts.length];
		for(int i = 0; i < districts.length; i++)
			dispposarray[i] = LocationCalculator.calculateLocation(dpanelsize, holdersize, 0.06f + (i * 0.3125f), 0.115f);
		
		//Insert label
		this.roundLabel = new JLabel();
		ComponentUtil.setComponentBounds(roundLabel, rlabelsize, rlabelpos);
		this.roundLabel.setForeground(Color.white);
		this.roundLabel.setFont(Content.largeFont);
		this.add(roundLabel);
		
		//Insert buttons
		this.nextRoundButton = new JButton("Next Year");
		ComponentUtil.setComponentBounds(nextRoundButton, nextbuttonsize, nextbuttonpos);
		this.nextRoundButton.setActionCommand(OverviewController.nextRoundActionCommand);
		this.nextRoundButton.addActionListener(overviewController);
		this.add(nextRoundButton);
		this.unlockUndoButton = new JButton("Unlock Reset");
		ComponentUtil.setComponentBounds(unlockUndoButton, unlockundobuttonsize, unlockundobuttonpos);
		this.unlockUndoButton.setActionCommand(OverviewController.unlockResetActionCommand);
		this.unlockUndoButton.addActionListener(overviewController);
		this.add(unlockUndoButton);
		this.undoRoundButton = new JButton("Reset Year");
		ComponentUtil.setComponentBounds(undoRoundButton, undobuttonsize, undobuttonpos);
		this.undoRoundButton.setEnabled(false);
		this.undoRoundButton.setActionCommand(OverviewController.resetRoundActionCommand);
		this.undoRoundButton.addActionListener(overviewController);
		this.add(undoRoundButton);
		this.previousResultsButton = new JButton("Previous offer results");
		ComponentUtil.setComponentBounds(previousResultsButton, previousbuttonsize, previousbuttonpos);
		//this.add(previousResultsButton);
		
		//Insert districtpanels
		this.districtPanels = new DistrictPanel[districts.length];
		for(int i = 0; i < districts.length; i++)
		{
			DistrictPanel dp = new DistrictPanel(new Dimension((int)dpanelsize.x, (int)dpanelsize.y), districts[i], overviewController);
			ComponentUtil.setComponentBounds(dp, dpanelsize, dispposarray[i]);
			districtPanels[i] = dp;
			this.add(dp);			
		}		
		
		//Start up some components
		this.setRound(this.roundNumber);
	}
	
	/**
	 * This will set the current round number.
	 * @param round
	 */
	public void setRound(int round)
	{
		FontMetrics largeFontMetrics = this.getFontMetrics(Content.largeFont);
		
		//Calculate the new size and location of the label based on the text size.
		String text = "Year " + round;
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		VectorF2 size = SizeCalculator.calculateSize(new VectorF2(largeFontMetrics.stringWidth(text), 30), holdersize);
		VectorF2 location = LocationCalculator.calculateLocation(size, holdersize, LocationType.CENTER, 0.0175f);
		
		//Change text and position/location of the label.
		this.roundLabel.setText(text);
		ComponentUtil.setComponentBounds(roundLabel, size, location);
	}
	
	public JButton getUnlockResetButton()
	{
		return this.unlockUndoButton;
	}
	
	public JButton getResetRoundButton()
	{
		return this.undoRoundButton;
	}
}
