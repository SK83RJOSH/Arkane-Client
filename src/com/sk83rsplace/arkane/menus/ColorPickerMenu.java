package com.sk83rsplace.arkane.menus;

import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ColorPickerComponent;
import com.sk83rsplace.arkane.client.Board;

public class ColorPickerMenu extends Menu {
	public ColorPickerMenu() {
		addComponent(new ColorPickerComponent(-1, Board.getHeight() / 2 - 128));
	}
}
