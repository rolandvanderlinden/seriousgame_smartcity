package view.panels;

import java.awt.Dimension;

import javax.swing.JPanel;

import view.components.BufferedImageJPanel;
import content.Content;

public class HappinessPanel extends JPanel
{
	protected BufferedImageJPanel percentageImage, overlayImage;
	
	public HappinessPanel(Dimension size)
	{
		super();
		
		initialize(size);
	}
	
	private void initialize(Dimension size)
	{
		this.setLayout(null);
		this.setSize(size);
		this.setOpaque(false);
		
		//Insert images
		this.overlayImage = new BufferedImageJPanel(Content.happinessOverlay);
		this.overlayImage.setSize(size);
		this.add(overlayImage);
		this.percentageImage = new BufferedImageJPanel(Content.happinessBar);
		this.add(percentageImage);
		
		//Set size of the bar.
		//TODO remove
		setHappiness(0.52f);
	}
	
	/**
	 * Set the size of the happinessbar.
	 * @param percentage
	 */
	public void setHappiness(double percentage)
	{
		//Percentage in between 0 and 1.
		percentage = Math.min(1, Math.max(0, percentage));
		
		int width = (int)(percentage * (this.getWidth()));
		int height = this.getHeight();
		
		this.percentageImage.setSize(width, height);
	}
	
}
