package view.panels;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.data.ProductOffer;
import model.util.VectorF2;
import util.ComponentUtil;

public class ProductOfferPanel extends JPanel
{
	protected JScrollPane scrollPane;
	protected JPanel offerPanel;
	
	public ProductOfferPanel(Dimension size)
	{
		super();
		
		initialize(size);
	}
	
	private void initialize(Dimension size)
	{
		int width = size.width;
		int height = size.height;
		
		this.setLayout(null);
		this.setOpaque(false);
		
		//Sizes
		VectorF2 holdersize = new VectorF2(width, height);
		
		//Locations
		
		//Offerpanel / scrollpane
		this.offerPanel = new JPanel();
		this.offerPanel.setLayout(null);
		this.offerPanel.setOpaque(false);
		ComponentUtil.setComponentBounds(offerPanel, holdersize, new VectorF2());
		this.scrollPane = new JScrollPane(offerPanel);
		ComponentUtil.setComponentBounds(scrollPane, holdersize, new VectorF2());
		this.scrollPane.getViewport().setOpaque(false);
		this.scrollPane.setOpaque(false);
		this.add(scrollPane);
	}
	
	/**
	 * This will make sure the panel contains all necessary information from the productoffers.
	 * @param productOffers
	 */
	public void createFromProductOffers(HashMap<ProductOffer, Integer> productOffers)
	{
		
	}
}
