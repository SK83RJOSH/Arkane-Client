package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;

public class TestsMenu extends Menu {
	public TestsMenu() {
		addComponent(new TextComponent("Component Tests", Color.white, -1, 15));
		
		addComponent(new ButtonComponent("Color Picker", -1, 45) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ColorPickerMenu());
			}
		});
	}
}
