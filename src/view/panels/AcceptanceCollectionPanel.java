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

import model.data.AcceptanceData;
import model.data.ProductOffer;
import model.managers.ProductManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.ProductOfferUtil;
import view.components.BufferedImageJPanel;
import content.Content;

public class AcceptanceCollectionPanel extends JPanel
{
	protected JScrollPane scrollPane;
	protected JPanel acceptancePanel;
	
	public AcceptanceCollectionPanel(Dimension size, ArrayList<AcceptanceData> data)
	{
		super();
		
		initialize(size, data);
	}
	
	private void initialize(Dimension size, ArrayList<AcceptanceData> data)
	{
		this.setLayout(null);
		this.setSize(size);
		this.setOpaque(false);

		//Start with an empty panel.
		fillWithAcceptanceData(data);
	}
	
	/**
	 * This will make sure the panel contains all necessary information from the acceptance of offers.
	 */
	public void fillWithAcceptanceData(ArrayList<AcceptanceData> data)
	{
		//Remove the old offers
		this.removeOldProductOffers();
		
		//Sizes of items & panels
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		VectorF2 entrysize = new VectorF2(holdersize.x - 18, 30);
		VectorF2 panelsize = new VectorF2(holdersize.x - 18, data.size() * entrysize.y);
		VectorF2 imagesize = new VectorF2(40, 0.75f * entrysize.y);
		VectorF2 countsize = new VectorF2(30, 20);
		VectorF2 acceptancesize = new VectorF2(120, 20);
				
		//Locations & distances
		float imageX = 25;
		float imageY = 0.1f * entrysize.y;
		float countX = 90;
		float countY = 0.15f * entrysize.y;
		float acceptanceX = 140;
		float acceptanceY = 0.15f * entrysize.y;
		
		//Create the acceptance panel
		this.acceptancePanel = new JPanel();
		this.acceptancePanel.setLayout(null);
		this.acceptancePanel.setOpaque(false);
		ComponentUtil.setComponentBounds(acceptancePanel, panelsize, new VectorF2());
		this.acceptancePanel.setPreferredSize(new Dimension((int)panelsize.x, (int)panelsize.y));

		//Insert the data
		int index = 0;
		for(AcceptanceData ad : data)
		{
			ProductOffer po = ad.productOffer;
			int count = ad.count;
			boolean accepted = ad.accepted;
			
			//Insert product image
			VectorF2 imagepos = new VectorF2(imageX, imageY + (index * entrysize.y));
			BufferedImageJPanel productImage = new BufferedImageJPanel(ProductManager.getProductResourceInfo(po.getProduct()));
			ComponentUtil.setComponentBounds(productImage, imagesize, imagepos);
			productImage.setToolTipText(po.getProduct().toString());
			acceptancePanel.add(productImage);
			
			//Insert count label
			VectorF2 countpos = new VectorF2(countX, countY + (index * entrysize.y));
			JLabel countLabel = new JLabel("" + count);
			countLabel.setForeground(Color.white);
			countLabel.setFont(Content.smallFont);
			countLabel.setToolTipText("The number of product offers of type " + po.getProduct().toString());
			ComponentUtil.setComponentBounds(countLabel, countsize, countpos);
			acceptancePanel.add(countLabel);
			
			//Insert acceptance label
			VectorF2 acceptancepos = new VectorF2(acceptanceX, acceptanceY + (index * entrysize.y));
			JLabel acceptanceLabel = new JLabel();
			acceptanceLabel.setFont(Content.smallFont);
			ComponentUtil.setComponentBounds(acceptanceLabel, acceptancesize, acceptancepos);
			if(accepted)
			{
				acceptanceLabel.setText("Accepted");
				acceptanceLabel.setForeground(Color.green);
				acceptanceLabel.setToolTipText("District " + po.getDistrict().getName() + " accepted productoffer " + po.toString() + ".");				
			}
			else
			{
				acceptanceLabel.setText("Rejected");
				acceptanceLabel.setForeground(Color.red);
				acceptanceLabel.setToolTipText("District " + po.getDistrict().getName() + " rejected productoffer " + po.toString() + ".");			
			}
			acceptancePanel.add(acceptanceLabel);
			
			index++;
		}
		
		//Create the scrollpane
		this.scrollPane = new JScrollPane(acceptancePanel);
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
			this.acceptancePanel = null;
		}
	}
}
