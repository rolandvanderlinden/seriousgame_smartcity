package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;

import model.data.District;
import model.data.TechImprovement;
import model.data.Technology;
import model.managers.DistrictManager;
import model.managers.ProductManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.components.BufferedImageJPanel;
import view.components.TranslucentBufferedImageJPanel;
import content.Content;
import controller.screens.ProductOfferController;

public class ProductOfferPanel extends TranslucentBufferedImageJPanel
{
	public final static String offerProductActionCommand = "offerproduct";
	public final static String cancelOfferActionCommand = "canceloffer";
	
	protected ProductOfferController productOfferController;
	protected District district;
	
	protected JLabel titleLabel, technologyLabel, improvementLabel;
	protected BufferedImageJPanel districtImage;
	protected RadioTechnologyPanel[] rtpanels;
	protected ButtonGroup radioButtonGroup;
	protected CheckImprovementPanel[] cipanels;
	protected JButton offerButton, cancelButton;
	
	public ProductOfferPanel(Dimension size, ProductOfferController productOfferController, District district)
	{
		super(Content.black, 0.5f);
		
		this.productOfferController = productOfferController;
		this.district = district;
		
		initialize(size);
	}
	
	private void initialize(Dimension size)
	{
		Technology[] technologies = ProductManager.getInstance().getTechnologies();
		TechImprovement[] improvements = ProductManager.getInstance().getImprovements();
		
		this.setSize(size);
		this.setLayout(null);
		FontMetrics mediumFontMetrics = this.getFontMetrics(Content.mediumFont);
		FontMetrics largeFontMetrics = this.getFontMetrics(Content.largeFont);
		
		//Text of labels
		String titleText = "New product offer for district " + this.district.getName();
		String technologyText = "Technology";
		String improvementText = "Technology Improvement";
		
		//Sizes
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		VectorF2 titlelabelsize = new VectorF2(largeFontMetrics.stringWidth(titleText), 30);
		VectorF2 techlabelsize = new VectorF2(mediumFontMetrics.stringWidth(technologyText), 20);
		VectorF2 improvlabelsize = new VectorF2(mediumFontMetrics.stringWidth(improvementText), 20);
		VectorF2 dimagesize = SizeCalculator.calculateSize(holdersize, 0.5f, 0.35f);
		VectorF2 rtpanelsize = SizeCalculator.calculateSize(holdersize, 0.075f, 0.15f);
		VectorF2 cipanelsize = rtpanelsize.clone();
		VectorF2 offerbuttonsize = new VectorF2(150, 30);
		VectorF2 cancelbuttonsize = new VectorF2(150, 30);
		
		//Locations
		VectorF2 titlelabelpos = LocationCalculator.calculateLocation(titlelabelsize, holdersize, LocationType.CENTER, 0.05f);
		VectorF2 techlabelpos = LocationCalculator.calculateLocation(techlabelsize, holdersize, 0.125f, 0.575f);
		VectorF2 improvlabelpos = LocationCalculator.calculateLocation(improvlabelsize, holdersize, 0.075f, 0.725f);
		VectorF2 dimagepos = LocationCalculator.calculateLocation(dimagesize, holdersize, LocationType.CENTER, 0.125f);
		VectorF2 firstrtpanelpos = LocationCalculator.calculateLocation(rtpanelsize, holdersize, 0.35f, 0.545f);
		VectorF2 firstcipanelpos = LocationCalculator.calculateLocation(cipanelsize, holdersize, 0.35f, 0.695f);
		VectorF2 offerbuttonpos = LocationCalculator.calculateLocation(offerbuttonsize, holdersize, 0.575f, 0.875f);
		VectorF2 cancelbuttonpos = LocationCalculator.calculateLocation(cancelbuttonsize, holdersize, 0.775f, 0.875f);
		
		//Add labels
		this.titleLabel = new JLabel(titleText);
		ComponentUtil.setComponentBounds(titleLabel, titlelabelsize, titlelabelpos);
		this.titleLabel.setForeground(Color.white);
		this.titleLabel.setFont(Content.largeFont);
		this.add(titleLabel);
		this.technologyLabel = new JLabel(technologyText);
		ComponentUtil.setComponentBounds(technologyLabel, techlabelsize, techlabelpos);
		this.technologyLabel.setForeground(Color.white);
		this.technologyLabel.setFont(Content.mediumFont);
		this.technologyLabel.setToolTipText("Select the technology to offer.");
		this.add(technologyLabel);
		this.improvementLabel = new JLabel(improvementText);
		ComponentUtil.setComponentBounds(improvementLabel, improvlabelsize, improvlabelpos);
		this.improvementLabel.setForeground(Color.white);
		this.improvementLabel.setFont(Content.mediumFont);
		this.improvementLabel.setToolTipText("Select one or more technology improvements (optional).");
		this.add(improvementLabel);
		
		//Add image
		this.districtImage = new BufferedImageJPanel(DistrictManager.getDistrictResourceInfo(district));
		ComponentUtil.setComponentBounds(districtImage, dimagesize, dimagepos);
		this.add(districtImage);
		
		//Add technology panels
		int rtpaneldistance = 100;
		this.rtpanels = new RadioTechnologyPanel[technologies.length];
		this.radioButtonGroup = new ButtonGroup();
		for(int i = 0; i < technologies.length; i++)
		{
			Technology technology = technologies[i];
			VectorF2 pos = new VectorF2(firstrtpanelpos.x + (i * rtpaneldistance), firstrtpanelpos.y);
			
			RadioTechnologyPanel rtpanel = new RadioTechnologyPanel(new Dimension((int)rtpanelsize.x, (int)rtpanelsize.y), technology, this.productOfferController, radioButtonGroup);
			ComponentUtil.setComponentBounds(rtpanel, rtpanelsize, pos);
			rtpanels[i] = rtpanel;
			this.add(rtpanel);
		}
		
		//Add improvement panels
		int cipaneldistance = 100;
		this.cipanels = new CheckImprovementPanel[improvements.length];
		for(int i = 0; i < improvements.length; i++)
		{
			TechImprovement improvement = improvements[i];
			VectorF2 pos = new VectorF2(firstcipanelpos.x + (i * cipaneldistance), firstcipanelpos.y);
			
			CheckImprovementPanel cipanel = new CheckImprovementPanel(new Dimension((int)cipanelsize.x, (int)cipanelsize.y), improvement, productOfferController);
			ComponentUtil.setComponentBounds(cipanel, cipanelsize, pos);
			cipanels[i] = cipanel;
			this.add(cipanel);
		}
		
		//Add buttons
		this.offerButton = new JButton("Offer product");
		ComponentUtil.setComponentBounds(offerButton, offerbuttonsize, offerbuttonpos);
		this.offerButton.addActionListener(productOfferController);
		this.offerButton.setToolTipText("The selected technology (and improvements) will be the product offer to district " + district.getName() + ".");
		this.offerButton.setActionCommand(offerProductActionCommand);
		this.offerButton.setEnabled(false);
		this.add(offerButton);
		this.cancelButton = new JButton("Cancel offer");
		ComponentUtil.setComponentBounds(cancelButton, cancelbuttonsize, cancelbuttonpos);
		this.cancelButton.addActionListener(productOfferController);
		this.cancelButton.setToolTipText("The offer will be cancelled. You will return to the overview screen.");
		this.cancelButton.setActionCommand(cancelOfferActionCommand);
		this.add(cancelButton);
	}

	public JButton getOfferButton()
	{
		return this.offerButton;
	}
}
