package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.client.Board;

public class PanelComponent extends Component {
	private Image image;
	private int stackX, stackY;
	
	public PanelComponent(int x, int y, int stackX, int stackY, Image image) {
		setImage(image);
		set(x, y);
		setStack(stackX, stackY);
		setSize(image.getWidth(), image.getHeight());
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public void render(GameContainer container, Graphics g) {
		for(int x = 0; x < getStackX(); x++)
			for(int y = 0; y < getStackY(); y++)
				g.drawImage(image, getX() + (image.getWidth() * x), getY() + (image.getHeight() * y));
	}
	
	public void setStack(int stackX, int stackY) {
		this.stackX = stackX;
		this.stackY = stackY;
	}
	
	public int getStackX() {
		if(stackX == -1)
			return (int) Math.ceil((Board.getWidth() * 1f) / image.getWidth());
		else
			return stackX;
		
	}
	
	public int getStackY() {
		if(stackY == -1)
			return (int) Math.ceil((Board.getHeight() * 1f) / image.getHeight());
		else
			return stackY;
	}
}
