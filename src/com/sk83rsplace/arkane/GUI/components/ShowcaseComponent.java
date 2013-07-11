package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.GUI.Images;
import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.client.interfaces.IClickable;

public class ShowcaseComponent extends Component implements IClickable {
	private Image backgroundImage = Images.tileSelector;
	private Image showcasedItem = null;
	private boolean isToggled;
	
	public ShowcaseComponent(Image showcasedItem, int x, int y) {
		setShowcased(showcasedItem);
		set(x, y);
		setSize(backgroundImage.getWidth() / 3, backgroundImage.getHeight());
	}
	
	public ShowcaseComponent(Image backgroundImage, Image showcasedItem, int x, int y) {
		setBackground(backgroundImage);
		setShowcased(showcasedItem);
		set(x, y);
		setSize(backgroundImage.getWidth() / 3, backgroundImage.getHeight());
	}
	
	public void render(GameContainer container, Graphics g) {
		if(isActive() && !isToggled) {
			if(Board.mouseButtons.isDown(0)) {
				g.drawImage(backgroundImage.getSubImage(getWidth() * 2, 0, getWidth(), getHeight()), getX(), getY());
			} else {
				g.drawImage(backgroundImage.getSubImage(getWidth(), 0, getWidth(), getHeight()), getX(), getY());				
			}
		} else if(isToggled) {
			g.drawImage(backgroundImage.getSubImage(0, 0, getWidth(), getHeight()), getX(), getY());
		}
		
		g.drawImage(showcasedItem, getX() + ((getWidth() - showcasedItem.getWidth()) / 2), getY() + ((getHeight() - showcasedItem.getHeight()) / 2));
	}

	public void setShowcased(Image showcasedItem) {
		if(showcasedItem.getWidth() == 32 && showcasedItem.getHeight() <= 32 || showcasedItem.getWidth() <= 32 && showcasedItem.getHeight() == 32) {
			this.showcasedItem = showcasedItem;
		} else {
			float scale = 0f;
			
			if(showcasedItem.getWidth() > showcasedItem.getHeight()) {
				scale = 32f / showcasedItem.getWidth();				
			} else {
				scale = 32f / showcasedItem.getHeight();		
			}
			
			this.showcasedItem = showcasedItem.getScaledCopy(scale);
		}
	}

	public void setBackground(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	
	public void update(GameContainer container) {
		super.update(container);
		
		if(isActive() && Board.mouseButtons.wasReleased(0)) {
			click();
		}
	}
	
	private void click() {
		setToggled(!isToggled);
		onClick();
	}
	
	public void setToggled(boolean isToggled) {
		this.isToggled = isToggled;
	}
	
	public boolean getToggled() {
		return isToggled;
	}
	
	public void onClick() {
		//To be overriden
	}
}
