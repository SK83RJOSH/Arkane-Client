package com.sk83rsplace.arkane.GUI;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
	public static Image defaultImage = deriveImage("res/buttons/button.png");
	public static Image hortArrowLeft = deriveImage("res/buttons/hort-arrow-left.png");
	public static Image hortArrowRight= deriveImage("res/buttons/hort-arrow-right.png");
	public static Image vertArrow = deriveImage("res/buttons/vert-arrow.png");
	public static Image hide = deriveImage("res/buttons/hide.png");
	public static Image show = deriveImage("res/buttons/show.png");
	public static Image tileSelector = deriveImage("res/buttons/tile-selection.png");
	public static Image miniButton = deriveImage("res/buttons/button-mini.png");
	public static Image expandDown = deriveImage("res/buttons/expand-down.png");
	public static Image expandUp= deriveImage("res/buttons/expand-up.png");
	public static Image smallPanel = deriveImage("res/images/panel.png");
	public static Image largePanel = deriveImage("res/images/panel-large.png");
	public static Image splashScreen = deriveImage("res/images/splash.png");
	public static Image colorOverlay = deriveImage("res/images/color-overlay.png");
	public static Image colorHue = deriveImage("res/images/color-hue-picker.png");
	public static Image colorHandle= deriveImage("res/images/color-handle.png");
	public static Image cursorHue = deriveImage("res/images/hue-cursor.png");
	public static Image cursorPicker= deriveImage("res/images/picker-cursor.png");
	
	private static Image deriveImage(String res) {
		try {
			return new Image(res);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		return null;
	}
}
