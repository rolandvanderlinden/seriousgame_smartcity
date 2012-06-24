package view.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.data.Technology;
import model.managers.ProductManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.components.BufferedImageJPanel;

public class RadioTechnologyPanel extends JPanel
{
	protected Technology technology;
	
	protected BufferedImageJPanel technologyImage;
	protected JRadioButton radioButton;
	
	public RadioTechnologyPanel(Dimension size, Technology technology)
	{
		super();
		
		this.technology = technology;
		
		initialize(size);
	}
	
	private void initialize(Dimension size)
	{
		this.setLayout(null);
		this.setSize(size);
		this.setOpaque(false);
		
		//Sizes
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		VectorF2 techimagesize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.6f, 1.5f);
		VectorF2 radiobuttonsize = new VectorF2(20, 20);
		
		//Locations
		VectorF2 techimagepos = LocationCalculator.calculateLocation(techimagesize, holdersize, LocationType.CENTER, 0.1f);
		VectorF2 radiobuttonpos = LocationCalculator.calculateLocation(radiobuttonsize, holdersize, LocationType.CENTER, 0.9f);
		
		//Add components
		this.technologyImage = new BufferedImageJPanel(ProductManager.getTechnologyResourceInfo(technology));
		ComponentUtil.setComponentBounds(technologyImage, techimagesize, techimagepos);
		this.technologyImage.setToolTipText(technology.getName());
		this.add(technologyImage);
		
		this.radioButton = new JRadioButton();
		ComponentUtil.setComponentBounds(radioButton, radiobuttonsize, radiobuttonpos);
		this.radioButton.setOpaque(false);
		this.radioButton.setToolTipText("Select technology " + technology.getName() + ". Automatically unselects a previously selected technology.");
		//this.radioButton.setBackground(new Color(1, 1, 1, 0.25f));
		this.add(radioButton);
		
	}
	
	public JRadioButton getRadioButton()
	{
		return this.radioButton;
	}
}
