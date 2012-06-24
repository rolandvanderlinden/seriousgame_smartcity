package view.screens;

import java.awt.Dimension;

import model.data.District;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.panels.ProductOfferPanel;
import controller.screens.IScreenDisplayController;
import controller.screens.ProductOfferController;

public class ProductOfferScreen extends AScreen
{
	protected ProductOfferController productOfferController;
	protected District district;
	
	protected ProductOfferPanel productOfferPanel;
	
	public ProductOfferScreen(Dimension size, IScreenDisplayController screenDisplayController, District district)
	{
		super(size);
		
		this.productOfferController = new ProductOfferController(screenDisplayController, district);
		this.district = district;
		
		initialize();
	}
	
	private void initialize()
	{
		int width = this.getWidth();
		int height = this.getHeight();
		
		//Sizes
		VectorF2 holdersize = new VectorF2(width, height);
		VectorF2 popanelsize = SizeCalculator.calculateSize(holdersize, 0.75f, 0.6f);
		
		//Locations
		VectorF2 popanelpos = LocationCalculator.calculateLocation(popanelsize, holdersize, LocationType.CENTER, 0.2f);
		
		//Insert product offer panel
		this.productOfferPanel = new ProductOfferPanel(new Dimension((int)popanelsize.x, (int)popanelsize.y), productOfferController, district);
		ComponentUtil.setComponentBounds(productOfferPanel, popanelsize, popanelpos);
		this.add(productOfferPanel);
	}

}
