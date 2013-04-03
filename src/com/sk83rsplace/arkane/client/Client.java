package com.sk83rsplace.arkane.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {
	private static Socket connection = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    private boolean running = true;
    private int heartbeats;
    private final String ACTION_KICKED = "Kicked";
    private final String ACTION_ACCEPTED = "Connect";
    private final String ACTION_HEARTBEAT = "Ping";
    private final String ACTION_HEARTBEAT_RECEIVED = "Pong";
    private final String CLIENT_CREATE = "Create";
    private final String CLIENT_UPDATE = "Update";

	public Client() {
		try {
			connection = new Socket("josh-gaming-pc", 3371);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
        try {
	        out = new PrintWriter(connection.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
			e.printStackTrace();
		}
        
        //new GameThread();
        this.start();
	}
	
	public void run() {
		super.run();
		
        String serverMessage = "";
        String lastMessage = "";
        
        while(running != false) {
        	try {
				serverMessage = in.readLine();
			} catch (IOException e) {
				if(e.getMessage().equals("Connection reset"))
					heartbeats = 6;
			}
        	
            if(lastMessage != serverMessage && serverMessage != null) {
            	String args[] = serverMessage.split(" ");
            	            	
            	switch(args[0]) {
	            	case ACTION_KICKED:
	            		running = false;
	            		kicked(serverMessage.replace(args[0] + " ", ""));
	            		break;
	            	case ACTION_ACCEPTED:
	        	        out.println("Username SK83RJOSH");
	            		break;
	            	case ACTION_HEARTBEAT:
	            		out.println("Pong");
	            		break;
					case ACTION_HEARTBEAT_RECEIVED:
						heartbeats--;
						break;
					case CLIENT_CREATE:
						Board.clients.add(new Player(Integer.valueOf(args[2]), Integer.valueOf(args[3]), args[1]));
						break;
					case CLIENT_UPDATE:
						boolean exists = false;
						
						for(Player p : Board.clients) {
							if(p.username.equals(args[1])) {
								exists = true;
								p.pos.x = Integer.valueOf(args[2]);
								p.pos.y = Integer.valueOf(args[3]);
							}
						}
						
						if(!exists)
							Board.clients.add(new Player(Integer.valueOf(args[2]), Integer.valueOf(args[3]), args[1]));

						System.out.println("Got an update: " + serverMessage);
						break;
            	}
            	            	            	
            	lastMessage = serverMessage;
            }    
        }      
	}
	
	private static void kicked(String reason) {
		System.out.println("You were kicked! Reason: " + reason); //Disconnects as wanted...
		out.println("Disconnecting Kicked");
		System.exit(0);
	}
	
	public void update(String update) {
		String args[] = update.split(" ");
		
		switch(args[0]) {
			case ACTION_HEARTBEAT:
				heartbeats++;
				
				if(heartbeats > 5) {
					try {
						connection.close();
						running = false;
						System.exit(0);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}
				break;
		}
		
		out.println(update);
	}
}
