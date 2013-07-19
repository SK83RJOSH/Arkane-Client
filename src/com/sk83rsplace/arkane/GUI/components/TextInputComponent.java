package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.client.interfaces.IKeyListener;
import com.sk83rsplace.arkane.client.interfaces.IValuedString;

/**
 * 
 * @author SK83RJOSH
 */
public class TextInputComponent extends Component implements IValuedString, IKeyListener {
	private String placeholder = "", content = "";
	private boolean isSelected = false;
	private boolean password = false;
	private int tick;
	
	public TextInputComponent(String placeholder, String content, int x, int y) {
		this.placeholder = placeholder;
		this.content = content;
		set(x, y);
		setSize(258, 22);
	}
	
	public TextInputComponent(String placeholder, String content, int x, int y, boolean password) {
		this.placeholder = placeholder;
		this.content = content;
		this.password = password;
		set(x, y);
		setSize(258, 22);
	}
	
	public void render(GameContainer container, Graphics g) {
		String maskedContent = "";
		
		for(int index = 0; index < content.length(); index++)
			maskedContent += "*";

		g.setColor(Color.white);
				
		if(isSelected) {
			g.fill(new Rectangle(getX() - 2, getY() - 2, 2, getHeight() + 4)); //Left
			g.fill(new Rectangle(getX() + getWidth(), getY() - 2, 2, getHeight() + 4)); //Right	
			g.fill(new Rectangle(getX() - 2, getY() - 2, getWidth() + 4, 2)); //Top
			g.fill(new Rectangle(getX() - 2, getY() + getHeight(), getWidth() + 4, 2)); //Bottom
		} else {
			g.fill(new Rectangle(getX() - 1, getY() - 1, 1, getHeight() + 2)); //Left
			g.fill(new Rectangle(getX() + getWidth(), getY() - 1, 1, getHeight() + 2)); //Right
			g.fill(new Rectangle(getX() - 1, getY() - 1, getWidth() + 2, 1)); //Top
			g.fill(new Rectangle(getX() - 1, getY() + getHeight(), getWidth() + 2, 1)); //Bottom
		}
			
		g.setFont(container.getDefaultFont());
		
		g.setColor((isActive() && !isSelected ? new Color(0f, 0f, 0f, 0.70f) : (isSelected ? new Color(0f, 0f, 0f, 0.75f) : new Color(0f, 0f, 0f, 0.65f))));
		g.fill(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		
		g.setClip((int) getX() + 5, (int) getY(), getWidth(), getHeight());
			g.setColor((isSelected && content.length() > 0 ? Color.white : Color.gray));
			g.drawString((content.length() > 0 ? (password ? maskedContent : content) : placeholder), getX() + 6 - (container.getDefaultFont().getWidth((password ? maskedContent : content)) > getWidth() - 12 ? 12 + (container.getDefaultFont().getWidth((password ? maskedContent : content)) - getWidth()) : 0), getY() + 6);
			
			if(isSelected && tick > 20 && tick < 40) {
				g.setColor(Color.white);
				g.drawString("|", getX() + 4 + container.getDefaultFont().getWidth((password ? maskedContent : content)) - (container.getDefaultFont().getWidth((password ? maskedContent : content)) > getWidth() - 12 ? 12 + (container.getDefaultFont().getWidth((password ? maskedContent : content)) - getWidth()) : 0),  getY() + 6);
			}
		g.setClip(0, 0, container.getWidth(), container.getHeight());
	}
		
	public void update(GameContainer container) {
		super.update(container);
		
		if(isActive() && Board.mouseButtons.wasReleased(0))
			isSelected = !isSelected;
		else if(!isActive() && Board.mouseButtons.wasReleased(0))
			isSelected = false;
		
		tick++;
		tick = tick % 60;
	}
	
	public String getValue() {
		return content;
	}
	
	public void setValue(String content) {
		this.content = content;
		onValueChange();
	}
	
	public void onValueChange() {
		//To be overridden
	}
	
	public void keyPressed(int key, char c) {
		if(c > 37 && c < 128) { //Character isn't Control Character and is ASCII
			content += c;
			onValueChange();
		} else {
			switch(key) {
				case Input.KEY_BACK:
					if(content.length() > 0) {
						content = content.substring(0, content.length() - 1);	
						onValueChange();
					}
					break;
				case Input.KEY_RETURN:
					isSelected = false;
					break;
				case Input.KEY_TAB:
					getParent().selectNext();
					break;
			}	
		}
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
