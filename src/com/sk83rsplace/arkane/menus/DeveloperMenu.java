package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Fonts;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;

public class DeveloperMenu extends Menu {
	public DeveloperMenu() {
		addComponent(new TextComponent("Developer Tools", Color.white, Fonts.mediumPoint, -1, 15));
		
		addComponent(new ButtonComponent("Color Picker", 15, 65) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ColorPickerMenu());
			}
		});
		
		addComponent(new ButtonComponent("Credits Menu", Board.getWidth() - 258 - 15, 65) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new CreditsMenu());
			}
		});
		
		addComponent(new ButtonComponent("Resource Manager", 15, 128) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ResourceManagerMenu());
			}
		});
		
		addComponent(new ButtonComponent("Back", -1, Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new MainMenu());
			}
		});
	}
}
