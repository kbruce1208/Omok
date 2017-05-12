import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JLabel;


public class ImagePanel extends Canvas{
	Image image;
	Toolkit toolkit = getToolkit();
	//JLabel label1 = new JLabel("Â¾´Ù.");
	String userName;
	
	ImagePanel(String image) {
		this.image = toolkit.getImage(image);
	}
	
	public void setName(String name) {
		this.userName = name;
	}
	
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, this);
		g.drawString(userName, 80, 45);
	}
}
