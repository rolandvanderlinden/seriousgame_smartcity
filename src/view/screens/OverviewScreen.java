package view.screens;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;

import model.util.VectorF2;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.panels.DistrictPanel;
import content.Content;


public class OverviewScreen extends AScreen
{
	private JLabel roundLabel;
	private JButton nextRoundButton, undoRoundButton, previousResultsButton;
	private DistrictPanel d1panel, d2panel, d3panel;
	
	public OverviewScreen(Dimension size)
	{
		super(size);
		
		initialize();
	}
	
	private void initialize()
	{
		int width = this.getWidth();
		int height = this.getHeight();
		
		//Sizes
		VectorF2 holdersize = new VectorF2(width, height);
		VectorF2 rlabelsize = SizeCalculator.calculateSize(new VectorF2(80, 30), holdersize);
		VectorF2 nextbuttonsize = SizeCalculator.calculateSize(new VectorF2(120, 30), holdersize);
		VectorF2 undobuttonsize = SizeCalculator.calculateSize(new VectorF2(170, 30), holdersize);
		VectorF2 previousbuttonsize = SizeCalculator.calculateSize(new VectorF2(170, 30), holdersize);
		VectorF2 dpanelsize = SizeCalculator.calculateSize(holdersize, 0.275f, 0.82f);
		
		//Locations
		VectorF2 rlabelpos = LocationCalculator.calculateLocation(rlabelsize, holdersize, LocationType.CENTER, 0.03f);
		VectorF2 nextbuttonpos = LocationCalculator.calculateLocationWithMargins(nextbuttonsize, holdersize, LocationType.END, 0.03f, new VectorF2(18,0));
		VectorF2 undobuttonpos = LocationCalculator.calculateLocation(undobuttonsize, holdersize, 0.7f, 0.03f);
		VectorF2 previousbuttonpos = LocationCalculator.calculateLocationWithMargins(previousbuttonsize, holdersize, LocationType.BEGIN, 0.03f, new VectorF2(18,0));
		VectorF2 d1panelpos = LocationCalculator.calculateLocation(dpanelsize, holdersize, 0.05f, 0.13f);
		VectorF2 d2panelpos = LocationCalculator.calculateLocation(dpanelsize, holdersize, LocationType.CENTER, 0.13f);
		VectorF2 d3panelpos = LocationCalculator.calculateLocation(dpanelsize, holdersize, 0.675f, 0.13f);
		
		//Insert label
		this.roundLabel = new JLabel();
		this.setComponentBounds(roundLabel, rlabelsize, rlabelpos);
		this.roundLabel.setForeground(Color.white);
		this.roundLabel.setFont(Content.largeFont);
		this.add(roundLabel);
		
		//Insert buttons
		this.nextRoundButton = new JButton("Next Round");
		this.setComponentBounds(nextRoundButton, nextbuttonsize, nextbuttonpos);
		this.add(nextRoundButton);
		this.undoRoundButton = new JButton("Reset Current Round");
		this.setComponentBounds(undoRoundButton, undobuttonsize, undobuttonpos);
		this.add(undoRoundButton);
		this.previousResultsButton = new JButton("Previous offer results");
		this.setComponentBounds(previousResultsButton, previousbuttonsize, previousbuttonpos);
		this.add(previousResultsButton);
		
		//Insert districtpanels
		this.d1panel = new DistrictPanel();
		this.setComponentBounds(d1panel, dpanelsize, d1panelpos);
		this.add(d1panel);
		this.d2panel = new DistrictPanel();
		this.setComponentBounds(d2panel, dpanelsize, d2panelpos);
		this.add(d2panel);
		this.d3panel = new DistrictPanel();
		this.setComponentBounds(d3panel, dpanelsize, d3panelpos);
		this.add(d3panel);
		
	}
	
	/**
	 * This will set the current round number.
	 * @param round
	 */
	public void setRound(int round)
	{
		this.roundLabel.setText("Round " + round);
	}
	
}
