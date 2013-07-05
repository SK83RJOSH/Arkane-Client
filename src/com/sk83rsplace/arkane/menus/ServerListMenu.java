package com.sk83rsplace.arkane.menus;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Fonts;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.ServerComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.utils.HTTP;

public class ServerListMenu extends Menu {
	private TextComponent errorMessage;
	private ArrayList<ServerComponent> serverComponents = new ArrayList<ServerComponent>();

	public ServerListMenu() {
		errorMessage = new TextComponent("", Color.yellow, -1, 392);

		addComponent(new TextComponent("Server Listing", Color.white, Fonts.mediumPoint, -1, 26));

		getServers();
		
		addComponent(errorMessage);
		addComponent(new ButtonComponent("Back", -1, Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new MainMenu());
			}
		});
	}
	
	private void getServers() {
		HTTP httpConnection = new HTTP();
		String JSONResult = httpConnection.post("http://vps.kieraan.co.uk/~Josh/listServers.php", new HashMap<String, String>());
		
		try {
			JSONObject JSONParser = new JSONObject(JSONResult);
			JSONArray servers = JSONParser.getJSONArray("servers");
			
			for(int index = 0; index < servers.length(); index++) {
				JSONObject server = servers.getJSONObject(index);
				ServerComponent component = new ServerComponent(-1, 64 + (64 * index), server.getString("server_address"));
				serverComponents.add(component);
				addComponent(component);
			}
			
			if(servers.length() == 0)
				addComponent(new TextComponent("No Servers Online!", Color.orange, -1, 64));
		} catch (JSONException e) {
			e.printStackTrace();
			errorMessage.setValue("Unexpected Error!");
		}
	}
}
