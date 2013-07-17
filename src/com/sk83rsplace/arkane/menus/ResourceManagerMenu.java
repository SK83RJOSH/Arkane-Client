package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Fonts;
import com.sk83rsplace.arkane.GUI.Images;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;

public class ResourceManagerMenu extends Menu {
	private TextComponent statusText;
	private ButtonComponent refreshButton, forceButton, backButton;
	
	public ResourceManagerMenu() {
		refreshButton = new ButtonComponent("Refresh Resources", (Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, 65) {
			public void onClick() {
				long startTime = System.currentTimeMillis();
				statusText.setValue("Refreshing Resources . . .");
				Board.res.scanResources();
				statusText.setValue("Resources Refreshed in " + (System.currentTimeMillis() - startTime) + "ms.");
			}
		};
		forceButton = new ButtonComponent("Force-Check Resources", Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), 65) {
			public void onClick() {
				long startTime = System.currentTimeMillis();
				statusText.setValue("Checking Resources . . .");
				Board.res.checkResources();
				Board.res.scanResources();
				statusText.setValue("Resources Checked & Refreshed in " + (System.currentTimeMillis() - startTime) + "ms.");
			}
		};
		statusText = new TextComponent("Waiting for User Input . . .", Color.yellow, -1, Board.getHeight() - Fonts.mediumPoint.getLineHeight() - 78);
		backButton = new ButtonComponent("Back", -1, Board.getHeight() - 63) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new DeveloperMenu());
			}
		};
		
		addComponent(new TextComponent("Resource Management", Color.white, Fonts.mediumPoint, -1, 15));		
		
		addComponent(statusText);
		addComponent(refreshButton);
		addComponent(forceButton);
		addComponent(backButton);
	}
	
	public void resize() {
		refreshButton.set((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4, 65);
		forceButton.set(Board.getWidth() - ((Board.getWidth() - ((Images.defaultImage.getWidth() / 3) * 2)) / 4) - (Images.defaultImage.getWidth() / 3), 65);
		statusText.set(-1, Board.getHeight() - Fonts.mediumPoint.getLineHeight() - 78);
		backButton.set(-1, Board.getHeight() - 63);
	}
}
