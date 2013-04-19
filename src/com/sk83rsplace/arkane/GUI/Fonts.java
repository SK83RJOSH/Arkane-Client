package com.sk83rsplace.arkane.GUI;

import org.newdawn.slick.TrueTypeFont;

import com.sk83rsplace.arkane.client.Board;

public class Fonts {
	public static TrueTypeFont normalPoint = deriveFont(16f);
	public static TrueTypeFont mediumPoint = deriveFont(22f);
	public static TrueTypeFont largePoint = deriveFont(32f);
	
	private static TrueTypeFont deriveFont(float fontSize) {
		return new TrueTypeFont(Board.font.deriveFont(fontSize), false);
	}
}
