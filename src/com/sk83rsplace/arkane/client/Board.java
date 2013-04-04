package com.sk83rsplace.arkane.client;

import java.awt.Font;
import java.io.InputStream;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

//import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.ResourceLoader;

import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.menus.LoginMenu;

public class Board extends BasicGame {
	private static int STARTING_WIDTH = 640;
	private static int STARTING_HEIGHT = 512;
	private static ScalableGame game;
//	private static Client client;
	public static CopyOnWriteArrayList<Player> clients = new CopyOnWriteArrayList<Player>();
	public static Stack<Menu> menuStack = new Stack<Menu>();
	public static MouseButtons mouseButtons = new MouseButtons();
	public static String username = "Bob";
	public static int userID = 0;
	public static int characterID = 0;
	
	public Board(String title) {
		super(title);
	}

    public static void main(String[] args) throws Exception {
        game = new ScalableGame(new Board("Arkane Prototype"), STARTING_WIDTH, STARTING_HEIGHT) {
        	protected void renderOverlay(GameContainer container, Graphics g) {        		
        		if(!menuStack.isEmpty())
        			menuStack.peek().render(container, g);
        	}
        };
        //client = new Client();
        
        AppGameContainer container = new AppGameContainer(game);
        container.setMultiSample(0);
        container.setVSync(true);
        container.start();
    }

	public void render(GameContainer container, Graphics g) throws SlickException {
		for(Player p : clients) {
			Rectangle player = new Rectangle(p.pos.x, p.pos.y, 32, 32);
			g.setColor(p.color);
			g.fill(player);
			g.setColor(Color.white);
			g.drawString(p.username, p.pos.x + 16 - (g.getFont().getWidth(p.username) / 2), p.pos.y + 36);
		}
	}

	public void init(GameContainer container) throws SlickException {
		//clients.add(new Player(0, 0, "Me"));
		menuStack.add(new LoginMenu());
		
    	try {
    		InputStream inputStream	= ResourceLoader.getResourceAsStream("res/fonts/Orion.ttf");    		
    		container.setDefaultFont(new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(16f), false));
    	} catch (Exception e) {
    		e.printStackTrace();
    	} 
	}

	int tick = 0;
	int lastX = -1;
	int lastY = -1;
	
	public void update(GameContainer container, int delta) throws SlickException {
		for(int button = 0; button < 4; button++) {
			if(Mouse.isButtonDown(button)) {
		        mouseButtons.setNextState(button, true);
			} else {
		        mouseButtons.setNextState(button, false);
			}
		}
		
//		tick++;
//		tick = tick%120;
//		
//		if(tick%30 == 0) {
//			client.update("Ping");
//		}
		
//		for(Player p : clients) {
//			if(p.username == "Me" && lastX != ((int) p.pos.x) || p.username == "Me" && lastY != ((int) p.pos.y)) {
//				client.update("Update John " + ((int) p.pos.x) + " " + ((int) p.pos.y));
//
//				lastX = ((int) p.pos.x);
//				lastY = ((int) p.pos.y);
//			}
//		}
		
//		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
//			for(Player p : clients)
//				if(p.username == "Me")
//					p.pos.set(p.pos.x, p.pos.y + 4);
//		}
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
//			for(Player p : clients)
//				if(p.username == "Me")
//					p.pos.set(p.pos.x, p.pos.y - 4);
//		}
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
//			for(Player p : clients)
//				if(p.username == "Me")
//					p.pos.set(p.pos.x + 4, p.pos.y);
//		}
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
//			for(Player p : clients)
//				if(p.username == "Me")
//					p.pos.set(p.pos.x - 4, p.pos.y);
//		}
		
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
		//Board.level = new Level("test.xml");
	}
	
	public static void exitGame() {
		menuStack.pop();
		//Board.level = null;
		//menuStack.add(new StartMenu());
	}
}
