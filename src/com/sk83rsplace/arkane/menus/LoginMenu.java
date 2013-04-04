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
import com.sk83rsplace.arkane.HTTP.HTTP;
import com.sk83rsplace.arkane.client.Board;

public class LoginMenu extends Menu {
	private TextInputComponent usernameField;
	private TextInputComponent passwordField;
	private TextComponent errorMessage;
	
	public LoginMenu() {
		usernameField = new TextInputComponent("Username", "", -1, 200);
		passwordField = new TextInputComponent("Password", "", -1, 250, true);
		errorMessage = new TextComponent("", Color.yellow, -1, 360);
		
		addComponent(new TextComponent("Please Log-In!", Color.white, -1, 150));
		addComponent(usernameField);
		addComponent(passwordField);
		addComponent(new ButtonComponent("Login", -1, 300) {
			public void onClick() {
				checkLogin();
			}
		});
		addComponent(errorMessage);
	}
	
	void checkLogin() {		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("username", usernameField.getValue());
		params.put("password", HTTP.MD5(passwordField.getValue()));
		
		HTTP httpConnection = new HTTP();
		String JSONResult = httpConnection.post("http://vps.kieraan.co.uk/~Josh/login.php", params);
		
		try {
			JSONObject JSONParser = new JSONObject(JSONResult);

			if(JSONParser.getInt("status") == 0) {
				errorMessage.setValue("Log-In Details Incorrect!");
			} else {
				errorMessage.setValue("");
				Board.username = usernameField.getValue();
				Board.menuStack.pop();
				Board.menuStack.add(new MainMenu());
			}
		} catch (JSONException e) {
			e.printStackTrace();
			errorMessage.setValue("Unexpected Error!");
		}

	}
}
