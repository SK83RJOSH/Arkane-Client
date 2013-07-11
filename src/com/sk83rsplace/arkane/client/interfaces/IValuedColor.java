package com.sk83rsplace.arkane.client.interfaces;

import org.newdawn.slick.Color;

/**
 * 
 * @author SK83RJOSH
 */

//TODO: This is a specific case, I'd much rather use Object instead of a finite return for getValue, but strangely enough there's a problem.
public interface IValuedColor {
	public void onValueChange();
	public void setValue(Color content);
	public Color getValue();
}
