package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.data.District;
import model.data.Product;
import model.data.ProductOffer;
import model.data.Technology;
import model.managers.ProductManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.ProductOfferUtil;
import view.components.BufferedImageJPanel;
import content.Content;

public class ProductOfferCollectionPanel extends JPanel
{
	protected JScrollPane scrollPane;
	protected JPanel offerPanel;
	
	public ProductOfferCollectionPanel(Dimension size, HashMap<ProductOffer, Integer> pomap)
	{
		super();
		
		initialize(size, pomap);
	}
	
	private void initialize(Dimension size, HashMap<ProductOffer, Integer> pomap)
	{
		this.setLayout(null);
		this.setSize(size);
		this.setOpaque(false);
		
		//Sizes
		//VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		
		//Locations

		//Start with an empty panel.
		fillWithProductOffers(pomap);
	}
	
	/**
	 * This will make sure the panel contains all necessary information from the productoffers.
	 * @param productOffers
	 */
	public void fillWithProductOffers(HashMap<ProductOffer, Integer> productOffers)
	{
		//Remove the old offers
		this.removeOldProductOffers();
		
		//Sizes of items & panels
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		VectorF2 entrysize = new VectorF2(holdersize.x - 18, 30);
		VectorF2 panelsize = new VectorF2(holdersize.x - 18, productOffers.size() * entrysize.y);
		VectorF2 imagesize = new VectorF2(40, 0.75f * entrysize.y);
		VectorF2 countsize = new VectorF2(30, 20);
				
		//Locations & distances
		float imageX = 25;
		float imageY = 0.1f * entrysize.y;
		float countX = 90;
		float countY = 0.15f * entrysize.y;
		
		//Create the offerpanel
		this.offerPanel = new JPanel();
		this.offerPanel.setLayout(null);
		this.offerPanel.setOpaque(false);
		ComponentUtil.setComponentBounds(offerPanel, panelsize, new VectorF2());
		this.offerPanel.setPreferredSize(new Dimension((int)panelsize.x, (int)panelsize.y));

		//Insert the offers
		int index = 0;
		Set<Entry<ProductOffer, Integer>> entrySet = productOffers.entrySet();
		ArrayList<Entry<ProductOffer, Integer>> sortedEntryList = ProductOfferUtil.sortEntrySetOnHashCode(entrySet);
		for(Entry<ProductOffer, Integer> entry : sortedEntryList)
		{
			ProductOffer po = entry.getKey();
			int timesOffered = entry.getValue();
			
			//Insert product image
			VectorF2 imagepos = new VectorF2(imageX, imageY + (index * entrysize.y));
			BufferedImageJPanel productImage = new BufferedImageJPanel(ProductManager.getProductResourceInfo(po.getProduct()));
			ComponentUtil.setComponentBounds(productImage, imagesize, imagepos);
			productImage.setToolTipText(po.getProduct().toString());
			offerPanel.add(productImage);
			
			//Insert count label
			VectorF2 countpos = new VectorF2(countX, countY + (index * entrysize.y));
			JLabel countLabel = new JLabel("" + timesOffered);
			countLabel.setForeground(Color.white);
			countLabel.setFont(Content.smallFont);
			countLabel.setToolTipText("The number of product offers of type " + po.getProduct().toString());
			ComponentUtil.setComponentBounds(countLabel, countsize, countpos);
			offerPanel.add(countLabel);
			
			index++;
		}
		
		//Create the scrollpane
		this.scrollPane = new JScrollPane(offerPanel);
		ComponentUtil.setComponentBounds(scrollPane, holdersize, new VectorF2());
		this.scrollPane.getViewport().setOpaque(false);
		this.scrollPane.setOpaque(false);
		this.add(scrollPane);
	}
	
	/**
	 * Remove the old panel and scrollpanel with their content.
	 */
	private void removeOldProductOffers()
	{
		if(scrollPane != null)
		{
			this.remove(scrollPane);
			this.scrollPane = null;
			this.offerPanel = null;
		}
	}
}
