package com.sk83rsplace.arkane.client.resources;

import java.io.File;
import java.util.HashMap;


public class Resources {
	private String resourceLocation = System.getProperty("user.home") + "\\Slightly Undead\\Aeternal\\Resources";
	private File resources;
	private HashMap<String, TerrainResource> terrainResources = new HashMap<String, TerrainResource>();
	
	public Resources() {
		mountResources();
	}
	
	private void mountResources() {
		resources = new File(resourceLocation);
		
		if(resources.exists()) {
			scanResources();
		} else {
			if(resources.mkdirs())
				scanResources();
			else
				System.err.println("Couldn't create Hotbed!");
		}
	}
		
	private void scanResources() {
		long startTime = System.currentTimeMillis();
		File terrainRes = new File(resourceLocation + "\\Terrain");
		
		if(terrainRes.exists()) {
			File[] terrainResContents = terrainRes.listFiles();
			
			if(terrainResContents.length > 0) {
				for(File f : terrainResContents) {
					String fileName = f.getName();
					
					if(f.isDirectory()) {
						File terrainData = new File(f.getAbsoluteFile() + "\\folder.adf");
						
						if(terrainData.exists()) {
							System.out.println("Processing folder.adf in " + fileName + " . . .");
							TerrainResource resource = new TerrainResource(terrainData);
							terrainResources.put(resource.getReferenceName(), resource);
						}
					}
				}
			}
		}
		
		System.out.println((System.currentTimeMillis() - startTime) + "ms taken to scan for resources.");
	}
	
	public void addTerrainDefinition(String def, TerrainResource val) {
		terrainResources.put(def, val);
	}
	
	public TerrainResource getTerrainDefinition(String def) {
		if(terrainResources.containsKey(def))
			return terrainResources.get(def);
		else
			System.err.println("Tried to get invalid Terrain Definition '" + def + "'!");
		
		return null;
	}
	
	public HashMap<String, TerrainResource> getTerrainDefinitions() {
		return terrainResources;
	}
}
