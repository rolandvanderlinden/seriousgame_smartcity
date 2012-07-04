package content;

import java.awt.Font;

import model.util.ResourceInfo;
import content.icon.IconRef;
import content.image.ImageRef;

public class Content
{
	//Images
	public final static ResourceInfo background = new ResourceInfo("background.png", ImageRef.class);
	public final static ResourceInfo black = new ResourceInfo("black.png", ImageRef.class);
	public final static ResourceInfo white = new ResourceInfo("white.png", ImageRef.class);
	public final static ResourceInfo lightblue = new ResourceInfo("lightblue.png", ImageRef.class);
	public final static ResourceInfo happinessBar = new ResourceInfo("happinessbar.png", ImageRef.class);
	public final static ResourceInfo happinessOverlay = new ResourceInfo("happinessoverlay.png", ImageRef.class);
	public final static ResourceInfo uparrow = new ResourceInfo("uparrow.png", ImageRef.class);
	public final static ResourceInfo downarrow = new ResourceInfo("downarrow.png", ImageRef.class);
	public final static ResourceInfo logo = new ResourceInfo("logo.png", ImageRef.class);
	
	//Icons
	public final static ResourceInfo leftArrow = new ResourceInfo("leftarrow.png", IconRef.class);
	public final static ResourceInfo rightArrow = new ResourceInfo("rightarrow.png", IconRef.class);
	
	//Fonts
	public final static Font smallFont = new Font("Arial", Font.PLAIN, 10);
	public final static Font mediumFont = new Font("Arial", Font.PLAIN, 14);
	public final static Font largeFont = new Font("Arial", Font.PLAIN, 18);
	public final static Font hugeFont = new Font("Arial", Font.PLAIN, 24);
	public final static Font hugeBoldFont = new Font("Arial", Font.BOLD, 24);
}
