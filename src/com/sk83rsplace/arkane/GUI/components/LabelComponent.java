package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.sk83rsplace.arkane.GUI.Component;

/**
 * 
 * @author SK83RJOSH
 */
public class LabelComponent extends TextComponent {
	private Component parent;
	
	public LabelComponent(String content, Component parent) {
		super(content + ": ", Color.white, -100, -100);
		this.parent = parent;
	}
	
	public void render(GameContainer container, Graphics g) {		
		set(parent.getX() - getWidth(), parent.getY() + (parent.getHeight() / 2) - (getHeight() / 2));
		
		g.setColor(Color.black);
		g.drawString(getValue(), getX() + 2, getY() + 2);
		
		g.setColor(Color.white);
		g.drawString(getValue(), getX(), getY());
	}

	public void onInitialization(GameContainer container) {
		setSize(container.getDefaultFont().getWidth(getValue()), container.getDefaultFont().getHeight(getValue()));
	}
	
	public void setValue(String content) {
		super.setValue(content + ": ");
	}
	
	public void onValueChange() {
		reInitialize();
	}
}
