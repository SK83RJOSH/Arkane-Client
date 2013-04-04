package com.sk83rsplace.arkane.GUI;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import com.sk83rsplace.arkane.client.Board;

/**
 * 
 * @author SK83RJOSH
 */
public abstract class Menu implements IRenderable  {
	protected CopyOnWriteArrayList<Component> components = new CopyOnWriteArrayList<Component>();
	private Color color = new Color(255, 0, 255);
	
	public void render(GameContainer container, Graphics g) {
		g.setColor(getColor());
		g.fill(new Rectangle(0, 0, Board.getWidth(), Board.getHeight()));
		
		for(Component c : components)
			c.render(container, g);
	}
	
	public void update(GameContainer container) {
		for(Component c : components)
			c.update(container);
	}
	
	public void addComponent(Component c) {
		components.add(c);
	}
	
	public void destroy() {
		components.clear();
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}
