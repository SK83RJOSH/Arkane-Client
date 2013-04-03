package com.sk83rsplace.arkane.client;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class Player {
	public String username = "Bob";
	public Vector2f pos;
	public Color color;
	
	public Player(int x, int y, String username) {
		pos = new Vector2f();
		pos.set(x, y);
		this.username = username;
		Random ran = new Random();
		color = new Color(ran.nextFloat(), ran.nextFloat(), ran.nextFloat());
	}
}
