package controller.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import model.data.District;
import model.data.Product;
import model.data.ProductOffer;
import model.data.TechImprovement;
import model.data.Technology;
import model.managers.ProductManager;
import view.panels.ProductOfferPanel;

public class ProductOfferController implements ActionListener
{
	protected IScreenDisplayController screenDisplayController;
	protected District district;
	protected Technology selectedTechnology;
	protected ArrayList<TechImprovement> selectedImprovements;
	
	public ProductOfferController(IScreenDisplayController screenDisplayController, District district)
	{
		super();
		
		this.screenDisplayController = screenDisplayController;
		this.district = district;
		
		this.selectedImprovements = new ArrayList<TechImprovement>();
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source instanceof JButton)
		{
			JButton button = (JButton)source;
			String actioncommand = button.getActionCommand();
			
			//Offer the product with the currently selected technology and improvements.
			if(actioncommand.equals(ProductOfferPanel.offerProductActionCommand))
			{
				//TODO add the product to the teams' round offers. Remove the product info that has been saved here.
				Product offeredProduct = ProductManager.getInstance().getProductByContent(selectedTechnology, selectedImprovements.toArray(new TechImprovement[selectedImprovements.size()]));
				ProductOffer productOffer = new ProductOffer(offeredProduct, district);
				
				//Go back to the overview screen.
				screenDisplayController.removeCurrentScreen();
				screenDisplayController.insertOverviewScreen();
			}
			//Cancel anything that happened and return to the overview screen
			else if(actioncommand.equals(ProductOfferPanel.cancelOfferActionCommand))
			{
				screenDisplayController.removeCurrentScreen();
				screenDisplayController.insertOverviewScreen();
			}
			else
				throw new UnsupportedOperationException("actioncommand was incorrect: " + actioncommand);
			
		}
		else
			throw new UnsupportedOperationException();
		
	}
	
	

}
