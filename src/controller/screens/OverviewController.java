package controller.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.data.District;
import model.managers.DistrictManager;

public class OverviewController implements ActionListener
{
	private IScreenDisplayController screenDisplayController;
	
	public OverviewController(IScreenDisplayController screenDisplayController)
	{
		super();
		
		this.screenDisplayController = screenDisplayController;
	}
		
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if(source instanceof JButton)
		{
			JButton button = (JButton)source;
			String actioncommand = button.getActionCommand();
			
			//Test for district offer buttons
			if(actioncommand.startsWith(DistrictManager.districtOfferActionCommand))
			{
				String[] restarray = actioncommand.split(DistrictManager.districtOfferActionCommand);
				String idstring = restarray[restarray.length - 1];
				idstring = idstring.trim();
				
				//Find the district for which a product offer is going to be made.
				int districtID = Integer.parseInt(idstring);
				District district = DistrictManager.getInstance().getDistrictByID(districtID);
				if(district == null)
					throw new UnsupportedOperationException("actioncommand was incorrect: " + actioncommand);
				
				//Make sure that the applet starts up the productoffer screen.
				screenDisplayController.removeCurrentScreen();
				screenDisplayController.insertProductOfferScreen(district);
			}
			else
				throw new UnsupportedOperationException();
		}
		else 
			throw new UnsupportedOperationException();
	}

}
