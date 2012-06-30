package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;

import model.data.District;
import model.data.ProductOffer;
import model.managers.DistrictManager;
import model.managers.TeamManager;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.components.BufferedImageJPanel;
import view.components.TranslucentBufferedImageJPanel;
import content.Content;

public class DistrictPanel extends TranslucentBufferedImageJPanel
{
	protected District district;
	
	protected JLabel nameLabel, happinessLabel, roundOfferLabel;
	protected JButton offerButton;
	protected BufferedImageJPanel image;
	protected HappinessPanel happinessPanel;
	protected ProductOfferCollectionPanel roundOffersPanel;
	
	public DistrictPanel(Dimension size, District district, ActionListener actionListener)
	{
		super(Content.black, 0.5f);
		
		this.district = district;

		initialize(size, actionListener);
	}
	
	private void initialize(Dimension size, ActionListener actionListener)
	{
		HashMap<ProductOffer, Integer> roundOfferMap = TeamManager.getInstance().getRoundOfferMapForDistrict(district.getID());
		boolean roundOfferMapEmpty = roundOfferMap.isEmpty();
		int width = size.width;
		int height = size.height;
		FontMetrics mediumFontMetrics = this.getFontMetrics(Content.mediumFont);
		FontMetrics largeFontMetrics = this.getFontMetrics(Content.largeFont);
		
		this.setLayout(null);
		
		//Text
		String nameLabelText = "District: " + district.getName();
		String happinessLabelText = "Happiness of " + district.getName();
		String roundOfferLabelText = "This years' product introductions";
		
		//Sizes
		VectorF2 holdersize = new VectorF2(width, height);
		VectorF2 namelabelsize = SizeCalculator.calculateSize(new VectorF2(largeFontMetrics.stringWidth(nameLabelText), 30), holdersize);
		VectorF2 hlabelsize = SizeCalculator.calculateSize(new VectorF2(mediumFontMetrics.stringWidth(happinessLabelText), 20), holdersize);
		VectorF2 roundofferlabelsize = SizeCalculator.calculateSize(new VectorF2(mediumFontMetrics.stringWidth(roundOfferLabelText), 30), holdersize);
		VectorF2 offerbuttonsize = SizeCalculator.calculateSize(new VectorF2(150, 28), holdersize);
		VectorF2 imagesize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.22f);
		VectorF2 hpanelsize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.08f);
		VectorF2 roundofferpanelsize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.36f);
		
		//Locations
		VectorF2 namelabelpos = LocationCalculator.calculateLocation(namelabelsize, holdersize, LocationType.CENTER, 0.03f);
		VectorF2 hlabelpos = LocationCalculator.calculateLocation(hlabelsize, holdersize, LocationType.CENTER, 0.34f);
		VectorF2 roundofferlabelpos = LocationCalculator.calculateLocation(roundofferlabelsize, holdersize, LocationType.CENTER, 0.44f);
		VectorF2 offerbuttonpos = LocationCalculator.calculateLocation(offerbuttonsize, holdersize, LocationType.CENTER, 0.9f);
		VectorF2 imagepos = LocationCalculator.calculateLocation(imagesize, holdersize, LocationType.CENTER, 0.085f);
		VectorF2 hpanelpos = LocationCalculator.calculateLocation(hpanelsize, holdersize, LocationType.CENTER, 0.335f);
		VectorF2 roundofferppos = LocationCalculator.calculateLocation(roundofferpanelsize, holdersize, LocationType.CENTER, 0.495f);
		
		//Insert labels
		this.nameLabel = new JLabel(nameLabelText);
		ComponentUtil.setComponentBounds(nameLabel, namelabelsize, namelabelpos);
		this.nameLabel.setForeground(Color.white);
		this.nameLabel.setFont(Content.largeFont);
		this.add(nameLabel);
		this.happinessLabel = new JLabel(happinessLabelText);
		ComponentUtil.setComponentBounds(happinessLabel, hlabelsize, hlabelpos);
		this.happinessLabel.setForeground(Color.white);
		this.happinessLabel.setFont(Content.mediumFont);
		this.add(happinessLabel);
		this.roundOfferLabel = new JLabel(roundOfferLabelText);
		ComponentUtil.setComponentBounds(roundOfferLabel, roundofferlabelsize, roundofferlabelpos);
		this.roundOfferLabel.setForeground(Color.white);
		this.roundOfferLabel.setFont(Content.mediumFont);
		this.roundOfferLabel.setToolTipText("These are the product offers made in the current round. They will be processed when the round ends.");
		if(!roundOfferMapEmpty)
			this.add(roundOfferLabel);
		
		//Insert buttons
		this.offerButton = new JButton("Introduce product");
		ComponentUtil.setComponentBounds(offerButton, offerbuttonsize, offerbuttonpos);
		this.offerButton.setActionCommand(DistrictManager.districtOfferActionCommand + district.getID());
		this.offerButton.addActionListener(actionListener);
		this.offerButton.setToolTipText("Introduce a new product to district " + district.getName() + ".");
		this.add(offerButton);
		
		//Insert image
		this.image = new BufferedImageJPanel(DistrictManager.getDistrictResourceInfo(district));
		ComponentUtil.setComponentBounds(image, imagesize, imagepos);
		this.add(image);
		
		//Insert happinespanel
		this.happinessPanel = new HappinessPanel(new Dimension((int)hpanelsize.x, (int)hpanelsize.y));
		ComponentUtil.setComponentBounds(happinessPanel, hpanelsize, hpanelpos);
		happinessPanel.setToolTipText("The level of happiness of district " + district.getName() + ".");
		this.add(happinessPanel);
		
		//Insert productoffer panels
		this.roundOffersPanel = new ProductOfferCollectionPanel(new Dimension((int)roundofferpanelsize.x, (int)roundofferpanelsize.y), roundOfferMap);
		ComponentUtil.setComponentBounds(roundOffersPanel, roundofferpanelsize, roundofferppos);
		if(!roundOfferMapEmpty)
			this.add(roundOffersPanel);
	}
}
