package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Fonts;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.ImageComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.client.resources.TerrainBase;
import com.sk83rsplace.arkane.client.resources.TerrainResource;
import com.sk83rsplace.arkane.client.resources.TerrainUpgrade;

public class ResourcesMenu extends Menu {
	public ResourcesMenu() {
		addComponent(new TextComponent("Resources", Color.white, Fonts.mediumPoint, -1, 15));
		
		int offsetY = 40;
		for(TerrainResource resources : Board.res.getTerrainDefinitions().values()) {
			addComponent(new TextComponent(resources.getReferenceName() + " (" + resources.getBases().size() + " Base" + (resources.getBases().size() > 1 || resources.getBases().size() == 0 ? "s" : "") + ", " + resources.getUpgrades().size() + " Upgrade" + (resources.getUpgrades().size() > 1 || resources.getUpgrades().size() == 0 ? "s" : "") + ")", Color.white, Fonts.normalPoint, -1, offsetY));
			
			int offsetX = 5;
			int upgradeOffsetX = 5;
			boolean hadUpgrades = false;
			for(final TerrainBase base : resources.getBases()) {
				ImageComponent image = new ImageComponent(offsetX, offsetY + 20, "res/button.png");
				image.setImage(base.getResource().getScaledCopy(0.5f));
				
				addComponent(image);
				
				offsetX += 69;
				
				for(final TerrainUpgrade upgrade : base.getValidUpgrades()) {
					ImageComponent baseWithUpgradeImage = new ImageComponent(upgradeOffsetX, offsetY + 89, "res/button.png");
					baseWithUpgradeImage.setImage(base.getResource().getScaledCopy(0.145f));
					
					addComponent(baseWithUpgradeImage);
					
					ImageComponent upgradeImage = new ImageComponent(upgradeOffsetX, offsetY + 89, "res/button.png");
					upgradeImage.setImage(upgrade.getResource().getScaledCopy(0.5f));
					
					addComponent(upgradeImage);
					
					upgradeOffsetX += 69;
				}
				
				if(base.getValidUpgrades().size() > 0)
					hadUpgrades = true;
			}
			
			if(hadUpgrades)
				offsetY += 128;
			else
				offsetY += 74;
		}
		
		addComponent(new ButtonComponent("Back", -1, Board.getHeight() - 15 - 50) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new ResourceManagerMenu());
			}
		});
	}
}
