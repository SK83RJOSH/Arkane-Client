package com.sk83rsplace.arkane.GUI.components;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.GUI.IValuedString;
import com.sk83rsplace.arkane.client.Board;

/**
 * 
 * @author SK83RJOSH
 */
public class TextInputComponent extends Component implements IValuedString {
	private String placeholder = "", content = "";
	private boolean isSelected = false;
	private boolean password = false;
	private int tick;
	
	public TextInputComponent(String placeholder, String content, int x, int y) {
		this.placeholder = placeholder;
		this.content = content;
		set(x, y);
		setSize(257, 22);
	}
	
	public TextInputComponent(String placeholder, String content, int x, int y, boolean password) {
		this.placeholder = placeholder;
		this.content = content;
		this.password = password;
		set(x, y);
		setSize(257, 22);
	}
	
	public void render(GameContainer container, Graphics g) {
		String maskedContent = "";
		
		for(int index = 0; index < content.length(); index++)
			maskedContent += "*";
			
		
		g.setFont(container.getDefaultFont());
		
		g.setColor(Color.white);
		g.draw(new Rectangle(getX() - 1, getY(), getWidth() + 1, getHeight() + 1));
		if(isSelected)
			g.draw(new Rectangle(getX() - 2, getY() - 1, getWidth() + 3, getHeight() + 3));
		
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
	
	char lastchar;
	
	public void update(GameContainer container) {
		super.update(container);
		
		if(isActive() && Board.mouseButtons.wasReleased(0))
			isSelected = !isSelected;
		else if(!isActive() && Board.mouseButtons.wasReleased(0))
			isSelected = false;
		
		tick++;
		tick = tick % 60;
		
		if(Keyboard.getEventKeyState() && lastchar != Keyboard.getEventCharacter() && isSelected || Keyboard.isRepeatEvent() && isSelected) {
			if(Keyboard.getEventCharacter() != 0 && Keyboard.getEventKey() != Keyboard.KEY_BACK && Keyboard.getEventKey() != Keyboard.KEY_RETURN && Keyboard.getEventKey() != Keyboard.KEY_TAB) {
				content += (Keyboard.getEventCharacter());
				onValueChange();
			} else {
				switch(Keyboard.getEventKey()) {
					case Keyboard.KEY_BACK:
						if(content.length() > 0) {
							content = content.substring(0, content.length() - 1);	
							onValueChange();
						}
						break;
					case Keyboard.KEY_RETURN:
						isSelected = false;
						break;
					case Keyboard.KEY_TAB:
						getParent().selectNext();
						break;
				}	
			}

			lastchar = Keyboard.getEventCharacter();
		} else if(!Keyboard.getEventKeyState()) {
			lastchar = 9999;
		}
	}
	
	public String getValue() {
		return content;
	}
	
	public void setValue(String content) {
		this.content = content;
		onValueChange();
	}
	
	public void onValueChange() {
		System.out.println("Value changed, defaulted action.");
	}
	
	public boolean getSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
