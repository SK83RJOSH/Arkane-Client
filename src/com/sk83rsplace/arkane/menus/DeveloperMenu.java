package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Fonts;
import com.sk83rsplace.arkane.GUI.Images;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;

public class DeveloperMenu extends Menu {
	private ButtonComponent colorPicker, creditsMenu, resourceManager, levelEditor,  back;
	
	public DeveloperMenu() {
		colorPicker = new ButtonComponent("Color Picker", (Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, 65) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ColorPickerMenu());
			}
		};
		creditsMenu = new ButtonComponent("Credits Menu", Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), 65) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new CreditsMenu());
			}
		};
		resourceManager = new ButtonComponent("Resource Manager", (Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, 128) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ResourceManagerMenu());
			}
		};
		levelEditor = new ButtonComponent("Level Editor", Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), 128) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new MapEditor());
			}
		};
		back = new ButtonComponent("Back", -1, Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new MainMenu());
			}
		};
		
		addComponent(new TextComponent("Developer Tools", Color.white, Fonts.mediumPoint, -1, 15));
		
		addComponent(colorPicker);
		addComponent(creditsMenu);
		addComponent(resourceManager);
		addComponent(levelEditor);
		addComponent(back);
	}
	
	public void resize() {
		colorPicker.set((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, colorPicker.getY());
		resourceManager.set((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, resourceManager.getY());
		creditsMenu.set(Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), creditsMenu.getY());
		levelEditor.set(Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), levelEditor.getY());
		back.set(-1, Board.getHeight() - 15 - 50);
	}
}
