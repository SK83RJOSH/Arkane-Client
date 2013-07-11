package com.sk83rsplace.arkane.client.interfaces;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public interface IWorld extends IRenderable {
	public Vector3f getPos();
	public void setPos(Vector3f position);
	public void setPos(int x, int y, int z);
	public void setX(int x);
	public int getX();
	public void setY(int y);
	public int getY();
	public void setZ(int z);
	public int getZ();
	public Vector2f getDimensions();
	public void setDimensions(Vector2f dimensions);
	public void setDimensions(int width, int height);
	public void setWidth(int width);
	public void setHeight(int height);
	public int getWidth();
	public int getHeight();
}
