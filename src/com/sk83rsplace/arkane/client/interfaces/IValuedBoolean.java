package com.sk83rsplace.arkane.client.interfaces;

/**
 * 
 * @author SK83RJOSH
 */

//TODO: This is a specific case, I'd much rather use Object instead of a finite return for getValue, but strangely enough there's a problem.
public interface IValuedBoolean {
	public void onValueChange();
	public void setValue(boolean content);
	public boolean getValue();
}
