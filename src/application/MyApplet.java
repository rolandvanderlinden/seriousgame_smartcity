package application;

import java.awt.Color;

import javax.swing.JApplet;
import javax.swing.JPanel;

import util.Output;

public class MyApplet extends JApplet
{
	// **********************************************
	// Fields
	// **********************************************
	
	public JPanel rootpanel;
	
	
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
		this.rootpanel = new JPanel(null);
		this.rootpanel.setSize(Config.appsize);
		this.rootpanel.setBackground(Color.white);
		this.rootpanel.setLocation(0, 0);
		this.add(this.rootpanel);
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
