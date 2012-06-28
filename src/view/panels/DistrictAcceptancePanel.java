package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;

import model.data.AcceptanceData;
import model.data.District;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.components.BufferedImageJPanel;
import view.components.TranslucentBufferedImageJPanel;
import content.Content;

public class DistrictAcceptancePanel extends TranslucentBufferedImageJPanel
{
	protected District district;
	
	protected JLabel nameLabel, happinessLabel;
	protected BufferedImageJPanel happinessimage;
	protected AcceptanceCollectionPanel acceptanceCollectionPanel;
	
	public DistrictAcceptancePanel(Dimension size, District district, ArrayList<AcceptanceData> data)
	{
		super(Content.black, 0.5f);
		
		this.district = district;
		
		initialize(size, data);
	}

	private void initialize(Dimension size, ArrayList<AcceptanceData> data)
	{
		this.setLayout(null);
		this.setSize(size);
		FontMetrics mediumFontMetrics = this.getFontMetrics(Content.mediumFont);
		
		//Text
		String nameLabelText = district.getName();
		String happinessLabelText = "Happiness change";

		//Sizes
		VectorF2 holdersize = new VectorF2(size.width, size.height);
		VectorF2 namelabelsize = SizeCalculator.calculateSize(new VectorF2(mediumFontMetrics.stringWidth(nameLabelText), 30), holdersize);
		VectorF2 hlabelsize = SizeCalculator.calculateSize(new VectorF2(mediumFontMetrics.stringWidth(happinessLabelText), 30), holdersize);
		VectorF2 acceptancepsize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.5f);
		
		//Locations
		VectorF2 namelabelpos = LocationCalculator.calculateLocation(namelabelsize, holdersize, LocationType.CENTER, 0.05f);
		VectorF2 hlabelpos = LocationCalculator.calculateLocation(hlabelsize, holdersize, LocationType.CENTER, 0.8f);
		VectorF2 acceptanceppos = LocationCalculator.calculateLocation(acceptancepsize, holdersize, LocationType.CENTER, 0.15f);
	
		//Insert labels
		this.nameLabel = new JLabel(nameLabelText);
		ComponentUtil.setComponentBounds(nameLabel, namelabelsize, namelabelpos);
		this.nameLabel.setForeground(Color.white);
		this.nameLabel.setFont(Content.mediumFont);
		this.add(nameLabel);
		this.happinessLabel = new JLabel(happinessLabelText);
		ComponentUtil.setComponentBounds(happinessLabel, hlabelsize, hlabelpos);
		this.happinessLabel.setForeground(Color.white);
		this.happinessLabel.setFont(Content.mediumFont);
		this.add(happinessLabel);
		
		//Insert acceptance data panel
		this.acceptanceCollectionPanel = new AcceptanceCollectionPanel(new Dimension((int)acceptancepsize.x, (int)acceptancepsize.y), data);
		ComponentUtil.setComponentBounds(acceptanceCollectionPanel, acceptancepsize, acceptanceppos);
		this.add(acceptanceCollectionPanel);
	}
}
