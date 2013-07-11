package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.GUI.Fonts;
import com.sk83rsplace.arkane.client.interfaces.IValuedString;

/**
 * 
 * @author SK83RJOSH
 */
public class TextComponent extends Component implements IValuedString {
	private String content = "";
	private Color textColor = Color.white;
	private TrueTypeFont font = Fonts.normalPoint;
	
	public TextComponent(String content, Color textColor, int x, int y) {
		this.content = content;
		this.textColor = textColor;
		set(x, y);
	}
	
	public TextComponent(String content, Color textColor, TrueTypeFont font, int x, int y) {
		this.content = content;
		this.textColor = textColor;
		set(x, y);
		this.font = font;
	}
	
	public void render(GameContainer container, Graphics g) {
		g.setFont(font);
		g.setColor(new Color(0f, 0f, 0f, textColor.a));
		g.drawString(content, getX() + 2, getY() + 2);
		
		g.setColor(textColor);
		g.drawString(content, getX(), getY());
	}

	public void onInitialization(GameContainer container) {
		setSize(font.getWidth(content), font.getHeight(content));
	}

	public void onValueChange() {
		reInitialize();
	}

	public void setValue(String content) {
		this.content = content;
		onValueChange();
	}

	public String getValue() {
		return content;
	}
}
