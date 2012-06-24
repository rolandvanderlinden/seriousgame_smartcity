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
	private JButton nextRoundButton, undoRoundButton, previousResultsButton;
	private DistrictPanel[] districtPanels;
	
	public OverviewScreen(Dimension size, IScreenDisplayController screenDisplayController, District[] districts)
	{
		super(size);
		
		this.overviewController = new OverviewController(screenDisplayController);
		
		initialize(districts);
	}
	
	private void initialize(District[] districts)
	{
		int width = this.getWidth();
		int height = this.getHeight();
		
		//Sizes
		VectorF2 holdersize = new VectorF2(width, height);
		VectorF2 rlabelsize = SizeCalculator.calculateSize(new VectorF2(1, 1), holdersize); //size set on text set.
		VectorF2 nextbuttonsize = SizeCalculator.calculateSize(new VectorF2(120, 30), holdersize);
		VectorF2 undobuttonsize = SizeCalculator.calculateSize(new VectorF2(170, 30), holdersize);
		VectorF2 previousbuttonsize = SizeCalculator.calculateSize(new VectorF2(170, 30), holdersize);
		VectorF2 dpanelsize = SizeCalculator.calculateSize(holdersize, 0.275f, 0.835f);
		
		//Locations
		VectorF2 rlabelpos = LocationCalculator.calculateLocation(rlabelsize, holdersize, LocationType.CENTER, 0.03f); //location set on text set
		VectorF2 nextbuttonpos = LocationCalculator.calculateLocationWithMargins(nextbuttonsize, holdersize, LocationType.END, 0.03f, new VectorF2(18,0));
		VectorF2 undobuttonpos = LocationCalculator.calculateLocation(undobuttonsize, holdersize, 0.7f, 0.03f);
		VectorF2 previousbuttonpos = LocationCalculator.calculateLocationWithMargins(previousbuttonsize, holdersize, LocationType.BEGIN, 0.03f, new VectorF2(18,0));
		VectorF2[] dispposarray = new VectorF2[districts.length];
		for(int i = 0; i < districts.length; i++)
			dispposarray[i] = LocationCalculator.calculateLocation(dpanelsize, holdersize, 0.05f + (i * 0.3125f), 0.115f);
		
		//Insert label
		this.roundLabel = new JLabel();
		ComponentUtil.setComponentBounds(roundLabel, rlabelsize, rlabelpos);
		this.roundLabel.setForeground(Color.white);
		this.roundLabel.setFont(Content.largeFont);
		this.add(roundLabel);
		
		//Insert buttons
		this.nextRoundButton = new JButton("Next Round");
		ComponentUtil.setComponentBounds(nextRoundButton, nextbuttonsize, nextbuttonpos);
		this.add(nextRoundButton);
		this.undoRoundButton = new JButton("Reset Current Round");
		ComponentUtil.setComponentBounds(undoRoundButton, undobuttonsize, undobuttonpos);
		this.add(undoRoundButton);
		this.previousResultsButton = new JButton("Previous offer results");
		ComponentUtil.setComponentBounds(previousResultsButton, previousbuttonsize, previousbuttonpos);
		this.add(previousResultsButton);
		
		//Insert districtpanels
		this.districtPanels = new DistrictPanel[districts.length];
		for(int i = 0; i < districts.length; i++)
		{
			DistrictPanel dp = new DistrictPanel(new Dimension((int)dpanelsize.x, (int)dpanelsize.y), districts[i], overviewController);
			ComponentUtil.setComponentBounds(dp, dpanelsize, dispposarray[i]);
			districtPanels[i] = dp;
			this.add(dp);			
		}		
	}
	
	/**
	 * This will set the current round number.
	 * @param round
	 */
	public void setRound(int round)
	{
		FontMetrics largeFontMetrics = this.getFontMetrics(Content.largeFont);
		
		//Calculate the new size and location of the label based on the text size.
		String text = "Round " + round;
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		VectorF2 size = SizeCalculator.calculateSize(new VectorF2(largeFontMetrics.stringWidth(text), 30), holdersize);
		VectorF2 location = LocationCalculator.calculateLocation(size, holdersize, LocationType.CENTER, 0.03f);
		
		//Change text and position/location of the label.
		this.roundLabel.setText("Round " + round);
		ComponentUtil.setComponentBounds(roundLabel, size, location);
	}
	
}
