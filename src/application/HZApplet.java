package application;

import javax.swing.JApplet;

import model.data.District;
import model.managers.DistrictManager;
import util.Output;
import view.base.BackgroundPanel;
import view.screens.AScreen;
import view.screens.EndOfRoundScreen;
import view.screens.OverviewScreen;
import view.screens.ProductOfferScreen;
import controller.screens.IScreenDisplayController;

public class HZApplet extends JApplet implements IScreenDisplayController
{
	// **********************************************
	// Fields
	// **********************************************
	
	public BackgroundPanel backgroundPanel;
	public AScreen mainScreen;
	public int currentRoundNumber;
	
	// **********************************************
	// init
	// **********************************************
	
	/**
	 * This method is called when the applet is being initialized.
	 */
	public void init()
	{
		try
		{
			Output.show();
			
			//init some other components
			this.currentRoundNumber = 1;
			
			//Create the basic layout of the applet.
			this.createBasicLayout();
			//Insert the overview screen as the first screen.
			this.insertOverviewScreen();
		}
		catch(Exception e)
		{
			Output.showException(e, "An error occured during applet initialization.");
		}
	}
	
	/**
	 * This creates the basic background layout for the applet.
	 */
	private void createBasicLayout()
	{
		//Set the correct size of the applet.
		this.setLayout(null);
		this.setSize(Config.appsize);

		//Insert the most basic background panel into the applet.
		this.backgroundPanel = new BackgroundPanel(Config.appsize, Config.outerBorderSize);
		this.add(backgroundPanel);
	}
	
	// **********************************************
	// Insert & Remove screens
	// **********************************************
	
	@Override
	public void removeCurrentScreen()
	{
		if(mainScreen != null)
		{
			this.remove(mainScreen);
			this.mainScreen = null;
		}
	}
	
	private void insertAScreen(AScreen screen)
	{
		this.remove(backgroundPanel);
		
		this.mainScreen = screen;
		this.add(mainScreen);
		this.add(backgroundPanel);
		
		this.rootPane.revalidate();
		this.rootPane.repaint();
	}
	
	@Override
	public void insertOverviewScreen()
	{
		insertAScreen(new OverviewScreen(backgroundPanel.getSize(), this, DistrictManager.getInstance().getDistricts(), this.currentRoundNumber));
	}
	
	@Override
	public void insertProductOfferScreen(District district)
	{
		insertAScreen(new ProductOfferScreen(backgroundPanel.getSize(), this, district));
	}
	
	@Override
	public void insertEndOfRoundScreen()
	{
		insertAScreen(this.mainScreen = new EndOfRoundScreen(backgroundPanel.getSize(), this, currentRoundNumber));
	}
	
	
	// **********************************************
	// Start & Stop
	// **********************************************
	
	/**
	 * This method is called when the applet is started.
	 */
	public void start()
	{
		Output.show();
	}
	
	/**
	 * This method is called when the application is stopped.
	 */
	public void stop()
	{
		Output.show();
	}
}
