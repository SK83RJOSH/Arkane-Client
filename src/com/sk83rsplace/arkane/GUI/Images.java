package com.sk83rsplace.arkane.GUI;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
	public static Image defaultImage = deriveImage("res/buttons/button.png");
	public static Image hortArrowLeft = deriveImage("res/buttons/hort-arrow-left.png");
	public static Image hortArrowRight= deriveImage("res/buttons/hort-arrow-right.png"); //Can't Flip Horizontally without fucking some things up.
	public static Image vertArrow = deriveImage("res/buttons/vert-arrow.png");
	public static Image hide = deriveImage("res/buttons/hide.png");
	public static Image show = deriveImage("res/buttons/show.png");
	public static Image tileSelector = deriveImage("res/buttons/tile-selection.png");
	public static Image miniButton = deriveImage("res/buttons/button-mini.png");
	
	private static Image deriveImage(String res) {
		try {
			return new Image(res);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		return null;
	}
}
