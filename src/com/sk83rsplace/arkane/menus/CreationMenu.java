package com.sk83rsplace.arkane.menus;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.GUI.components.TextInputComponent;
import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.utils.HTTP;

public class CreationMenu extends Menu {
	private TextComponent errorMessage;
	private TextInputComponent characterInput;
	
	public CreationMenu() {
		errorMessage = new TextComponent("", Color.yellow, -1, 248);
		characterInput = new TextInputComponent("Character Name", "", -1, 200);
		
		addComponent(new TextComponent("Create a new Character!", Color.white, -1, 168));
		addComponent(characterInput);
		
		addComponent(errorMessage);
		
		addComponent(new ButtonComponent("Cancel", 15, Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new MainMenu());
			}
		});
		addComponent(new ButtonComponent("Create", Board.getWidth() - 15 - 258, Board.getHeight() - 15 - 50) {
			public void onClick() {
				if(characterInput.getValue().replace(" ", "").equals("") || characterInput.getValue().replace(" ", "").length() < 4)
					errorMessage.setValue("Invalid Name!");
				else {
					errorMessage.setValue("");
					createCharacter();
				}
			}
		});
	}
	
	private void createCharacter() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("name", characterInput.getValue());
		params.put("userID", "" + Board.userID);
		
		HTTP httpConnection = new HTTP();
		String JSONResult = httpConnection.post("http://vps.kieraan.co.uk/~Josh/createCharacter.php", params);
		
		try {
			JSONObject JSONParser = new JSONObject(JSONResult);

			if(JSONParser.getInt("status") == 0) {
				errorMessage.setValue(JSONParser.getString("message"));
			} else {
				errorMessage.setValue("");
				Board.menuStack.pop();
				Board.menuStack.add(new MainMenu());
			}
		} catch (JSONException e) {
			e.printStackTrace();
			errorMessage.setValue("Unexpected Error!");
		}
	}
}
