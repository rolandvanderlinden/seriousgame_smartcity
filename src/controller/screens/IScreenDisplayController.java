package controller.screens;

import model.data.District;

public interface IScreenDisplayController
{
	public void removeCurrentScreen();
	
	public void insertOverviewScreen();
	public void insertProductOfferScreen(District district);
	public void insertEndOfRoundScreen();
	
	//TODO Should not be in this interface.
	public void increaseRoundNumber();
}
