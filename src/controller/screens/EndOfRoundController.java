package controller.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import model.data.AcceptanceData;
import model.data.Team;
import model.managers.DistrictManager;
import model.managers.TeamManager;
import util.Output;
import view.screens.EndOfRoundScreen;
import application.Config;

public class EndOfRoundController implements ActionListener
{
	public final static String cancelActionCommand = "Cancel";
	public final static String unlockAdvanceActionCommand = "UnlockReset";
	public final static String advanceActionCommand = "Advance";
	
	private IScreenDisplayController screenDisplayController;
	private EndOfRoundScreen screen;
	
	public EndOfRoundController(IScreenDisplayController screenDisplayController, EndOfRoundScreen screen)
	{
		super();
		
		this.screenDisplayController = screenDisplayController;
		this.screen = screen;
	}
		
	private void processRoundData()
	{
		//First process the happinessChanges
		DistrictManager.getInstance().processHappinessChangeOfDistricts();
		
		//First calculate everything that is allowed.
		ArrayList<ArrayList<AcceptanceData>> acceptanceDataPerTeam = new ArrayList<ArrayList<AcceptanceData>>();
		for(int i = 0; i < Config.teamNames.length; i++)
		{
			Team t = TeamManager.getInstance().getTeamByName(Config.teamNames[i]);
			ArrayList<AcceptanceData> dataOfTeam = TeamManager.getInstance().getRoundAcceptanceDataForTeam(t.getID());
			acceptanceDataPerTeam.add(dataOfTeam);
		}
		
		//Then add it to the accepted lists of the teams, and clear their round offers.
		for(int i = 0; i < Config.teamNames.length; i++)
		{
			Team t = TeamManager.getInstance().getTeamByName(Config.teamNames[i]);
			ArrayList<AcceptanceData> dataOfTeam = acceptanceDataPerTeam.get(i);
			TeamManager.getInstance().addAcceptanceDataToAcceptedOffersOfTeam(t.getID(), dataOfTeam);
			t.clearRoundOffers();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if(source instanceof JButton)
		{
			JButton button = (JButton)source;
			String actioncommand = button.getActionCommand();
			
			//Test for going back without taking action.
			if(actioncommand.equals(cancelActionCommand))
			{
				//Make sure that the applet starts up the productoffer screen.
				screenDisplayController.removeCurrentScreen();
				screenDisplayController.insertOverviewScreen();
			}
			
			//Test for unlocking the advance button.
			else if(actioncommand.equals(unlockAdvanceActionCommand))
			{
				//Switch the option to reset the round.
				boolean advanceEnabled = screen.getAdvanceButton().isEnabled();
				if(advanceEnabled)
					screen.getUnlockAdvanceButton().setText("Unlock advance");
				else
					screen.getUnlockAdvanceButton().setText("Lock advance");
				screen.getAdvanceButton().setEnabled(!advanceEnabled);
			}
			
			//Test for advance
			else if(actioncommand.equals(advanceActionCommand))
			{
				processRoundData();
				
				//Make sure that the applet starts up the end-of-round screen.
				screenDisplayController.increaseRoundNumber();
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