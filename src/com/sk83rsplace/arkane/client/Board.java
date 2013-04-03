package com.sk83rsplace.arkane.client;

import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Board extends BasicGame {
	private static int STARTING_WIDTH = 640;
	private static int STARTING_HEIGHT = 512;
	private static ScalableGame game;
	private static Client client;
	public static CopyOnWriteArrayList<Player> clients = new CopyOnWriteArrayList<Player>();
	
	public Board(String title) {
		super(title);
	}

    public static void main(String[] args) throws Exception {
        game = new ScalableGame(new Board("Arkane Prototype"), STARTING_WIDTH, STARTING_HEIGHT);
        client = new Client();
        
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
		clients.add(new Player(0, 0, "Me"));
	}

	int tick = 0;
	int lastX = -1;
	int lastY = -1;
	
	public void update(GameContainer container, int delta) throws SlickException {
		tick++;
		tick = tick%120;
		
		if(tick%30 == 0) {
			client.update("Ping");
		}
		
		for(Player p : clients) {
			if(p.username == "Me" && lastX != ((int) p.pos.x) || p.username == "Me" && lastY != ((int) p.pos.y)) {
				client.update("Update SK83RJOSH " + ((int) p.pos.x) + " " + ((int) p.pos.y));

				lastX = ((int) p.pos.x);
				lastY = ((int) p.pos.y);
			}
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
	}
}
