package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.GUI.Images;
import com.sk83rsplace.arkane.GUI.interfaces.IClickable;
import com.sk83rsplace.arkane.client.Board;

/**
 * 
 * @author SK83RJOSH
 */
public class ButtonComponent extends Component implements IClickable {
	private String text = "";
	private boolean isPressed = false, isStatic = false;
	private Image buttonImage = Images.defaultImage;
	
	public ButtonComponent(String text, int x, int y) {
		setText(text);		
		set(x, y);
		setSize(buttonImage.getWidth() / 3, buttonImage.getHeight());
	}
	
	public ButtonComponent(Image buttonImage, int x, int y) {
		setStatic(true);
		setImage(buttonImage);
		set(x, y);
		setSize(buttonImage.getWidth() / 3, buttonImage.getHeight());
	}
	
	public ButtonComponent(String text, Image buttonImage, int x, int y) {
		setText(text);
		setImage(buttonImage);
		set(x, y);
		setSize(buttonImage.getWidth() / 3, buttonImage.getHeight());
	}
	
	public void render(GameContainer container, Graphics g) {
		g.setFont(container.getDefaultFont());
		
		int offset = (isActive() ? (Board.mouseButtons.isDown(0) ? 1 : 0) : 0);
		
		
		if(!isActive()) {
			g.drawImage(buttonImage.getSubImage(0, 0, getWidth(), getHeight()), getX(), getY());
		} else {
			if(Board.mouseButtons.isDown(0)) {
				g.drawImage(buttonImage.getSubImage(getWidth() * 2, 0, getWidth(), getHeight()), getX() + (isStatic ? 0 : 1), getY() + (isStatic ? 0 : 1));
			} else {
				g.drawImage(buttonImage.getSubImage(getWidth(), 0, getWidth(), getHeight()), getX(), getY());
			}
		}
		
		g.setColor(Color.white);
		g.drawString(text, (1 + offset)  + getX() + (getWidth() / 2) - (container.getDefaultFont().getWidth(text) / 2), (1 + offset) + getY() + (getHeight() / 2) - (container.getDefaultFont().getLineHeight() / 2));		
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Image getImage() {
		return buttonImage;
	}
	
	public void setImage(Image buttonImage) {
		this.buttonImage = buttonImage;
	}
	
	public boolean getStatic() {
		return isStatic;
	}
	
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;		
	}
	
	public void update(GameContainer container) {
		super.update(container);
		
		if(isActive() && Board.mouseButtons.wasReleased(0) && !isPressed) {
			isPressed = true;
			click();	
		} else if(isPressed && Board.mouseButtons.isDown(0)) {
			isPressed = false;
		}
	}
	
	private void click() {		
		try {
			new Sound("res/sounds/click.ogg").play();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		onClick();
	}

	public void onClick() {
		//To be overridden
	}
}
