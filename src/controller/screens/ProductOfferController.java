package controller.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import model.data.District;
import model.data.Product;
import model.data.ProductOffer;
import model.data.Team;
import model.data.TechImprovement;
import model.data.Technology;
import model.managers.ProductManager;
import model.managers.TeamManager;
import util.Output;
import view.panels.ProductOfferPanel;
import view.screens.ProductOfferScreen;

public class ProductOfferController implements ActionListener, ItemListener
{
	protected IScreenDisplayController screenDisplayController;
	protected District district;
	protected ProductOfferScreen productOfferScreen;
	
	protected Technology selectedTechnology;
	protected ArrayList<TechImprovement> selectedImprovements;
	
	public ProductOfferController(IScreenDisplayController screenDisplayController, District district, ProductOfferScreen productOfferScreen)
	{
		super();
		
		this.screenDisplayController = screenDisplayController;
		this.district = district;
		this.productOfferScreen = productOfferScreen;
		
		this.selectedImprovements = new ArrayList<TechImprovement>();
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source instanceof JRadioButton)
		{
			JRadioButton rbutton = (JRadioButton)source;
			String actioncommand = rbutton.getActionCommand();
			
			//A technology has been selected.
			if(actioncommand.startsWith(ProductManager.technologyActionCommand))
			{
				String[] restarray = actioncommand.split(ProductManager.technologyActionCommand);
				String idstring = restarray[restarray.length - 1];
				idstring = idstring.trim();
				
				int technologyID = Integer.parseInt(idstring);
				this.selectedTechnology = ProductManager.getInstance().getTechnologyByID(technologyID);
				
				//Make sure the players are now allowed to advance.
				this.productOfferScreen.getProductOfferPanel().getOfferButton().setEnabled(true);
			}
			else
				throw new UnsupportedOperationException();
			
		}
		
		else if(source instanceof JButton)
		{
			JButton button = (JButton)source;
			String actioncommand = button.getActionCommand();
			
			//Offer the product with the currently selected technology and improvements.
			if(actioncommand.equals(ProductOfferPanel.offerProductActionCommand))
			{
				//TODO add the product to the teams' round offers. Remove the product info that has been saved here.
				Product offeredProduct = ProductManager.getInstance().getProductByContent(selectedTechnology, selectedImprovements.toArray(new TechImprovement[selectedImprovements.size()]));
				ProductOffer productOffer = new ProductOffer(offeredProduct, district);
				
				//The id of the offered technology is equal to the id of the team.
				Team team = TeamManager.getInstance().getTeamByTechnologyID(productOffer.getProduct().getTechnology().getID());
				team.addRoundOffer(productOffer);
				
				this.selectedImprovements.clear();
				this.selectedTechnology = null;
				
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
		
		productOfferScreen.revalidate();
		productOfferScreen.repaint();
		
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getSource();
		
		if(source instanceof JCheckBox)
		{
			JCheckBox cbox = (JCheckBox)source;
			String actioncommand = cbox.getActionCommand();
			
			//A techimprovement has been selected or deselected.
			if(actioncommand.startsWith(ProductManager.techImprovementActionCommand))
			{
				String[] restarray = actioncommand.split(ProductManager.techImprovementActionCommand);
				String idstring = restarray[restarray.length - 1];
				idstring = idstring.trim();
				
				int improvementID = Integer.parseInt(idstring);
				TechImprovement improvement = ProductManager.getInstance().getImprovementByID(improvementID);
				
				if(cbox.isSelected())
					this.selectedImprovements.add(improvement);
				else
					this.selectedImprovements.remove(improvement);
			}
			
			else
				throw new UnsupportedOperationException();
		}
	}
	
	

}
