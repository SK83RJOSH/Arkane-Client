package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Fonts;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;

public class ResourceManagerMenu extends Menu {
	public ResourceManagerMenu() {
		addComponent(new TextComponent("Resource Management", Color.white, Fonts.mediumPoint, -1, 15));
		
		final TextComponent status = new TextComponent("Waiting for User Input . . .", Color.yellow, -1, Board.getHeight() - Fonts.mediumPoint.getLineHeight() - 78);
		addComponent(status);
		
		addComponent(new ButtonComponent("Refresh Resources", 15, 65) {
			public void onClick() {
				long startTime = System.currentTimeMillis();
				status.setValue("Refreshing Resources . . .");
				Board.res.scanResources();
				status.setValue("Resources Refreshed in " + (System.currentTimeMillis() - startTime) + "ms.");
			}
		});
		
		addComponent(new ButtonComponent("Force-Check Resources", Board.getWidth() - 271, 65) {
			public void onClick() {
				long startTime = System.currentTimeMillis();
				status.setValue("Checking Resources . . .");
				Board.res.checkResources();
				Board.res.scanResources();
				status.setValue("Resources Checked & Refreshed in " + (System.currentTimeMillis() - startTime) + "ms.");
			}
		});
		
		addComponent(new ButtonComponent("Back", -1, Board.getHeight() - 63) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new DeveloperMenu());
			}
		});
	}
}
