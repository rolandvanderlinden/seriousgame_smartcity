package controller.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.data.District;
import model.managers.DistrictManager;
import model.managers.TeamManager;
import view.screens.OverviewScreen;

public class OverviewController implements ActionListener
{
	public final static String nextRoundActionCommand = "NextRound";
	public final static String unlockResetActionCommand = "UnlockReset";
	public final static String resetRoundActionCommand = "ResetRound";
	
	private IScreenDisplayController screenDisplayController;
	private OverviewScreen screen;
	
	public OverviewController(IScreenDisplayController screenDisplayController, OverviewScreen screen)
	{
		super();
		
		this.screenDisplayController = screenDisplayController;
		this.screen = screen;
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
			
			//Test for next round
			else if(actioncommand.equals(nextRoundActionCommand))
			{
				//Make sure that the applet starts up the end-of-round screen.
				screenDisplayController.removeCurrentScreen();
				screenDisplayController.insertEndOfRoundScreen();
			}
			
			//Test for unlock reset
			else if(actioncommand.equals(unlockResetActionCommand))
			{
				//Switch the option to reset the round.
				boolean resetEnabled = screen.getResetRoundButton().isEnabled();
				if(resetEnabled)
					screen.getUnlockResetButton().setText("Unlock Reset");
				else
					screen.getUnlockResetButton().setText("Lock Reset");
				screen.getResetRoundButton().setEnabled(!resetEnabled);
			}
			
			//Test for reset current round
			else if(actioncommand.equals(resetRoundActionCommand))
			{
				TeamManager.getInstance().resetRoundOffers();
				
				//Re-insert the overview screen with a reset round.
				screenDisplayController.removeCurrentScreen();
				screenDisplayController.insertOverviewScreen();
			}
			
			else
				throw new UnsupportedOperationException();
			
			screen.revalidate();
			screen.repaint();
		}
		else 
			throw new UnsupportedOperationException();
	}

}
