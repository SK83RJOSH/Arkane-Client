package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.sk83rsplace.arkane.GUI.Component;

/**
 * 
 * @author SK83RJOSH
 */
public class ImageComponent extends Component {
	private Image image;
	
	public ImageComponent(int x, int y, String res) {
		try {
			image = new Image(res);

			set(x, y);
			setSize(image.getWidth(), image.getHeight());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public void render(GameContainer container, Graphics g) {
		g.drawImage(image, getX(), getY());
	}
}
