package com.sk83rsplace.arkane.client;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.client.resources.Resources;
import com.sk83rsplace.arkane.menus.LoginMenu;
import com.sk83rsplace.arkane.menus.PauseMenu;

public class Board extends BasicGame {
	private static int STARTING_WIDTH = 640;
	private static int STARTING_HEIGHT = 512;
	private static ScalableGame game;
	public static Properties properties;
	public static String userSettingsLocation = System.getProperty("user.home") + "\\Slightly Undead\\Aeternal\\Settings\\settings.prop";
	public static File settings;
	public static Client client;
	public static CopyOnWriteArrayList<Player> clients = new CopyOnWriteArrayList<Player>();
	public static Stack<Menu> menuStack = new Stack<Menu>();
	public static MouseButtons mouseButtons = new MouseButtons();
	public static Resources res;
	public static String username = "Bob";
	public static int userID = 0;
	public static int characterID = 0;
	public static Font font;
	public static boolean debugging = false;
	private boolean isToggled = false;
	private int timeout = 0;
	
	public Board(String title) {
		super(title);
	}

    public static void main(String[] args) throws Exception {
        game = new ScalableGame(new Board("Aeternal Prototype"), STARTING_WIDTH, STARTING_HEIGHT) {
        	protected void renderOverlay(GameContainer container, Graphics g) {        		
        		if(!menuStack.isEmpty())
        			menuStack.peek().render(container, g);
        	}
        };
                
        AppGameContainer container = new AppGameContainer(game);
        container.setMultiSample(0);
        container.setVSync(true);
        container.setTargetFrameRate(60);
        container.start();
    }

	public void render(GameContainer container, Graphics g) throws SlickException {
		for(Player p : clients) {
			g.drawImage(new Image("res/steven.png"), p.pos.x, p.pos.y);
			g.setColor(Color.white);
			g.drawString(p.username, p.pos.x + 38 - (g.getFont().getWidth(p.username) / 2), p.pos.y + 64);
		}
	}

	public void init(GameContainer container) throws SlickException {
    	try {
    		InputStream inputStream	= ResourceLoader.getResourceAsStream("res/fonts/Orion.ttf");    		
    		font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
    		container.setDefaultFont(new TrueTypeFont(font.deriveFont(16f), false));
    	} catch (Exception e) {
    		e.printStackTrace();
    	} 
		
		loadProperties();
		debugging = ((Board.properties.getProperty("debug_mode") == null ? false : Board.properties.getProperty("remember_me").equals("1")) ? true : false);
		res = new Resources();
		menuStack.add(new LoginMenu());
		System.out.println("Game Ready.");
	}

	int tick = 0;
	int lastX = -1;
	int lastY = -1;
	
	public void update(GameContainer container, int delta) throws SlickException {
		//TODO: Implement Delta into movement
		
		for(int button = 0; button < 4; button++) {
			if(Mouse.isButtonDown(button)) {
		        mouseButtons.setNextState(button, true);
			} else {
		        mouseButtons.setNextState(button, false);
			}
		}

		if(timeout > 0)
			timeout--;
		
		if(Client.connected) {
			tick++;
			tick = tick%120;
			
			if(tick%30 == 0) {
					client.update("Ping");
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				for(Player p : clients)
					if(p.username == "Me")
						p.pos.set(p.pos.x, p.pos.y + 4);
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				for(Player p : clients)
					if(p.username == "Me")
						p.pos.set(p.pos.x, p.pos.y - 4);
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
				for(Player p : clients)
					if(p.username == "Me")
						p.pos.set(p.pos.x + 4, p.pos.y);
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				for(Player p : clients)
					if(p.username == "Me")
						p.pos.set(p.pos.x - 4, p.pos.y);
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !isToggled && timeout == 0) {
				menuStack.add(new PauseMenu());
				timeout = 30;
				isToggled = !isToggled;
			} else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && isToggled && timeout == 0) {
				if(menuStack.size() > 0)
					menuStack.pop();
				
				timeout = 30;
				isToggled = !isToggled;
			}
		}
		
		for(Player p : clients) {
			if(p.username == "Me" && lastX != ((int) p.pos.x) && Client.connected || p.username == "Me" && lastY != ((int) p.pos.y) && Client.connected) {
				client.update("Update " + characterID + " " + ((int) p.pos.x) + " " + ((int) p.pos.y));

				lastX = ((int) p.pos.x);
				lastY = ((int) p.pos.y);
			}
		}
		
		if(!menuStack.empty())
			menuStack.peek().update(container);
			
        mouseButtons.update();
	}

	public static int getWidth() {
		return (int) (STARTING_WIDTH * game.getScaleX());
	}
	
	public static int getHeight() {
		return (int) (STARTING_HEIGHT * game.getScaleY());
	}
	
	public static void startGame() {
		menuStack.pop();
	}
	
	public static void exitGame() {
		menuStack.pop();
	}
	
	private void loadProperties() {
		settings = new File(userSettingsLocation);
		properties = new Properties();
		
		try {
			properties.load(new FileInputStream(userSettingsLocation));
		} catch (IOException e) {			
			try {
				if(settings.getParentFile().mkdirs()) {
					if(settings.createNewFile()) {
						saveProperties();
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void saveProperties() {
		try {
			properties.store(new FileOutputStream(settings), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
