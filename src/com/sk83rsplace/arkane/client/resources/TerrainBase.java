package com.sk83rsplace.arkane.client.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TerrainBase {
	private Image sprite;
	private TerrainResource parent;
	private String flags;
	private String upgrades;
	
	public TerrainBase(InputStream sprite, String flags, String upgrades, TerrainResource parent) {
		try {
			this.sprite = new Image(sprite, sprite.toString(), false);
			sprite.close();
		} catch (SlickException | IOException e) {
			e.printStackTrace();
		}
	
		this.parent = parent;
		this.flags = flags;
		this.upgrades = upgrades;
	}
	
	public Image getResource() {
		return sprite;
	}
	
	public String getFlags() {
		return flags;
	}
	
	public ArrayList<TerrainUpgrade> getValidUpgrades() {
		ArrayList<TerrainUpgrade> validUpgrades = new ArrayList<TerrainUpgrade>();
		String[] parsedUpgrades = upgrades.split(" ");
		
		for(int currentUpgrade = 0; currentUpgrade < parsedUpgrades.length; currentUpgrade++) {
			if(!parsedUpgrades[currentUpgrade].equals(""))
				validUpgrades.add(parent.getUpgrade(Integer.parseInt(parsedUpgrades[currentUpgrade])));
		}
		
		return validUpgrades;
	}
	
	public boolean isValidUpgrade(TerrainUpgrade upgrade) {
		return getValidUpgrades().contains(upgrade);
	}
}
