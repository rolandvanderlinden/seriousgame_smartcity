package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JLabel;

import model.data.District;
import model.managers.DistrictManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.components.BufferedImageJPanel;
import view.components.TranslucentBufferedImageJPanel;
import content.Content;
import controller.screens.ProductOfferController;

public class ProductOfferPanel extends TranslucentBufferedImageJPanel
{
	protected ProductOfferController productOfferController;
	protected District district;
	
	protected JLabel titleLabel;
	protected BufferedImageJPanel districtImage;
	
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
		VectorF2 titlelabelsize = new VectorF2(largeFontMetrics.stringWidth(titleText), 30);
		VectorF2 dimagesize = SizeCalculator.calculateSize(holdersize, 0.5f, 0.3f);
		
		//Locations
		VectorF2 titlelabelpos = LocationCalculator.calculateLocation(titlelabelsize, holdersize, LocationType.CENTER, 0.05f);
		VectorF2 dimagepos = LocationCalculator.calculateLocation(dimagesize, holdersize, LocationType.CENTER, 0.1f);
		
		//Add labels
		this.titleLabel = new JLabel(titleText);
		ComponentUtil.setComponentBounds(titleLabel, titlelabelsize, titlelabelpos);
		this.titleLabel.setForeground(Color.white);
		this.titleLabel.setFont(Content.largeFont);
		this.add(titleLabel);
		
		//Add image
		this.districtImage = new BufferedImageJPanel(DistrictManager.getDistrictResourceInfo(district));
		ComponentUtil.setComponentBounds(districtImage, dimagesize, dimagepos);
		this.add(districtImage);
	}

}
