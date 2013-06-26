package com.sk83rsplace.arkane.client.resources;

import java.io.InputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TerrainUpgrade {
	private Image sprite;
	private String flags;
	
	public TerrainUpgrade(InputStream sprite, String flags) {
		try {
			this.sprite = new Image(sprite, sprite.toString(), false);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
		this.flags = flags;
	}
	
	public Image getResource() {
		return sprite;
	}
	
	public String getFlags() {
		return flags;
	}
}
