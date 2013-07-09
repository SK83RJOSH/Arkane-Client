package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.sk83rsplace.arkane.GUI.Component;

/**
 * 
 * @author SK83RJOSH
 */
public class ImageComponent extends Component {
	private Image image;
	
	public ImageComponent(int x, int y, Image image) {
		setImage(image);
		set(x, y);
		setSize(image.getWidth(), image.getHeight());
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public void render(GameContainer container, Graphics g) {
		g.drawImage(image, getX(), getY());
	}
}
