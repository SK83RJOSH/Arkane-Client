package com.sk83rsplace.arkane.menus;

import com.sk83rsplace.arkane.GUI.Images;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.ColorPickerComponent;
import com.sk83rsplace.arkane.client.Board;

public class ColorPickerMenu extends Menu {
	private ButtonComponent backButton, resetButton;
	
	public ColorPickerMenu() {
		backButton = new ButtonComponent("Back", (Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new DeveloperMenu());
			}
		};
		resetButton = new ButtonComponent("Reset", Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ColorPickerMenu());
			}
		};
		
		addComponent(new ColorPickerComponent(-1, -1));

		addComponent(backButton);
		addComponent(resetButton);
	}
	
	public void resize() {		
		backButton.set((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, Board.getHeight() - 15 - 50);
		resetButton.set(Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), Board.getHeight() - 15 - 50);
	}
}
