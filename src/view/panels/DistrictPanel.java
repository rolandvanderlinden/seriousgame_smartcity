package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JButton;
import javax.swing.JLabel;

import model.data.District;
import model.managers.DistrictManager;
import model.util.ResourceInfo;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.components.BufferedImageJPanel;
import view.components.TranslucentBufferedImageJPanel;
import content.Content;
import content.image.ImageRef;

public class DistrictPanel extends TranslucentBufferedImageJPanel
{
	protected District district;
	
	protected JLabel nameLabel, happinessLabel, roundOfferLabel, acceptedOfferLabel;
	protected JButton offerButton;
	protected BufferedImageJPanel image;
	protected HappinessPanel happinessPanel;
	protected ProductOfferPanel roundOffersPanel, acceptedOffersPanel;
	
	public DistrictPanel(Dimension size, District district)
	{
		super(Content.black, 0.5f);
		
		this.district = district;

		initialize(size);
	}
	
	private void initialize(Dimension size)
	{
		int width = size.width;
		int height = size.height;
		FontMetrics smallFontMetrics = this.getFontMetrics(Content.smallFont);
		FontMetrics mediumFontMetrics = this.getFontMetrics(Content.mediumFont);
		
		this.setLayout(null);
		
		//Text
		String nameLabelText = district.getName();
		String happinessLabelText = "Happiness level";
		String roundOfferLabelText = "Unprocessed product offers";
		String acceptedOfferLabelText = "Accepted product offers";
		
		//Sizes
		VectorF2 holdersize = new VectorF2(width, height);
		VectorF2 namelabelsize = SizeCalculator.calculateSize(new VectorF2(mediumFontMetrics.stringWidth(nameLabelText), 30), holdersize);
		VectorF2 hlabelsize = SizeCalculator.calculateSize(new VectorF2(smallFontMetrics.stringWidth(happinessLabelText), 20), holdersize);
		VectorF2 roundofferlabelsize = SizeCalculator.calculateSize(new VectorF2(mediumFontMetrics.stringWidth(roundOfferLabelText), 30), holdersize);
		VectorF2 accofferlabelsize = SizeCalculator.calculateSize(new VectorF2(mediumFontMetrics.stringWidth(acceptedOfferLabelText), 30), holdersize);
		VectorF2 offerbuttonsize = SizeCalculator.calculateSize(new VectorF2(110, 28), holdersize);
		VectorF2 imagesize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.18f);
		VectorF2 hpanelsize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.025f);
		VectorF2 roundofferpanelsize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.16f);
		VectorF2 accofferpanelsize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.32f);
		
		//Locations
		VectorF2 namelabelpos = LocationCalculator.calculateLocation(namelabelsize, holdersize, LocationType.CENTER, 0.02f);
		VectorF2 hlabelpos = LocationCalculator.calculateLocation(hlabelsize, holdersize, LocationType.CENTER, 0.2475f);
		VectorF2 roundofferlabelpos = LocationCalculator.calculateLocation(roundofferlabelsize, holdersize, LocationType.CENTER, 0.37f);
		VectorF2 accofferlabelpos = LocationCalculator.calculateLocation(accofferlabelsize, holdersize, LocationType.CENTER, 0.6f);
		VectorF2 offerbuttonpos = LocationCalculator.calculateLocation(offerbuttonsize, holdersize, LocationType.CENTER, 0.31f);
		VectorF2 imagepos = LocationCalculator.calculateLocation(imagesize, holdersize, LocationType.CENTER, 0.06f);
		VectorF2 hpanelpos = LocationCalculator.calculateLocation(hpanelsize, holdersize, LocationType.CENTER, 0.25f);
		VectorF2 roundofferppos = LocationCalculator.calculateLocation(roundofferpanelsize, holdersize, LocationType.CENTER, 0.41f);
		VectorF2 accofferppos = LocationCalculator.calculateLocation(accofferpanelsize, holdersize, LocationType.CENTER, 0.64f);
		
		//Insert labels
		this.nameLabel = new JLabel(nameLabelText);
		ComponentUtil.setComponentBounds(nameLabel, namelabelsize, namelabelpos);
		this.nameLabel.setForeground(Color.white);
		this.nameLabel.setFont(Content.mediumFont);
		this.add(nameLabel);
		this.happinessLabel = new JLabel(happinessLabelText);
		ComponentUtil.setComponentBounds(happinessLabel, hlabelsize, hlabelpos);
		this.happinessLabel.setForeground(Color.white);
		this.happinessLabel.setFont(Content.smallFont);
		this.add(happinessLabel);
		this.roundOfferLabel = new JLabel(roundOfferLabelText);
		ComponentUtil.setComponentBounds(roundOfferLabel, roundofferlabelsize, roundofferlabelpos);
		this.roundOfferLabel.setForeground(Color.white);
		this.roundOfferLabel.setFont(Content.mediumFont);
		this.add(roundOfferLabel);
		this.acceptedOfferLabel = new JLabel(acceptedOfferLabelText);
		ComponentUtil.setComponentBounds(acceptedOfferLabel, accofferlabelsize, accofferlabelpos);
		this.acceptedOfferLabel.setForeground(Color.white);
		this.acceptedOfferLabel.setFont(Content.mediumFont);
		this.add(acceptedOfferLabel);
		
		//Insert buttons
		this.offerButton = new JButton("Offer product");
		ComponentUtil.setComponentBounds(offerButton, offerbuttonsize, offerbuttonpos);
		this.add(offerButton);
		
		//Insert image
		this.image = new BufferedImageJPanel(DistrictManager.getDistrictResourceInfo(district));
		ComponentUtil.setComponentBounds(image, imagesize, imagepos);
		this.add(image);
		
		//Insert happinespanel
		this.happinessPanel = new HappinessPanel(new Dimension((int)hpanelsize.x, (int)hpanelsize.y));
		ComponentUtil.setComponentBounds(happinessPanel, hpanelsize, hpanelpos);
		this.add(happinessPanel);
		
		//Insert productoffer panels
		this.roundOffersPanel = new ProductOfferPanel(new Dimension((int)roundofferpanelsize.x, (int)roundofferpanelsize.y));
		ComponentUtil.setComponentBounds(roundOffersPanel, roundofferpanelsize, roundofferppos);
		this.add(roundOffersPanel);
		this.acceptedOffersPanel = new ProductOfferPanel(new Dimension((int)accofferpanelsize.x, (int)accofferpanelsize.y));
		ComponentUtil.setComponentBounds(acceptedOffersPanel, accofferpanelsize, accofferppos);
		this.add(acceptedOffersPanel);
	}
}
