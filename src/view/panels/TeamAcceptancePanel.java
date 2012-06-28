package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.ArrayList;

import javax.swing.JLabel;

import model.data.AcceptanceData;
import model.data.Team;
import model.util.VectorF2;
import util.ComponentUtil;
import util.LocationCalculator;
import util.LocationCalculator.LocationType;
import util.SizeCalculator;
import view.components.TranslucentBufferedImageJPanel;
import content.Content;

public class TeamAcceptancePanel extends TranslucentBufferedImageJPanel
{
	protected Team team;
	
	protected JLabel nameLabel, fundsLabel, fundsAdditionLabel;
	protected AcceptanceCollectionPanel acceptanceCollectionPanel;
	
	public TeamAcceptancePanel(Dimension size, Team team, ArrayList<AcceptanceData> data, int fundschange)
	{
		super(Content.black, 0.5f);
		
		this.team = team;
		
		initialize(size, data, fundschange);
	}

	private void initialize(Dimension size, ArrayList<AcceptanceData> data, int fundschange)
	{
		FontMetrics mediumFontMetrics = this.getFontMetrics(Content.mediumFont);
		
		this.setLayout(null);
		
		//Text
		String nameLabelText = team.getName();
		String fundsLabelText = "Funds: ";
		String fundsAdditionLabelText = "" + fundschange;
		if(fundschange > 0)
			fundsAdditionLabelText = "+" + fundsAdditionLabelText;
			
		//Sizes
		VectorF2 holdersize = new VectorF2(size.width, size.height);
		VectorF2 namelabelsize = SizeCalculator.calculateSize(new VectorF2(mediumFontMetrics.stringWidth(nameLabelText), 30), holdersize);
		VectorF2 flabelsize = SizeCalculator.calculateSize(new VectorF2(mediumFontMetrics.stringWidth(fundsLabelText), 30), holdersize);
		VectorF2 faddlabelsize = SizeCalculator.calculateSize(new VectorF2(mediumFontMetrics.stringWidth(fundsAdditionLabelText), 30), holdersize);
		VectorF2 acceptancepsize = SizeCalculator.calculateSize(holdersize, 0.8f, 0.5f);
		
		//Locations
		VectorF2 namelabelpos = LocationCalculator.calculateLocation(namelabelsize, holdersize, LocationType.CENTER, 0.05f);
		VectorF2 flabelpos = LocationCalculator.calculateLocation(flabelsize, holdersize, LocationType.CENTER, 0.8f);
		VectorF2 faddlabelpos = LocationCalculator.calculateLocation(faddlabelsize, holdersize, LocationType.FIVE_EIGHTS, 0.8f);
		VectorF2 acceptanceppos = LocationCalculator.calculateLocation(acceptancepsize, holdersize, LocationType.CENTER, 0.15f);
	
		//Insert labels
		this.nameLabel = new JLabel(nameLabelText);
		ComponentUtil.setComponentBounds(nameLabel, namelabelsize, namelabelpos);
		this.nameLabel.setForeground(Color.white);
		this.nameLabel.setFont(Content.mediumFont);
		this.add(nameLabel);
		this.fundsLabel = new JLabel(fundsLabelText);
		ComponentUtil.setComponentBounds(fundsLabel, flabelsize, flabelpos);
		this.fundsLabel.setForeground(Color.white);
		this.fundsLabel.setFont(Content.mediumFont);
		this.add(fundsLabel);
		this.fundsAdditionLabel = new JLabel(fundsAdditionLabelText);
		ComponentUtil.setComponentBounds(fundsAdditionLabel, faddlabelsize, faddlabelpos);
		if(fundschange < 0)
			this.fundsAdditionLabel.setForeground(Color.red);
		else if (fundschange > 0)
			this.fundsAdditionLabel.setForeground(Color.green);
		else
			fundsAdditionLabel.setForeground(Color.white);
		this.fundsAdditionLabel.setFont(Content.mediumFont);
		this.add(fundsAdditionLabel);
		
		//Insert acceptance data panel
		this.acceptanceCollectionPanel = new AcceptanceCollectionPanel(new Dimension((int)acceptancepsize.x, (int)acceptancepsize.y), data);
		ComponentUtil.setComponentBounds(acceptanceCollectionPanel, acceptancepsize, acceptanceppos);
		this.add(acceptanceCollectionPanel);
	}
}