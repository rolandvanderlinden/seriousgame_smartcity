package controller.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.data.District;

public class ProductOfferController implements ActionListener
{
	protected IScreenDisplayController screenDisplayController;
	protected District district;
	
	public ProductOfferController(IScreenDisplayController screenDisplayController, District district)
	{
		super();
		
		this.screenDisplayController = screenDisplayController;
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		// TODO Auto-generated method stub
		
	}
	
	

}
