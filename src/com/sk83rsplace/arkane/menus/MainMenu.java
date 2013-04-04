package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.GUI.components.TextInputComponent;
import com.sk83rsplace.arkane.client.Board;

public class MainMenu extends Menu {
	public MainMenu() {
		addComponent(new TextComponent("Welcome " + Board.username + ",", Color.white, -1, 200));
		addComponent(new TextComponent("Join a a god-damn server already!", Color.white, -1, 216));
		addComponent(new TextInputComponent("Server Host", "vps.kieraan.co.uk", -1, 250));
		addComponent(new ButtonComponent("Connect", -1, 300));
	}
}
