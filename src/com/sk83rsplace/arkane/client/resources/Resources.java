package com.sk83rsplace.arkane.client.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.utils.HTTP;


public class Resources {
	private String aeternalLocation = System.getProperty("user.home") + "\\Slightly Undead\\Aeternal";
	private String resourceLocation = aeternalLocation + "\\Resources";
	private String remoteLocation = "http://vps.kieraan.co.uk/~Josh/";
	private File resources;
	private HashMap<String, TerrainResource> terrainResources = new HashMap<String, TerrainResource>();
	
	public Resources() {
		if(!Board.debugging)
			checkResources();		
		
		mountResources();
	}
	
	public void checkResources() {
		long startTime = System.currentTimeMillis();
		HTTP httpConnection = new HTTP();
		String JSONResult = httpConnection.post(remoteLocation + "arkaneResources.php", new HashMap<String, String>());
		
		try {
			JSONObject JSONParser = new JSONObject(JSONResult);
			JSONArray filesList = JSONParser.getJSONArray("files");
			
			for(int index = 0; index < filesList.length(); index++) {
				JSONObject file = filesList.getJSONObject(index);
				String filename = file.getString("file");
				String md5 = file.getString("md5");
				int filesize = file.getInt("size");
				boolean acquire = false;
				
				File fileComparison = new File(aeternalLocation + "\\" + filename.replace("/", "\\"));
				
				if(fileComparison.exists()) {
					FileInputStream is = new FileInputStream(fileComparison);
					String md5Comparison = DigestUtils.md5Hex(is);
					long filesizeComparison = fileComparison.length();
					
					if(md5Comparison.equals(md5) && filesize == filesizeComparison) {
						System.out.println(filename + " checked out OK.");
					} else {
						acquire = true;
					}
				} else {
					if(!fileComparison.getParentFile().exists()) {
						System.out.println("Creating " + filename + " Directories . . .");
						fileComparison.getParentFile().mkdirs();	
					}
					
					acquire = true;
				}
				
				if(acquire) {
					System.out.println(filename + " needs to be updated.");	
					URL url = new URL(remoteLocation + filename);
					InputStream is = url.openStream();
					OutputStream os = new FileOutputStream(fileComparison);
							
					IOUtils.copy(is, os);
				}
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println((System.currentTimeMillis() - startTime) + "ms taken to check resources.");
	}
	
	public void mountResources() {
		resources = new File(resourceLocation);
		
		if(resources.exists()) {
			scanResources();
		} else {
			if(resources.mkdirs())
				scanResources();
			else
				System.err.println("Couldn't create Resources Directory!");
		}
	}
		
	public void scanResources() {
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
		} else {
			if(terrainRes.mkdirs())
				scanResources();
			else
				System.err.println("Couldn't create Terrain Directory!");
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
