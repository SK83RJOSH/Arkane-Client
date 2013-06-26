package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Fonts;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;

public class TestsMenu extends Menu {
	public TestsMenu() {
		addComponent(new TextComponent("Component Tests", Color.white, Fonts.mediumPoint, -1, 15));
		
		addComponent(new ButtonComponent("Color Picker", 15, 65) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ColorPickerMenu());
			}
		});
		
		addComponent(new ButtonComponent("Hotbed Resources", 15, 128) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ResourcesMenu());
			}
		});
		
		addComponent(new ButtonComponent("Credits Menu", Board.getWidth() - 258 - 15, 65) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new CreditsMenu());
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
