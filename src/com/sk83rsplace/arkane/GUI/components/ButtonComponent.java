package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.GUI.IClickable;
import com.sk83rsplace.arkane.client.Board;

/**
 * 
 * @author SK83RJOSH
 */
public class ButtonComponent extends Component implements IClickable {
	private String text;
	private boolean isPressed;
	private Image buttonImage;
	
	public ButtonComponent(String text, int x, int y) {
		this.text = text;
		
		set(x, y);
		
		try {
			buttonImage = new Image("res/button.png");
			
			setSize(buttonImage.getWidth(), buttonImage.getHeight());
		} catch (SlickException e) {
			e.printStackTrace();
		}	
	}
	
	public void render(GameContainer container, Graphics g) {
		g.setFont(container.getDefaultFont());
		
		float val = 1.00f - (isActive() ? (Board.mouseButtons.isDown(0) ? 0.20f : 0.10f) : 0.00f);
		int offset = (isActive() ? (Board.mouseButtons.isDown(0) ? 1 : 0) : 0);
		
		g.drawImage(buttonImage, getX() + 1, getY() + 1, new Color(val, val, val));
		
		if(!(Board.mouseButtons.isDown(0)) && isActive() || !isActive())
			g.drawImage(buttonImage, getX(), getY(), new Color(val, val, val));

		g.setColor(Color.black);
		g.drawString(text, (3 + offset) + getX() + (getWidth() / 2) - (container.getDefaultFont().getWidth(text) / 2), (3 + offset) + getY() + (getHeight() / 2) - (container.getDefaultFont().getHeight(text) / 2));
	
		g.setColor(Color.white);
		g.drawString(text, (1 + offset)  + getX() + (getWidth() / 2) - (container.getDefaultFont().getWidth(text) / 2), (1 + offset) + getY() + (getHeight() / 2) - (container.getDefaultFont().getHeight(text) / 2));		
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
		System.out.println("Default Action!");
	}
}
