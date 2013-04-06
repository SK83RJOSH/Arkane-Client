package com.sk83rsplace.arkane.menus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.CheckBoxComponent;
import com.sk83rsplace.arkane.GUI.components.LabelComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.GUI.components.TextInputComponent;
import com.sk83rsplace.arkane.HTTP.HTTP;
import com.sk83rsplace.arkane.client.Board;

public class LoginMenu extends Menu {
	private TextInputComponent usernameField;
	private TextInputComponent passwordField;
	private TextComponent errorMessage;
	private CheckBoxComponent rememberMe;
	private Properties properties;
	private String userSettings;
	private File settings;
	
	public LoginMenu() {
		userSettings = System.getProperty("user.home") + "\\AppData\\Roaming\\.arkane\\settings.prop";
		settings = new File(userSettings);
		properties = new Properties();
		
		try {
			properties.load(new FileInputStream(userSettings));
		} catch (IOException e) {
			System.out.println("Create");
			
			try {
				if(settings.getParentFile().mkdirs()) {
					if(settings.createNewFile()) {
						properties.load(new FileInputStream(userSettings));
						
						properties.setProperty("username", "");
						properties.setProperty("password", "");
						properties.setProperty("remember_me", "0");
						
						properties.store(new FileOutputStream(settings), null);
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		usernameField = new TextInputComponent("Username", properties.getProperty("username"), -1, 200);
		passwordField = new TextInputComponent("Password", properties.getProperty("password"), -1, 250, true);
		errorMessage = new TextComponent("", Color.yellow, -1, 360);
		rememberMe = new CheckBoxComponent((properties.getProperty("remember_me").equals("1") ? true : false), false, 370, 392) {
			public void onInitialization(GameContainer container) {
				addComponent(new LabelComponent("Remember Me", this));
			}
		};
		
		addComponent(new TextComponent("Please Log-In!", Color.white, -1, 150));
		addComponent(usernameField);
		addComponent(passwordField);
		addComponent(new ButtonComponent("Login", -1, 300) {
			public void onClick() {
				checkLogin();
			}
		});
		addComponent(errorMessage);
		addComponent(rememberMe);
	}
	
	private void checkLogin() {		
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
				Board.userID = JSONParser.getInt("player_id");
				
				if(rememberMe.getValue()) {
					properties.setProperty("username", usernameField.getValue());
					properties.setProperty("password", passwordField.getValue());
					properties.setProperty("remember_me", "1");
				} else {
					properties.setProperty("username", "");
					properties.setProperty("password", "");
					properties.setProperty("remember_me", "0");
				}
				
				try {
					properties.store(new FileOutputStream(settings), null);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Board.menuStack.pop();
				Board.menuStack.add(new MainMenu());
			}
		} catch (JSONException e) {
			e.printStackTrace();
			errorMessage.setValue("Unexpected Error!");
		}
	}
}
