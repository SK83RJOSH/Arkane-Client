package com.sk83rsplace.arkane.client.game;

import com.sk83rsplace.arkane.client.Client;

public class GameThread extends Thread {
	private static int tick = 0;

	public GameThread() {
		this.start();
	}
	
	public void run() {
		super.run();
		
		while(true) {
			if(tick%30 == 0)
				Client.update("Ping");
			
			tick++;
			tick = tick%60;
			try {
				sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
