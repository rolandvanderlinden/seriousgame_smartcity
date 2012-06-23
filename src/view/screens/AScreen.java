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
}
