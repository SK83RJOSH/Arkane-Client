package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.GUI.Images;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.ImageComponent;
import com.sk83rsplace.arkane.GUI.components.ShowcaseComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.client.resources.TerrainResource;

public class MapEditor extends Menu {
	private int setCount = 0;
	private int setPage = 1;
	TerrainResource selectedSet = null;
	private ButtonComponent previousSetPage;
	private static ButtonComponent nextSetPage;
	
	public MapEditor() {
		setColor(new Color(0f, 0f, 0f, 0f));
		
		final ButtonComponent ascendLayer = new ButtonComponent("", Images.vertArrow, Board.getWidth() - (Images.vertArrow.getWidth() / 3) - 8, 27 + Images.hortArrowRight.getHeight()).makeStatic();
		final ButtonComponent fieldToggle = new ButtonComponent("", Images.hide, Board.getWidth() - (Images.vertArrow.getWidth() / 3) + (((Images.vertArrow.getWidth() / 3) / 2) - ((Images.hide.getWidth() / 3) / 2)) - 8, 27 + Images.hortArrowRight.getHeight() + Images.vertArrow.getHeight() + 8) {
			public void onClick() {
				if(this.buttonImage != Images.show) {
					this.buttonImage = Images.show;
				} else {
					this.buttonImage = Images.hide;
				}
			}
		}.makeStatic();
		final ButtonComponent descendLayer = new ButtonComponent("", Images.vertArrow.getFlippedCopy(false, true), Board.getWidth() - (Images.vertArrow.getWidth() / 3) - 8,  27 + Images.hortArrowRight.getHeight() + Images.vertArrow.getHeight() + Images.hide.getHeight() + 16).makeStatic();
		final ImageComponent panel = new ImageComponent(0, 0, Images.smallPanel);
		final TextComponent alternatesText = new TextComponent("Alternates", Color.white, -1, Images.smallPanel.getHeight() + 8);
		final TextComponent upgradesText = new TextComponent("Upgrades", Color.white, -1, (Images.smallPanel.getHeight() * 2) + 37);
		previousSetPage = new ButtonComponent("", Images.hortArrowLeft, 8, 8) {
			public void onClick() {
				setPage--;
				
				showSetPage();
				
				if(setPage == 1)
					removeComponent(this);
								
				addComponent(nextSetPage);
			};	
		}.makeStatic();
		nextSetPage = new ButtonComponent("", Images.hortArrowRight, Board.getWidth() - (Images.hortArrowRight.getWidth() / 3) - 8, 8) {
			public void onClick() {
				setPage++;
				
				showSetPage();
				
				if(setPage == Math.ceil(setCount / 10f))
					removeComponent(this);
								
				addComponent(previousSetPage);
			};
		}.makeStatic();
		
		addComponent(panel);
		addComponent(new ButtonComponent("", Images.expandDown, (Board.getWidth() / 2) - ((Images.expandDown.getWidth() / 3) / 2), Images.smallPanel.getHeight() - 1) {
			public void onClick() {
				if(this.buttonImage != Images.expandDown) {
					this.buttonImage = Images.expandDown;
					
					panel.setImage(Images.smallPanel);
					set(getX(), Images.smallPanel.getHeight() - 1);

					addComponent(ascendLayer);
					addComponent(fieldToggle);
					addComponent(descendLayer);
					removeComponent(alternatesText);
					removeComponent(upgradesText);
				} else {
					this.buttonImage = Images.expandUp;
					
					panel.setImage(Images.largePanel);
					set(getX(), Images.largePanel.getHeight() - 1);
					
					removeComponent(ascendLayer);
					removeComponent(fieldToggle);
					removeComponent(descendLayer);
					addComponent(alternatesText);
					addComponent(upgradesText);
				}
			};
		}.makeStatic());
				
		showSetPage();
		
		if(setCount >= 10)
			addComponent(nextSetPage);
			
		addComponent(ascendLayer);
		addComponent(fieldToggle);
		addComponent(descendLayer);
	
		addComponent(new ButtonComponent("Exit", Images.miniButton, 8, Board.getHeight() - Images.miniButton.getHeight() - 8) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new DeveloperMenu());
			}
		});
		
		addComponent(new ButtonComponent("Load", Images.miniButton, Board.getWidth() - ((Images.miniButton.getWidth() / 3) * 2) - 16, Board.getHeight() - Images.miniButton.getHeight() - 8));
		addComponent(new ButtonComponent("Save", Images.miniButton, Board.getWidth() - (Images.miniButton.getWidth() / 3) - 8, Board.getHeight() - Images.miniButton.getHeight() - 8));
	}
	
	private void selectSet(TerrainResource selectedSet) {
		this.selectedSet = selectedSet;
	}
	
	private void showSetPage() {		
		for(Component c : components) {
			if(c instanceof ShowcaseComponent && c.getY() == 8) {
				removeComponent(c);
			}
		}
		
		setCount = 0;
		
		for(final TerrainResource resources : Board.res.getTerrainDefinitions().values()) {			
			if(setCount < 10 * setPage && setCount >= 10 * (setPage - 1 > 0 ? setPage - 1 : 0)) {
				ShowcaseComponent c = new ShowcaseComponent(resources.getBase(0).getResource(), 8 + (Images.hortArrowLeft.getWidth() / 3) + 19 + (((Images.tileSelector.getWidth() / 3) + 16) * (setCount - (10 * (setPage - 1 > 0 ? setPage - 1 : 0)))), 8) {
					public void onClick() {

						selectSet(resources);
						
						for(Component c : components) {
							if(c instanceof ShowcaseComponent && c.getY() == getY()) {
								if(c != this) {
									((ShowcaseComponent) c).setToggled(false);
								}
							}
						}
					}
				};
				
				if(resources == selectedSet) {
					c.setToggled(true);
				} else if(selectedSet == null) {
					c.setToggled(true);
					selectSet(resources);
				}
		
				addComponent(c);
			}
			
			setCount++;
		}
	}
}
