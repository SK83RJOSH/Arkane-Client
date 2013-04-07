package com.sk83rsplace.arkane.GUI.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.GUI.IClickable;
import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.client.Client;

public class ServerComponent extends Component implements IClickable {
	private Socket connection;
    private PrintWriter out;
    private BufferedReader in;
	private long ping = -1;
	private int maxPlayers = -1;
	private int currentPlayers = -1;
	private final int MAX_TIMEOUT = 60 * 4; //8 Seconds
	private int timeout = MAX_TIMEOUT;
	private String serverAddress = "null";
	private String serverBanner = "MOTD == UNIMPLEMENTED";
	private boolean isPressed;
	
	public ServerComponent(int x, int y, String serverAddress) {
		this.serverAddress = serverAddress;
		setSize(Board.getWidth(), 64);
		set(x, y);
		collectInformation(serverAddress);
	}
	
	public void render(GameContainer container, Graphics g) {
		g.setColor(new Color(0f, 0f, 0f, 0.5f + (isActive() ? 0.25f : 0f)));
		g.fill(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		
		//TODO: Draw String
		g.setFont(container.getDefaultFont());
		
		g.setColor(Color.black);
		g.drawString(serverAddress, getX() + (getWidth() / 2) - (container.getDefaultFont().getWidth(serverAddress) / 2) + 2, getY() + 8);
		g.setColor(Color.white);
		g.drawString(serverAddress, getX() + (getWidth() / 2) - (container.getDefaultFont().getWidth(serverAddress) / 2), getY() + 6);
		
		g.setColor(Color.black);		
		g.drawString(serverBanner, getX() + (getWidth() / 2) - (container.getDefaultFont().getWidth(serverBanner) / 2) + 2, getY() + (getHeight() / 2) - (container.getDefaultFont().getLineHeight() / 2) + 2);
		g.setColor(Color.white);
		g.drawString(serverBanner, getX() + (getWidth() / 2) - (container.getDefaultFont().getWidth(serverBanner) / 2), getY() + (getHeight() / 2) - (container.getDefaultFont().getLineHeight() / 2));
		
		g.setColor(Color.black);
		g.drawString("Players: " + currentPlayers + "/" + maxPlayers, getX() + 142, getY() + getHeight() - 16);
		g.setColor(Color.white);
		g.drawString("Players: " + currentPlayers + "/" + maxPlayers, getX() + 140, getY() + getHeight() - 18);
		
		g.setColor(Color.black);
		g.drawString("Ping: " + ping + "ms", getX() + getWidth() - container.getDefaultFont().getWidth("Ping:" + ping + "ms") - 138, getY() + getHeight() - 16);
		g.setColor(Color.white);
		g.drawString("Ping: " + ping + "ms", getX() + getWidth() - container.getDefaultFont().getWidth("Ping:" + ping + "ms") - 140, getY() + getHeight() - 18);
		
		g.drawLine(getX(), getY(), getX() + getWidth(), getY());
		g.drawLine(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight());
	}
	
	public void update(GameContainer container) {
		super.update(container);
		
		if(isActive() && Board.mouseButtons.wasReleased(0) && !isPressed) {
			isPressed = true;
			click();	
		} else if(isPressed && Board.mouseButtons.isDown(0)) {
			isPressed = false;
		}
		
		if(timeout > 0) {
			timeout--;
		} else {
			timeout = MAX_TIMEOUT;
			collectInformation(serverAddress);
		}	
	}
	
	private void collectInformation(String serverAddress) {
		try {
			connection = new Socket(serverAddress, 3371);
	        out = new PrintWriter(connection.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String serverMessage = "";
		
		if(out != null && in != null) {
			out.println("Stats");
			
			ping = System.currentTimeMillis();
			
        	try {
				serverMessage = in.readLine();
	        	String[] args = serverMessage.split(" ");
	        		        	
	        	if(args.length == 2) {	        		
	        		ping = System.currentTimeMillis() - ping;
	    			this.currentPlayers = Integer.parseInt(args[0]);
	    			this.maxPlayers = Integer.parseInt(args[1]);
	        	}
        	} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				ping = -1;
				serverBanner = "Server may be offline!";
			} catch (NumberFormatException e) {
				e.printStackTrace();
				timeout = 1;
			}
		}
	}

	private void click() {		
		try {
			new Sound("res/sounds/click.ogg").play();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		onClick();
	}
	
	public void onClick() {
		Board.client = new Client(serverAddress);
		if(Client.connected)
			Board.menuStack.pop();
	}
}
