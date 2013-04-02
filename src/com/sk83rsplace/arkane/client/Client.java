package com.sk83rsplace.arkane.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sk83rsplace.arkane.client.game.GameThread;

public class Client extends Thread {
	private static Socket connection = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    private static boolean running = true;
    private static int heartbeats;
    private static final String ACTION_KICKED = "Kicked";
    private static final String ACTION_ACCEPTED = "Connect";
    private static final String ACTION_HEARTBEAT = "Ping";
    private static final String ACTION_HEARTBEAT_RECEIVED = "Pong";
    
	public static void main(String argv[]) {
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
        
        new GameThread();
        
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
	
	public static void update(String update) {
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
		}
		
		out.println(update);
	}
}
