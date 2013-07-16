package com.sk83rsplace.arkane.menus;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Images;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.GUI.components.TextInputComponent;
import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.utils.HTTP;

public class CreationMenu extends Menu {
	private TextComponent errorMessage, headingMessage;
	private TextInputComponent characterInput;
	private ButtonComponent cancelButton, createButton;
	
	public CreationMenu() {
		errorMessage = new TextComponent("", Color.yellow, -1, (Board.getHeight() / 2) + 32);
		headingMessage = new TextComponent("Create a new Character!", Color.white, -1, (Board.getHeight() / 2) - 16);
		characterInput = new TextInputComponent("Character Name", "", -1, (Board.getHeight() / 2) + 16);
		cancelButton = new ButtonComponent("Cancel", (Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new MainMenu());
			}
		};
		createButton = new ButtonComponent("Create", Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), Board.getHeight() - 15 - 50) {
			public void onClick() {
				if(characterInput.getValue().replace(" ", "").equals("") || characterInput.getValue().replace(" ", "").length() < 4)
					errorMessage.setValue("Invalid Name!");
				else {
					errorMessage.setValue("");
					createCharacter();
				}
			}
		};
		
		addComponent(headingMessage);
		addComponent(characterInput);
		addComponent(errorMessage);
		addComponent(cancelButton);
		addComponent(createButton);
	}
	
	public void resize() {
		headingMessage.set(-1, (Board.getHeight() / 2) - 16);
		characterInput.set(-1, (Board.getHeight() / 2) + 16);
		errorMessage.set(-1, (Board.getHeight() / 2) + 32);
		cancelButton.set((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, Board.getHeight() - 15 - 50);
		createButton.set(Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), Board.getHeight() - 15 - 50);
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
