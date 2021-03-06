package com.sk83rsplace.arkane.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import com.sk83rsplace.arkane.GUI.Fonts;
import com.sk83rsplace.arkane.GUI.Images;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.CheckBoxComponent;
import com.sk83rsplace.arkane.GUI.components.LabelComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.utils.HTTP;

public class MainMenu extends Menu {
	private TextComponent errorMessage;
	private ButtonComponent createButton;
	private ButtonComponent debugButton;
	private ButtonComponent continueButton;
	private ArrayList<CheckBoxComponent> characterComponents = new ArrayList<CheckBoxComponent>();
	
	public MainMenu() {
		errorMessage = new TextComponent("", Color.yellow, -1, 392);
		createButton = new ButtonComponent("Create New", (Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new CreationMenu());
			}
		};
		debugButton = new ButtonComponent("Developer Tools", -1, Board.getHeight() - 15 - 110) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new DeveloperMenu());
			}
		};
		continueButton = new ButtonComponent("Continue", Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ServerListMenu());
			}
		};
		
		addComponent(new TextComponent("Welcome " + Board.username + ",", Color.white, Fonts.mediumPoint, -1, 50));
		addComponent(new TextComponent("Select your Character:", Color.white, Fonts.mediumPoint, -1, 72));
		
		getCharacters();

		if(Board.debugging) {
			addComponent(debugButton);
		}
		
		addComponent(createButton);
		addComponent(continueButton);
		addComponent(errorMessage);
	}
	
	public void resize() {
		createButton.set((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, Board.getHeight() - 15 - 50);
		debugButton.set(-1, Board.getHeight() - 15 - 110);
		continueButton.set(Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), Board.getHeight() - 15 - 50);
		
		for(CheckBoxComponent c : characterComponents)
			c.set((Board.getWidth() / 2) + 40, c.getY());
	}
	
	private void getCharacters() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("userID", "" + (Board.userID));
		
		HTTP httpConnection = new HTTP();
		String JSONResult = httpConnection.post("http://josh.chopdawg.com/arkane/listCharacters.php", params);
		
		try {
			JSONObject JSONParser = new JSONObject(JSONResult);
			JSONArray characters = JSONParser.getJSONArray("characters");
			
			for(int index = 0; index < characters.length(); index++) {
				final JSONObject character = characters.getJSONObject(index);
				CheckBoxComponent component = new CheckBoxComponent((index == 0 ? true : false), true, (Board.getWidth() / 2) + 40, 98 + (index * 32)) {
					public void onInitialization(GameContainer container) {
						try {
							addComponent(new LabelComponent(character.getString("name"), this));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					
					public void onValueChange() {
						try {
							if(getValue()) {
								System.out.println(character.getInt("character_id"));
								Board.characterID = character.getInt("character_id");
								
								for(CheckBoxComponent c : characterComponents)
									if(c != this)
										c.setValue(false);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				};
				
				if(index == 0)
					Board.characterID = character.getInt("character_id");
					
				addComponent(component);
				characterComponents.add(component);
			}
			
			if(characters.length() == 0)
				addComponent(new TextComponent("You don't have any Characters!", Color.orange, -1, 98));
		} catch (JSONException e) {
			e.printStackTrace();
			errorMessage.setValue("Unexpected Error!");
		}
	}
}
