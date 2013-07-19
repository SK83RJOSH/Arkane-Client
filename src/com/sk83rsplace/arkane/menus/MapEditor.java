package com.sk83rsplace.arkane.menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.GUI.Images;
import com.sk83rsplace.arkane.GUI.Menu;
import com.sk83rsplace.arkane.GUI.components.ButtonComponent;
import com.sk83rsplace.arkane.GUI.components.PanelComponent;
import com.sk83rsplace.arkane.GUI.components.ShowcaseComponent;
import com.sk83rsplace.arkane.GUI.components.TextComponent;
import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.client.resources.TerrainBase;
import com.sk83rsplace.arkane.client.resources.TerrainResource;
import com.sk83rsplace.arkane.client.resources.TerrainUpgrade;

public class MapEditor extends Menu {
	private int setPage = 1;
	private int setCount = Board.res.getTerrainDefinitions().size();
	private int alternatePage = 1;
	private int alternateCount = 0;
	private int upgradePage = 1;
	private int upgradeCount = 0;
	private boolean panelExpanded = false;
	public TerrainResource selectedSet = null;
	public TerrainBase selectedAlternate = null;
	public TerrainUpgrade selectedUpgrade = null;
	private ButtonComponent previousSetPage, nextSetPage, previousAlternatePage, nextAlternatePage, previousUpgradePage, nextUpgradePage, ascendLayer, fieldToggle, descendLayer, panelToggle, exitButton, loadButton, saveButton;
	private PanelComponent panel;
	private TextComponent alternatesText, upgradesText;
	
	public MapEditor() {
		setColor(new Color(0f, 0f, 0f, 0f));
		Board.showGrid = true;
		
		ascendLayer = new ButtonComponent(Images.vertArrow, Board.getWidth() - (Images.vertArrow.getWidth() / 3) - 8, 27 + Images.hortArrowRight.getHeight()) {
			public void onClick() {
				Board.z++;
			}
		};
		fieldToggle = new ButtonComponent(Images.hide, Board.getWidth() - (Images.vertArrow.getWidth() / 3) + (((Images.vertArrow.getWidth() / 3) / 2) - ((Images.hide.getWidth() / 3) / 2)) - 8, 27 + Images.hortArrowRight.getHeight() + Images.vertArrow.getHeight() + 8) {
			public void onClick() {
				if(getImage() != Images.show) {
					Board.showGrid = false;
					setImage(Images.show);
				} else {
					Board.showGrid = true;
					setImage(Images.hide);
				}
			}
		};
		descendLayer = new ButtonComponent(Images.vertArrow.getFlippedCopy(false, true), Board.getWidth() - (Images.vertArrow.getWidth() / 3) - 8,  27 + Images.hortArrowRight.getHeight() + Images.vertArrow.getHeight() + Images.hide.getHeight() + 16) {
			public void onClick() {
				Board.z--;
			}
		};
		panel = new PanelComponent(0, 0, -1, 1, Images.smallPanel);
		panelToggle = new ButtonComponent(Images.expandDown, -1, Images.smallPanel.getHeight() - 3) {
			public void onClick() {
				if(getImage() != Images.expandDown)
					collapsePanel();
				else
					expandPanel();
			};
		};
		alternatesText = new TextComponent("Alternates", Color.white, -1, Images.smallPanel.getHeight() + 8);
		upgradesText = new TextComponent("Upgrades", Color.white, -1, (Images.smallPanel.getHeight() * 2) + 37);
		previousSetPage = new ButtonComponent(Images.hortArrowLeft, 8, 8) {
			public void onClick() {
				setPage--;
				
				showSetPage();
			};	
		};
		nextSetPage = new ButtonComponent(Images.hortArrowRight, Board.getWidth() - (Images.hortArrowRight.getWidth() / 3) - 8, 8) {
			public void onClick() {
				setPage++;
				
				showSetPage();
			};
		};
		previousAlternatePage = new ButtonComponent(Images.hortArrowLeft, 8, 96) {
			public void onClick() {
				alternatePage--;
				
				showAlternatePage();
			};	
		};
		nextAlternatePage = new ButtonComponent(Images.hortArrowRight, Board.getWidth() - (Images.hortArrowRight.getWidth() / 3) - 8, 96) {
			public void onClick() {
				alternatePage++;
				
				showAlternatePage();
			};
		};
		previousUpgradePage = new ButtonComponent(Images.hortArrowLeft, 8, 184) {
			public void onClick() {
				upgradePage--;
				
				showUpgradePage();
			};	
		};
		nextUpgradePage = new ButtonComponent(Images.hortArrowRight, Board.getWidth() - (Images.hortArrowRight.getWidth() / 3) - 8, 184) {
			public void onClick() {
				upgradePage++;
				
				showUpgradePage();
			};
		};
		exitButton = new ButtonComponent("Exit", Images.miniButton, 8, Board.getHeight() - Images.miniButton.getHeight() - 8) {
			public void onClick() {
				Board.menuStack.pop();
				Board.menuStack.add(new DeveloperMenu());
			}
		};
		loadButton = new ButtonComponent("Load", Images.miniButton, Board.getWidth() - ((Images.miniButton.getWidth() / 3) * 2) - 16, Board.getHeight() - Images.miniButton.getHeight() - 8);
		saveButton = new ButtonComponent("Save", Images.miniButton, Board.getWidth() - (Images.miniButton.getWidth() / 3) - 8, Board.getHeight() - Images.miniButton.getHeight() - 8);
		
		addComponent(panel);
		addComponent(panelToggle);
				
		showSetPage();
			
		addComponent(ascendLayer);
		addComponent(fieldToggle);
		addComponent(descendLayer);
	
		addComponent(exitButton);
		addComponent(loadButton);
		addComponent(saveButton);
	}
	
	public void resize() {
		ascendLayer.set(Board.getWidth() - (Images.vertArrow.getWidth() / 3) - 8, 27 + Images.hortArrowRight.getHeight());
		fieldToggle.set(Board.getWidth() - (Images.vertArrow.getWidth() / 3) + (((Images.vertArrow.getWidth() / 3) / 2) - ((Images.hide.getWidth() / 3) / 2)) - 8, 27 + Images.hortArrowRight.getHeight() + Images.vertArrow.getHeight() + 8);
		descendLayer.set(Board.getWidth() - (Images.vertArrow.getWidth() / 3) - 8,  27 + Images.hortArrowRight.getHeight() + Images.vertArrow.getHeight() + Images.hide.getHeight() + 16);
		nextSetPage.set(Board.getWidth() - (Images.hortArrowRight.getWidth() / 3) - 8, nextSetPage.getY());
		nextAlternatePage.set(Board.getWidth() - (Images.hortArrowRight.getWidth() / 3) - 8, nextAlternatePage.getY());
		nextUpgradePage.set(Board.getWidth() - (Images.hortArrowRight.getWidth() / 3) - 8, nextUpgradePage.getY());
		
		showSetPage();
		
		if(panelExpanded) {
			showAlternatePage();
			showUpgradePage();
		}
		
		exitButton.set(8, Board.getHeight() - Images.miniButton.getHeight() - 8);
		loadButton.set(Board.getWidth() - ((Images.miniButton.getWidth() / 3) * 2) - 16, Board.getHeight() - Images.miniButton.getHeight() - 8);
		saveButton.set(Board.getWidth() - (Images.miniButton.getWidth() / 3) - 8, Board.getHeight() - Images.miniButton.getHeight() - 8);
	}
	
	private void selectSet(TerrainResource selectedSet) {
		this.selectedSet = selectedSet;
		
		selectAlternate(selectedSet.getBase(0));
		alternatePage = 1;
		alternateCount = selectedSet.getBases().size();
		
		if(panelExpanded) {
			showAlternatePage();
			showUpgradePage();
		}
	}
	
	private void showSetPage() {		
		hideComponentRow(8);
		int freeSpace = (Board.getWidth() - (8 + ((Images.hortArrowLeft.getWidth() / 3)) * 2)) + 8;
		int displayable = (int) Math.floor(freeSpace * 1f / ((Images.tileSelector.getWidth() / 3) + 16));
		int padding = (freeSpace - (displayable * ((Images.tileSelector.getWidth() / 3) + 16))) / 2;
		
		if(setPage > Math.ceil(setCount / (displayable * 1f)))
			setPage = (int) (Math.ceil(setCount) / (displayable * 1f));
		
		if(setPage < 1)
			setPage = 1;
				
		if(setCount > displayable && setPage < Math.ceil(setCount / (displayable * 1f)))
			addComponent(nextSetPage);
		if(setPage > 1)
			addComponent(previousSetPage);
		
		int offset = 0;
		for(final TerrainResource resources : Board.res.getTerrainDefinitions().values()) {	
			if(offset < displayable * setPage && offset >= displayable * (setPage - 1 > 0 ? setPage - 1 : 0)) {
				ShowcaseComponent c = new ShowcaseComponent(resources.getBase(0).getResource(), 8 + (Images.hortArrowLeft.getWidth() / 3) + padding + (((Images.tileSelector.getWidth() / 3) + 16) * (offset - (displayable * (setPage - 1 > 0 ? setPage - 1 : 0)))), 8) {
					public void onClick() {
						selectSet(resources);
						
						if(getToggled()) {
							try {
								new Sound("res/sounds/click.ogg").play();
							} catch (SlickException e) {
								e.printStackTrace();
							}
						}
						
						for(Component c : components) {
							if(c instanceof ShowcaseComponent && c.getY() == getY()) {
								if(c != this) {
									((ShowcaseComponent) c).setToggled(false);
								} else {
									((ShowcaseComponent) c).setToggled(true);
								}
							}
						}
					}
				};
				
				if(resources == selectedSet)
					c.setToggled(true);
				else if(selectedSet == null) {
					c.setToggled(true);
					selectSet(resources);
				}
		
				addComponent(c);
			}
			
			offset++;
		}
	}	
	
	private void selectAlternate(TerrainBase selectedAlternate) {
		this.selectedAlternate = selectedAlternate;
		
		if(panelExpanded)
			showUpgradePage();
		
		selectUpgrade(null);
		upgradePage = 1;
		upgradeCount = selectedAlternate.getValidUpgrades().size();
	}
	
	private void showAlternatePage() {		
		hideComponentRow(96);
		int freeSpace = (Board.getWidth() - (8 + ((Images.hortArrowLeft.getWidth() / 3)) * 2)) + 8;
		int displayable = (int) Math.floor(freeSpace * 1f / ((Images.tileSelector.getWidth() / 3) + 16));
		int padding = (freeSpace - (displayable * ((Images.tileSelector.getWidth() / 3) + 16))) / 2;
		
		if(alternatePage > Math.ceil(alternateCount / (displayable * 1f)))
			alternatePage = (int) (Math.ceil(alternateCount) / (displayable * 1f));
		
		if(setPage < 1)
			alternatePage = 1;
		
		if(alternateCount > displayable && alternatePage < Math.ceil(alternateCount / (displayable * 1f)))
			addComponent(nextAlternatePage);
		if(alternatePage > 1)
			addComponent(previousAlternatePage);
		
		int offset = 0;
		for(final TerrainBase alternate : selectedSet.getBases()) {
			if(offset < displayable * alternatePage && offset >= displayable * (alternatePage - 1 > 0 ? alternatePage - 1 : 0)) {				
				ShowcaseComponent c = new ShowcaseComponent(alternate.getResource(), 8 + (Images.hortArrowLeft.getWidth() / 3) + padding + (((Images.tileSelector.getWidth() / 3) + 16) * (offset - (displayable * (alternatePage - 1 > 0 ? alternatePage - 1 : 0)))), 96) {
					public void onClick() {
						selectAlternate(alternate);
						
						if(getToggled()) {
							try {
								new Sound("res/sounds/click.ogg").play();
							} catch (SlickException e) {
								e.printStackTrace();
							}
						}
						
						for(Component c : components) {
							if(c instanceof ShowcaseComponent && c.getY() == getY()) {
								if(c != this) {
									((ShowcaseComponent) c).setToggled(false);
								} else {
									((ShowcaseComponent) c).setToggled(true);
								}
							}
						}
					}
				};
				
				if(alternate == selectedAlternate) {
					c.setToggled(true);
				} else if(selectedAlternate == null) {
					c.setToggled(true);
					selectAlternate(alternate);
				}
		
				addComponent(c);
			}

			offset++;
		}
	}
	
	private void selectUpgrade(TerrainUpgrade selectedUpgrade) {
		this.selectedUpgrade = selectedUpgrade;
	}
	
	private void showUpgradePage() {		
		hideComponentRow(184);
		int freeSpace = (Board.getWidth() - (8 + ((Images.hortArrowLeft.getWidth() / 3)) * 2)) + 8;
		int displayable = (int) Math.floor(freeSpace * 1f / ((Images.tileSelector.getWidth() / 3) + 16));
		int padding = (freeSpace - (displayable * ((Images.tileSelector.getWidth() / 3) + 16))) / 2;
		
		if(upgradePage > Math.ceil(upgradeCount / (displayable * 1f)))
			upgradePage = (int) (Math.ceil(upgradeCount) / (displayable * 1f));
		
		if(setPage < 1)
			upgradePage = 1;
		
		if(upgradeCount > displayable && upgradePage < Math.ceil(upgradeCount / (displayable * 1f)))
			addComponent(nextUpgradePage);
		if(upgradePage > 1)
			addComponent(previousUpgradePage);
		
		int offset = 0;
		for(final TerrainUpgrade upgrade: selectedAlternate.getValidUpgrades()) {	
			if(offset < displayable * upgradePage && offset >= displayable * (upgradePage - 1 > 0 ? upgradePage - 1 : 0)) {
				ShowcaseComponent c = new ShowcaseComponent(upgrade.getResource(), 8 + (Images.hortArrowLeft.getWidth() / 3) + padding + (((Images.tileSelector.getWidth() / 3) + 16) * (offset - (displayable * (upgradePage - 1 > 0 ? upgradePage - 1 : 0)))), 184) {
					public void onClick() {
						try {
							new Sound("res/sounds/click.ogg").play();
						} catch (SlickException e) {
							e.printStackTrace();
						}
						
						if(getToggled())
							selectUpgrade(upgrade);
						else
							selectUpgrade(null);
						
						for(Component c : components) {
							if(c instanceof ShowcaseComponent && c.getY() == getY()) {
								if(c != this) {
									((ShowcaseComponent) c).setToggled(false);
								}
							}
						}
					}
				};
				
				if(upgrade == selectedUpgrade)
					c.setToggled(true);
		
				addComponent(c);
			}
			
			offset++;
		}
	}
	
	private void expandPanel() {		
		panel.setImage(Images.largePanel);
		panelToggle.setImage(Images.expandUp);
		panelToggle.set(-1, Images.largePanel.getHeight() - 3);
		
		showAlternatePage();
		showUpgradePage();
		
		removeComponent(ascendLayer);
		removeComponent(fieldToggle);
		removeComponent(descendLayer);
		addComponent(alternatesText);
		addComponent(upgradesText);
		
		panelExpanded = true;
	}
	
	private void collapsePanel() {
		panel.setImage(Images.smallPanel);
		panelToggle.setImage(Images.expandDown);
		panelToggle.set(-1, Images.smallPanel.getHeight() - 3);

		addComponent(ascendLayer);
		addComponent(fieldToggle);
		addComponent(descendLayer);
		removeComponent(alternatesText);
		removeComponent(upgradesText);

		hideComponentRow(96);
		hideComponentRow(184);
		
		panelExpanded = false;
	}
	
	private void hideComponentRow(int rowYPosition) {
		for(Component c : components) {
			if(c.getY() == rowYPosition) {
				removeComponent(c);
			}
		}
	}
}
