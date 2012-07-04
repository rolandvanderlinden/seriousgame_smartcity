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
import controller.contentloader.ImageLoader;
import controller.screens.ProductOfferController;

public class ProductOfferPanel extends TranslucentBufferedImageJPanel
{
	public final static String offerProductActionCommand = "offerproduct";
	public final static String cancelOfferActionCommand = "canceloffer";
	public final static String lessActionCommand = "less";
	public final static String moreActionCommand = "more";
	
	protected ProductOfferController productOfferController;
	protected District district;
	
	protected JLabel titleLabel, technologyLabel, improvementLabel, quantityLabel, numberLabel;
	protected BufferedImageJPanel districtImage;
	protected RadioTechnologyPanel[] rtpanels;
	protected ButtonGroup radioButtonGroup;
	protected CheckImprovementPanel[] cipanels;
	protected JButton offerButton, cancelButton, lessButton, moreButton;
	
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
		String titleText = "New product introduction for district " + this.district.getName();
		String technologyText = "Technology";
		String improvementText = "Technology Improvement";
		String quantityText = "Quantity";
		
		//Sizes
		VectorF2 holdersize = new VectorF2(this.getWidth(), this.getHeight());
		VectorF2 titlelabelsize = new VectorF2(largeFontMetrics.stringWidth(titleText), 30);
		VectorF2 techlabelsize = new VectorF2(mediumFontMetrics.stringWidth(technologyText), 20);
		VectorF2 improvlabelsize = new VectorF2(mediumFontMetrics.stringWidth(improvementText), 20);
		VectorF2 quantitylabelsize = new VectorF2(mediumFontMetrics.stringWidth(quantityText), 20);
		VectorF2 dimagesize = SizeCalculator.calculateSize(holdersize, 0.45f, 0.35f);
		VectorF2 rtpanelsize = SizeCalculator.calculateSize(holdersize, 0.075f, 0.14f);
		VectorF2 cipanelsize = rtpanelsize.clone();
		VectorF2 offerbuttonsize = new VectorF2(150, 30);
		VectorF2 cancelbuttonsize = new VectorF2(150, 30);
		VectorF2 numberlabelsize = new VectorF2(20, 20);
		VectorF2 morelessbuttonsize = new VectorF2(30,30);
		
		//Locations
		VectorF2 titlelabelpos = LocationCalculator.calculateLocation(titlelabelsize, holdersize, LocationType.CENTER, 0.05f);
		VectorF2 techlabelpos = LocationCalculator.calculateLocation(techlabelsize, holdersize, 0.125f, 0.52f);
		VectorF2 improvlabelpos = LocationCalculator.calculateLocation(improvlabelsize, holdersize, 0.075f, 0.67f);
		VectorF2 quantitylabelpos = LocationCalculator.calculateLocation(quantitylabelsize, holdersize, 0.13f, 0.82f);
		VectorF2 dimagepos = LocationCalculator.calculateLocation(dimagesize, holdersize, LocationType.CENTER, 0.11f);
		VectorF2 firstrtpanelpos = LocationCalculator.calculateLocation(rtpanelsize, holdersize, 0.35f, 0.49f);
		VectorF2 firstcipanelpos = LocationCalculator.calculateLocation(cipanelsize, holdersize, 0.35f, 0.64f);
		VectorF2 offerbuttonpos = LocationCalculator.calculateLocation(offerbuttonsize, holdersize, 0.6f, 0.9f);
		VectorF2 cancelbuttonpos = LocationCalculator.calculateLocation(cancelbuttonsize, holdersize, 0.8f, 0.9f);
		VectorF2 numberlabelpos = LocationCalculator.calculateLocation(numberlabelsize, holdersize, 0.412f, 0.815f);
		VectorF2 lessbuttonpos = LocationCalculator.calculateLocation(morelessbuttonsize, holdersize, 0.36f, 0.81f);
		VectorF2 morebuttonpos = LocationCalculator.calculateLocation(morelessbuttonsize, holdersize, 0.44f, 0.81f);
		
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
		this.technologyLabel.setToolTipText("Select the technology to be introduced.");
		this.add(technologyLabel);
		this.improvementLabel = new JLabel(improvementText);
		ComponentUtil.setComponentBounds(improvementLabel, improvlabelsize, improvlabelpos);
		this.improvementLabel.setForeground(Color.white);
		this.improvementLabel.setFont(Content.mediumFont);
		this.improvementLabel.setToolTipText("Select one or more technology improvements (optional).");
		this.add(improvementLabel);
		this.quantityLabel = new JLabel(quantityText);
		ComponentUtil.setComponentBounds(quantityLabel, quantitylabelsize, quantitylabelpos);
		this.quantityLabel.setForeground(Color.white);
		this.quantityLabel.setFont(Content.mediumFont);
		this.quantityLabel.setToolTipText("The quantity of the products of this type you want to introduce.");
		this.add(quantityLabel);
		
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
		
		//Add quantity components
		this.numberLabel = new JLabel();
		ComponentUtil.setComponentBounds(numberLabel, numberlabelsize, numberlabelpos);
		this.numberLabel.setForeground(Color.white);
		this.numberLabel.setFont(Content.mediumFont);
		this.add(numberLabel);
		this.lessButton = new JButton(ImageLoader.Instance().loadImageIcon(Content.leftArrow));
		ComponentUtil.setComponentBounds(lessButton, morelessbuttonsize, lessbuttonpos);
		this.lessButton.addActionListener(productOfferController);
		this.lessButton.setToolTipText("Decrease the number of products you want to introduce.");
		this.lessButton.setActionCommand(lessActionCommand);
		this.lessButton.setEnabled(false);
		this.add(lessButton);
		this.moreButton = new JButton(ImageLoader.Instance().loadImageIcon(Content.rightArrow));
		ComponentUtil.setComponentBounds(moreButton, morelessbuttonsize, morebuttonpos);
		this.moreButton.addActionListener(productOfferController);
		this.moreButton.setToolTipText("Increase the number of products you want to introduce.");
		this.moreButton.setActionCommand(moreActionCommand);
		this.add(moreButton);
		
		//Add buttons
		this.offerButton = new JButton("Introduce product");
		ComponentUtil.setComponentBounds(offerButton, offerbuttonsize, offerbuttonpos);
		this.offerButton.addActionListener(productOfferController);
		this.offerButton.setToolTipText("The selected technology (and improvements) will be the introduced product to district " + district.getName() + ".");
		this.offerButton.setActionCommand(offerProductActionCommand);
		this.offerButton.setEnabled(false);
		this.add(offerButton);
		this.cancelButton = new JButton("Cancel");
		ComponentUtil.setComponentBounds(cancelButton, cancelbuttonsize, cancelbuttonpos);
		this.cancelButton.addActionListener(productOfferController);
		this.cancelButton.setToolTipText("The product introduction will be cancelled. You will return to the overview screen.");
		this.cancelButton.setActionCommand(cancelOfferActionCommand);
		this.add(cancelButton);
		
		this.setQuantity(1);
	}
	
	public void setQuantity(int quantity)
	{
		this.numberLabel.setText("" + quantity);
		this.numberLabel.setToolTipText("You plan to introduce " + numberLabel.getText() + " types of this product.");
	}
	
	public JButton getOfferButton()
	{
		return this.offerButton;
	}
	
	public JButton getLessButton()
	{
		return this.lessButton;
	}
	
	public JButton getMoreButton()
	{
		return this.moreButton;
	}

}
