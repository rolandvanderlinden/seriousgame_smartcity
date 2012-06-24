package application;

import javax.swing.JApplet;

import controller.screens.IScreenDisplayController;

import model.data.District;
import model.managers.DistrictManager;
import model.managers.ProductManager;
import util.Output;
import view.base.BackgroundPanel;
import view.screens.AScreen;
import view.screens.OverviewScreen;
import view.screens.ProductOfferScreen;

public class HZApplet extends JApplet implements IScreenDisplayController
{
	// **********************************************
	// Fields
	// **********************************************
	
	public BackgroundPanel backgroundPanel;
	public AScreen mainScreen;
	
	
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
	
	@Override
	public void insertOverviewScreen()
	{
		this.remove(backgroundPanel);
		
		this.mainScreen = new OverviewScreen(backgroundPanel.getSize(), this, DistrictManager.getInstance().getDistricts());
		this.add(mainScreen);
		this.add(backgroundPanel);
		
		this.rootPane.revalidate();
		this.rootPane.repaint();
	}
	
	@Override
	public void insertProductOfferScreen(District district)
	{
		this.remove(backgroundPanel);
		
		this.mainScreen = new ProductOfferScreen(backgroundPanel.getSize(), this, district);
		this.add(mainScreen);
		this.add(backgroundPanel);
		
		this.rootPane.revalidate();
		this.rootPane.repaint();
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
