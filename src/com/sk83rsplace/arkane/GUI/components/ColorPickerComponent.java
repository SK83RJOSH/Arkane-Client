package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.sk83rsplace.arkane.GUI.Component;

public class ColorPickerComponent extends Component {
	public ColorPickerComponent(int x, int y) {
		setSize(256, 256);
		set(x, y);
	}
	
	public void render(GameContainer container, Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(getX() - 9, getY() - 9, getWidth() + 53, getHeight() + 18);
		g.setColor(Color.white);
		g.fillRect(getX() - 8, getY() - 8, getWidth() + 51, getHeight() + 16);
		g.setColor(Color.cyan);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.gray);
		g.drawRect(getX() - 1, getY() - 1, getWidth() + 1, getHeight() + 1);
		g.setColor(Color.gray);
		g.drawRect(getX() + getWidth() + 15, getY() - 1, 20, getHeight() + 1);
		new ImageComponent((int) getX(), (int) getY(), "res/color_overlay.png").render(container, g);
		new ImageComponent((int) getX() + getWidth() + 16, (int) getY(), "res/color_hue_picker.png").render(container, g);
	}

}
