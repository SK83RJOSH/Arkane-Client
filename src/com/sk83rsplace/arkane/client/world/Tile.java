package com.sk83rsplace.arkane.client.world;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.sk83rsplace.arkane.client.Board;
import com.sk83rsplace.arkane.client.interfaces.IWorld;
import com.sk83rsplace.arkane.client.resources.TerrainResource;

public class Tile implements IWorld {
	private int base, upgrade;
	private TerrainResource definition;
	private Vector3f position;
	private Vector2f dimensions;
	
	public Tile(String definition, int base, int upgrade, int x, int y, int z) {
		setDefinition(Board.res.getTerrainDefinition(definition));
		setBase(base);
		setUpgrade(upgrade);
		setPos(x, y, z);
		setDimensions(new Vector2f(128, 128));
	}

	public void render(GameContainer container, Graphics g) {
		int renderX = (getX() * getWidth()) - (getY() % 2 == 0 ? getWidth() / 2 : 0);
		int renderY = (getY() * (getHeight() / 4)) - (getZ() * (getHeight() / 2));
		
		g.drawImage(getBaseResource(), renderX, renderY);
		
		if(getUpgrade() >= 0)
			g.drawImage(getUpgradeResource(), renderX, renderY - (getHeight() / 2));
	}
	
	public void setDefinition(TerrainResource definition) {
		this.definition = definition;
	}
	
	public TerrainResource getDefinition() {
		return definition;
	}
	
	public void setBase(int base) {
		this.base = base;
	}
	
	public int getBase() {
		return base;
	}
	
	public Image getBaseResource() {
		return definition.getBase(base).getResource();
	}
	
	public void setUpgrade(int upgrade) {
		if(definition.getBase(base).isValidUpgrade(definition.getUpgrade(upgrade)))
			this.upgrade = upgrade;
		else
			this.upgrade = -1;
	}
	
	public int getUpgrade() {
		return upgrade;
	}
	
	public Image getUpgradeResource() {
		return definition.getUpgrade(upgrade).getResource();
	}

	public Vector3f getPos() {
		return position;
	}

	public void setPos(int x, int y, int z) {
		position = new Vector3f(x, y, z);
	}

	public void setPos(Vector3f position) {
		this.position = position;
	}

	public void setX(int x) {
		setPos(x, getY(), getZ());
	}

	public int getX() {
		return (int) getPos().x;
	}

	public void setY(int y) {
		setPos(getX(), y, getZ());
	}

	public int getY() {
		return (int) getPos().y;
	}

	public void setZ(int z) {
		setPos(getX(), getY(), z);
	}

	public int getZ() {
		return (int) getPos().z;
	}

	
	public Vector2f getDimensions() {
		return dimensions;
	}

	public void setDimensions(Vector2f dimensions) {
		this.dimensions = dimensions;
	}

	public void setDimensions(int width, int height) {
		this.dimensions = new Vector2f(width, height);
	}

	public void setWidth(int width) {
		this.dimensions = new Vector2f(width, getHeight());
	}

	public void setHeight(int height) {
		this.dimensions = new Vector2f(getWidth(), height);
	}

	public int getWidth() {
		return (int) dimensions.x;
	}

	public int getHeight() {
		return (int) dimensions.y;
	}
}
