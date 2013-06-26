package com.sk83rsplace.arkane.client.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLElementList;
import org.newdawn.slick.util.xml.XMLParser;

public class TerrainResource {
	private File ADFRef;
	private ArrayList<TerrainBase> bases = new ArrayList<TerrainBase>();
	private ArrayList<TerrainUpgrade> upgrades = new ArrayList<TerrainUpgrade>();

	public TerrainResource(File ADFRef) {
		this.ADFRef = ADFRef;
		
		XMLParser parser = new XMLParser();
		try {
			String resourceLocation = ADFRef.getAbsolutePath().replace("folder.adf", "");
			XMLElement document = parser.parse(resourceLocation + "folder.adf");
			XMLElementList bases = document.getChildrenByName("base");
			XMLElementList upgrades = document.getChildrenByName("upgrade");
			String spriteFormat = document.getAttribute("sprite_format");
			
			for(int refID = 0; refID < bases.size(); refID++) {
				XMLElement base = bases.get(refID);
				String resourcePath = resourceLocation + base.getAttribute("base_sprite") + "." + spriteFormat;
				InputStream sprite = new FileInputStream(resourcePath);
				String upgradesAttr = base.getAttribute("upgrades");
				String flagsAttr = base.getAttribute("flags");
				
				this.bases.add(new TerrainBase(sprite, flagsAttr, upgradesAttr, this));
			}
			
			for(int refID = 0; refID < upgrades.size(); refID++) {
				XMLElement upgrade = upgrades.get(refID);
				String resourcePath = resourceLocation + upgrade.getAttribute("base_sprite") + "." + spriteFormat;
				InputStream sprite = new FileInputStream(resourcePath);
				String flagsAttr = upgrade.getAttribute("flags");
				
				this.upgrades.add(new TerrainUpgrade(sprite, flagsAttr));
			}
		} catch (SlickException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Found " + bases.size() + " Base" + (bases.size() != 1 ? "s" : "") + " and " + upgrades.size() + " Upgrade" + (upgrades.size() != 1 ? "s" : "") + ".");
	}
	
	public ArrayList<TerrainUpgrade> getUpgrades() {
		return upgrades;
	}
	
	public TerrainUpgrade getUpgrade(int refID) {
		if(refID < upgrades.size() && refID >= 0)
			return upgrades.get(refID);
		else
			return null;
	}
	
	public ArrayList<TerrainBase> getBases() {
		return bases;
	}
	
	public TerrainBase getBase(int refID) {
		if(refID < bases.size() && refID >= 0)
			return bases.get(refID);
		else
			return null;
	}
	
	public boolean isSolid(int baseID, int upgradeID) {
		return (getBase(baseID).getFlags().contains("impassable") || getUpgrade(upgradeID).getFlags().contains("impassable"));
	}
	
	public File getReference() {
		return ADFRef;
	}
}
