package view.panels;

import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JLabel;

import model.data.District;
import model.util.VectorF2;
import view.components.TranslucentBufferedImageJPanel;
import content.Content;
import controller.screens.ProductOfferController;

public class ProductOfferPanel extends TranslucentBufferedImageJPanel
{
	protected ProductOfferController productOfferController;
	protected District district;
	
	protected JLabel titleLabel;
	
	public ProductOfferPanel(Dimension size, ProductOfferController productOfferController, District district)
	{
		super(Content.black, 0.5f);
		
		this.productOfferController = productOfferController;
		this.district = district;
		
		initialize(size);
	}
	
	private void initialize(Dimension size)
	{
		this.setSize(size);
		this.setLayout(null);
		FontMetrics largeFontMetrics = this.getFontMetrics(Content.largeFont);
		
		//Text of labels
		String titleText = "New product offer for district " + this.district.getName();
		
		//Sizes
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		
		//Locations
	}

}
