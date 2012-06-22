package view.screens;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

import model.util.VectorF2;

public abstract class AScreen extends JPanel
{
	public AScreen(Dimension size)
	{
		super();
		
		initialize(size);
	}
	
	private void initialize(Dimension size)
	{
		this.setLayout(null);
		this.setSize(size);
		this.setOpaque(false);
	}
	
	protected void setComponentBounds(Component component, VectorF2 size, VectorF2 location)
	{
		component.setSize((int)size.x, (int)size.y);
		component.setLocation((int)location.x, (int)location.y);
	}

}
