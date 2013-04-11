package com.sk83rsplace.arkane.GUI.components;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.client.Board;

public class ColorPickerComponent extends Component {
	private Color color = Color.red;
	private int currentHue = 0;
	private int currentColorX = 256;
	private int currentColorY = 0;
	
	public ColorPickerComponent(int x, int y) {
		setSize(307, 272);
		set(x, y);
	}
	
	public void render(GameContainer container, Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(getX() - 9, getY() - 9, getWidth() + 2, getHeight() + 2);
		
		g.setColor(Color.white);
		g.fillRect(getX() - 8, getY() - 8, getWidth(), getHeight());
		
		g.setColor(color);
		g.fillRect(getX(), getY(), 256, 256);
		
		g.setColor(Color.gray);
		g.drawLine(getX() - 1, getY() - 1, getX() + 256, getY() - 1);
		g.drawLine(getX() + 256, getY() - 1, getX() + 256, getY() + 256);
		
		g.setColor(Color.lightGray);
		g.drawLine(getX() - 1, getY() -1, getX() - 1, getY() + 256);
		g.drawLine(getX() - 1, getY() + 256, getX() + 256, getY() + 256);
		
		g.setColor(Color.gray);
		g.drawRect(getX() + getWidth() - 40, getY() - 1, 20, 257);
		
		new ImageComponent((int) getX(), (int) getY(), "res/color_overlay.png").render(container, g);
		new ImageComponent((int) getX() + getWidth() - 39, (int) getY(), "res/color_hue_picker.png").render(container, g);
		new ImageComponent((int) getX() + getWidth() - 18, (int) getY() + currentHue - 3, "res/color_handle.png").render(container, g);
		
		g.setColor(Color.white);
		g.drawOval(getX() + currentColorX - 4, getY() + currentColorY - 4, 8, 8);
	}
	
	public void update(GameContainer container) {
		super.update(container);
		
		if(isActive() && Mouse.isButtonDown(0) && Mouse.getX() >= getX() + getWidth() - 39 && Mouse.getX() < getX() + getWidth() - 20 && (Board.getHeight() - Mouse.getY()) > getY() && (Board.getHeight() - Mouse.getY()) <= getY() + 256) {
			color = container.getGraphics().getPixel(Mouse.getX(), (Board.getHeight() - Mouse.getY()));
			currentHue = (int) ((Board.getHeight() - Mouse.getY()) - getY());
		} else if(isActive() && Mouse.isButtonDown(0) && Mouse.getX() >= getX() && Mouse.getX() < getX() + 256 && (Board.getHeight() - Mouse.getY()) >= getY() && (Board.getHeight() - Mouse.getY()) < getY() + 256) {
			currentColorX = (int) (Mouse.getX() - getX());
			currentColorY = (int) ((Board.getHeight() - Mouse.getY()) - getY());
		}
	}
}
