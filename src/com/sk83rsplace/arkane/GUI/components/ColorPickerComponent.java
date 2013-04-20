package com.sk83rsplace.arkane.GUI.components;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.GUI.IValuedColor;
import com.sk83rsplace.arkane.client.Board;

public class ColorPickerComponent extends Component implements IValuedColor {
	private Color color = Color.red;
	private Color content = Color.red;
	private int padding = 8;
	private int currentHue = 0;
	private int currentColorX = 256 + padding - 4;
	private int currentColorY = padding + 4;
	private boolean selecting = false;
	
	public ColorPickerComponent(int x, int y) {
		setSize(307, 272);
		set(x, y);
	}
	
	public void render(GameContainer container, Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(getX() - 1, getY() - 1, getWidth() + 2, getHeight() + 2);
		
		g.setColor(Color.white);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
		g.setColor(color);
		g.fillRect(getX() + padding, getY() + padding, 256, 256);
		
		g.setColor(Color.gray);
		g.drawLine(getX() - 1 + padding, getY() - 1 + padding, getX() + padding + 256, getY() + padding - 1);
		g.drawLine(getX() + padding + 256, getY() + padding - 1, getX() + padding + 256, getY() + padding + 256);
		
		g.setColor(Color.lightGray);
		g.drawLine(getX() + padding - 1, getY() + padding -1, getX() + padding - 1, getY() + padding + 256);
		g.drawLine(getX() + padding - 1, getY() + padding + 256, getX() + padding + 256, getY() + padding + 256);
		
		g.setColor(Color.gray);
		g.drawRect(getX() + getWidth() + padding - 40, getY() + padding - 1, 20, 257);
		
		new ImageComponent((int) getX() + padding, (int) getY() + padding, "res/color_overlay.png").render(container, g);
		new ImageComponent((int) getX() + padding + getWidth() - 39, (int) getY() + padding, "res/color_hue_picker.png").render(container, g);
		new ImageComponent((int) getX() + padding + getWidth() - 18, (int) getY() + padding + currentHue - 3, "res/color_handle.png").render(container, g);
		
		g.setColor(Color.white);
		if(!selecting)
			g.drawOval(getX() + currentColorX - 4, getY() + currentColorY - 4, 8, 8);
		
//		g.setColor(content);
//		g.fillRect(getX(), getY(), 16, 16);
	}
	
	public void update(GameContainer container) {
		super.update(container);
		
		Color tempColor = container.getGraphics().getPixel((int) getX() + currentColorX, (int) getY() + currentColorY);
		content = new Color(tempColor.r, tempColor.g, tempColor.b, 1f);
		
		if(isActive() && Mouse.getX() >= getX() + getWidth() - 31 && Mouse.getX() < getX() + getWidth() - 12 && (Board.getHeight() - Mouse.getY()) > getY() + padding && (Board.getHeight() - Mouse.getY()) <= getY() + padding + 256) {
			if(Mouse.isButtonDown(0)) {
				color = container.getGraphics().getPixel(Mouse.getX(), (Board.getHeight() - Mouse.getY()));
				currentHue = (int) ((Board.getHeight() - padding - Mouse.getY()) - getY());
				selecting = true;
				onValueChange();
			} else {
				selecting = false;
			}
			
			try {
				container.setMouseCursor("res/hue_cursor.png", 8, 4);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		} else if(isActive() && Mouse.getX() >= getX() + padding && Mouse.getX() < getX() + padding + 256 && (Board.getHeight() - Mouse.getY()) > getY() + padding && (Board.getHeight() - Mouse.getY()) < getY() + padding + 256) {
			if(Mouse.isButtonDown(0)) {
				currentColorX = (int) (Mouse.getX() - getX());
				currentColorY = (int) ((Board.getHeight() - Mouse.getY()) - getY());
				selecting = true;
				onValueChange();
			} else {
				selecting = false;
			}
			
			try {
				container.setMouseCursor("res/picker_cursor.png", 0, 16);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			container.setDefaultMouseCursor();
			selecting = false;
		}
	}

	public void onValueChange() {
		Board.menuStack.peek().setColor(content);
	}

	public void setValue(Color content) {
		this.content = content;
		onValueChange();
	}

	public Color getValue() {
		return content;
	}
}