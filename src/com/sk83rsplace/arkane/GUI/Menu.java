package com.sk83rsplace.arkane.GUI;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import com.sk83rsplace.arkane.GUI.components.TextInputComponent;
import com.sk83rsplace.arkane.GUI.interfaces.IKeyListener;
import com.sk83rsplace.arkane.GUI.interfaces.IRenderable;
import com.sk83rsplace.arkane.client.Board;

/**
 * 
 * @author SK83RJOSH
 */
public abstract class Menu implements IKeyListener, IRenderable {
	protected CopyOnWriteArrayList<Component> components = new CopyOnWriteArrayList<Component>();
	private Color color = new Color(255, 0, 255);
	private int nextTimeout = 0;
	
	public void render(GameContainer container, Graphics g) {
		g.setColor(getColor());
		g.fill(new Rectangle(0, 0, Board.getWidth(), Board.getHeight()));
		
		for(Component c : components)
			c.render(container, g);
	}
	
	public void update(GameContainer container) {
		for(Component c : components)
			c.update(container);
		
		if(nextTimeout > 0)
			nextTimeout--;
	}
	
	public void resize() {
		//TODO: Overridden
	}
	
	public void keyPressed(int key, char c) {
		for(Component comp : components) {
			if(comp instanceof IKeyListener) {
				if(comp instanceof TextInputComponent) {
					TextInputComponent input = (TextInputComponent) comp;
					
					if(input.isSelected()) {
						input.keyPressed(key, c);
					}
				} else {
					((IKeyListener) comp).keyPressed(key, c);
				}
			}
		}
	}
	
	public void addComponent(Component c) {
		c.setParent(this);
		components.add(c);
	}
	
	public void removeComponent(Component c) {
		components.remove(c);
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

	public void selectPrevious() {
		if(nextTimeout == 0) {
			boolean foundCurrent = false, selected = false;
						
			for(int index = components.size() - 1; index > 0; index--) {
				Component c = components.get(index);
							
				if(c instanceof TextInputComponent) {
					if(((TextInputComponent) c).isSelected() && !foundCurrent) {
						foundCurrent = true;
						((TextInputComponent) c).setSelected(false);
					} else if(foundCurrent && !selected) {
						((TextInputComponent) c).setSelected(true);
						selected = true;
					}
				}
			}
			
			if(foundCurrent && !selected) {
				TextInputComponent component = null;
				
				for(Component c : components) {
					if(c instanceof TextInputComponent) {
						component = (TextInputComponent) c;
					}
				}
				
				component.setSelected(true);
			}
			
			nextTimeout = 5;
		}
	}

	public void selectNext() {
		if(nextTimeout == 0) {
			boolean foundCurrent = false, selected = false;
			
			for(int index = 0; index < components.size(); index++) {
				Component c = components.get(index);
							
				if(c instanceof TextInputComponent) {
					if(((TextInputComponent) c).isSelected() && !foundCurrent) {
						foundCurrent = true;
						((TextInputComponent) c).setSelected(false);
					} else if(foundCurrent && !selected) {
						((TextInputComponent) c).setSelected(true);
						selected = true;
					}
				}
			}
			
			if(foundCurrent && !selected) {
				for(Component c : components) {
					if(c instanceof TextInputComponent && !selected) {
						((TextInputComponent) c).setSelected(true);
						selected = true;
					}
				}
			}
			
			nextTimeout = 5;
		}
	}
}
