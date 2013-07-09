package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Images;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.ImageComponent;
import com.sk83rsplace.arkane.client.Board;

public class MapEditor extends Menu {
	public MapEditor() {
		setColor(new Color(0f, 0f, 0f, 0f));
		
		addComponent(new ImageComponent(0, 0, "res/panel.png"));
		
		addComponent(new ButtonComponent("", Images.hortArrowLeft, 8, 8));
		addComponent(new ButtonComponent("", Images.hortArrowRight, Board.getWidth() - (Images.hortArrowRight.getWidth() / 3) - 8, 8));
		
		addComponent(new ButtonComponent("", Images.vertArrow, Board.getWidth() - (Images.vertArrow.getWidth() / 3) - 8, 27 + Images.hortArrowRight.getHeight()));
		
		addComponent(new ButtonComponent("", Images.hide, Board.getWidth() - (Images.vertArrow.getWidth() / 3) + (((Images.vertArrow.getWidth() / 3) / 2) - ((Images.hide.getWidth() / 3) / 2)) - 8, 27 + Images.hortArrowRight.getHeight() + Images.vertArrow.getHeight() + 8) {
			public void onClick() {
				if(this.buttonImage != Images.show) {
					this.buttonImage = Images.show;
				} else {
					this.buttonImage = Images.hide;
				}
			}
		});
		
		addComponent(new ButtonComponent("", Images.vertArrow.getFlippedCopy(false, true), Board.getWidth() - (Images.vertArrow.getWidth() / 3) - 8,  27 + Images.hortArrowRight.getHeight() + Images.vertArrow.getHeight() + Images.hide.getHeight() + 16));
	
		addComponent(new ButtonComponent("Exit", Images.miniButton, 8, Board.getHeight() - Images.miniButton.getHeight() - 8) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new DeveloperMenu());
			}
		});
		
		addComponent(new ButtonComponent("Load", Images.miniButton, Board.getWidth() - ((Images.miniButton.getWidth() / 3) * 2) - 16, Board.getHeight() - Images.miniButton.getHeight() - 8));
		addComponent(new ButtonComponent("Save", Images.miniButton, Board.getWidth() - (Images.miniButton.getWidth() / 3) - 8, Board.getHeight() - Images.miniButton.getHeight() - 8));
	}
}
