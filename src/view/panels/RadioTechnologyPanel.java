package view.panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.data.Technology;
import model.util.VectorF2;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.components.BufferedImageJPanel;

public class RadioTechnologyPanel extends JPanel
{
	protected BufferedImageJPanel technologyImage;
	protected JRadioButton radioButton;
	
	public RadioTechnologyPanel(Dimension size, Technology technology)
	{
		super();
		
		initialize(size);
	}
	
	private void initialize(Dimension size)
	{
		this.setLayout(null);
		this.setSize(size);
		this.setOpaque(false);
		
		//Sizes
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		VectorF2 productimagesize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.6f, 0.75f);
		VectorF2 radiobuttonsize = new VectorF2(30, 30);
		
		//Locations
		VectorF2 productimagepos = LocationCalculator.calculateLocation(productimagesize, holdersize, LocationType.CENTER, 0.1f);
		VectorF2 radiobuttonpos = LocationCalculator.calculateLocation(radiobuttonsize, holdersize, LocationType.CENTER, 0.8f);
		
		//Add components
		this.technologyImage = new BufferedImageJPanel(null);
		
	}
}
