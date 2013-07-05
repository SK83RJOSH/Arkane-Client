package com.sk83rsplace.arkane.menus;

import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.ColorPickerComponent;
import com.sk83rsplace.arkane.client.Board;

public class ColorPickerMenu extends Menu {
	public ColorPickerMenu() {
		addComponent(new ColorPickerComponent(-1, Board.getHeight() / 2 - 128));
		addComponent(new ButtonComponent("Back", 15, Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new DeveloperMenu());
			}
		});
		addComponent(new ButtonComponent("Reset", Board.getWidth() - 15 - 258, Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ColorPickerMenu());
			}
		});
	}
}
