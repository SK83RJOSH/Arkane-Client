package com.sk83rsplace.arkane.GUI;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Dimension;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;

import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.client.interfaces.IRenderable;

/**
 * 
 * @author SK83RJOSH
 */
public abstract class Component implements IRenderable {
	private Vector2f pos;
	private Dimension dim;
	private boolean active;
	private boolean initialized;
	private Menu parent;
	
	public Component() {
		dim = new Dimension();
		pos = new Vector2f();
	}
	
	public void update(GameContainer container) {
		if(Mouse.getX() >= getX() && Mouse.getX() <= getX() + getWidth() && (container.getHeight() - Mouse.getY()) >= getY() && (container.getHeight() - Mouse.getY()) <= getY() + getHeight()) {
			active = true;
		} else {
			active = false;
		}
		
		if(!initialized) {
			onInitialization(container);
			initialized = true;
		}
	}
	
	public int getX() {
		return (int) (pos.x == -1 ? (Board.getWidth() / 2) - (getWidth() / 2) : pos.x);
	}
	
	public int getY() {
		return (int) pos.y;
	}
	
	public void set(int x, int y) {
		pos.setX(x);
		pos.setY(y);
	}
	
	public int getWidth() {
		return dim.getWidth();
	}
	
	public int getHeight() {
		return dim.getHeight();
	}
	
	public void setSize(int width, int height) {
		dim.setWidth(width);
		dim.setHeight(height);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void onInitialization(GameContainer container) {
		//To be overridden.
	}
	
	public void reInitialize() {
		initialized = false;
	}
	
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	
	public Menu getParent() {
		return parent;
	}
}
