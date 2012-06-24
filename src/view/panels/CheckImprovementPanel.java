package view.panels;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import model.data.TechImprovement;
import model.managers.ProductManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.components.BufferedImageJPanel;

public class CheckImprovementPanel extends JPanel
{
	protected TechImprovement improvement;
	
	protected BufferedImageJPanel improvementImage;
	protected JCheckBox checkbox;
	
	public CheckImprovementPanel(Dimension size, TechImprovement improvement)
	{
		super();
		
		this.improvement = improvement;
		
		initialize(size);
	}
	
	private void initialize(Dimension size)
	{
		this.setLayout(null);
		this.setSize(size);
		this.setOpaque(false);
		
		//Sizes
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		VectorF2 improvimagesize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.6f, 1.5f);
		VectorF2 checkboxsize = new VectorF2(20, 20);
		
		//Locations
		VectorF2 improvimagepos = LocationCalculator.calculateLocation(improvimagesize, holdersize, LocationType.CENTER, 0.1f);
		VectorF2 checkboxpos = LocationCalculator.calculateLocation(checkboxsize, holdersize, LocationType.CENTER, 0.9f);
		
		//Add components
		this.improvementImage = new BufferedImageJPanel(ProductManager.getTechImprovementResourceInfo(improvement));
		ComponentUtil.setComponentBounds(improvementImage, improvimagesize, improvimagepos);
		this.improvementImage.setToolTipText(improvement.getName());
		this.add(improvementImage);
		
		this.checkbox = new JCheckBox();
		ComponentUtil.setComponentBounds(checkbox, checkboxsize, checkboxpos);
		this.checkbox.setOpaque(false);
		this.checkbox.setToolTipText("Select technology improvement " + improvement.getName() + ". Multi-selection allowed.");
		this.add(checkbox);
		
	}
	
	public JCheckBox getCheckBox()
	{
		return this.checkbox;
	}
}