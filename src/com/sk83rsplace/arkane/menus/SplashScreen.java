package com.sk83rsplace.arkane.menus;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.client.Board;

/**
 * 
 * @author SK83RJOSH
 */
public class SplashScreen extends Menu {
	private ArrayList<Image> images = new ArrayList<Image>();
	private int current, timeout = 150;
	private boolean skipped;
	
	public SplashScreen() {
		try {
			images.add(new Image("res/splash.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void render(GameContainer container, Graphics g) {
		float col = 1f;
		
		if(timeout >= 120 && current == 0)
			col -= (0.03333333333f * (31 - (150 - timeout)));
		else if(timeout <= 30 && current == images.size() - 1)
			col -= 0.03333333333f * (31 - timeout);
		
		g.pushTransform();
			g.scale((Board.getWidth() * 1f) / images.get(current).getWidth(), (Board.getHeight() * 1f) / images.get(current).getHeight());
			g.drawImage(images.get(current), 0, 0, new Color(col, col, col));
		g.popTransform();
	}
	
	public void update(GameContainer container) {
		timeout--;
		
		if(timeout == 0 && current == images.size() - 1) {
			Board.menuStack.pop();
			Board.menuStack.add(new MainMenu());
		} else if(timeout == 0) {
			timeout = 150;
			current++;
		} else if((Mouse.isButtonDown(0) || Keyboard.isKeyDown(Keyboard.KEY_SPACE)) && !skipped) {
			skipped = true;
			timeout = 30;
			current = images.size() - 1;
		}
	}
}
