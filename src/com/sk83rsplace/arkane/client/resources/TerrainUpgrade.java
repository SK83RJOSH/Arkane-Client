package com.sk83rsplace.arkane.client.resources;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TerrainUpgrade {
	private Image sprite;
	private String flags;
	
	public TerrainUpgrade(InputStream sprite, String flags) {
		try {
			this.sprite = new Image(sprite, sprite.toString(), false, Image.FILTER_NEAREST);
			sprite.close();
		} catch (SlickException | IOException e) {
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
