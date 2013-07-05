package com.sk83rsplace.arkane.GUI.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import com.sk83rsplace.arkane.GUI.Component;
import com.sk83rsplace.arkane.GUI.IValuedBoolean;
import com.sk83rsplace.arkane.client.Board;

/**
 * 
 * @author SK83RJOSH
 */
public class CheckBoxComponent extends Component implements IValuedBoolean {
	private boolean isChecked;
	private boolean isRadial;
	
	public CheckBoxComponent(boolean isChecked, boolean isRadial, int x, int y) {
		this.isChecked = isChecked;
		this.isRadial = isRadial;
		set(x, y);
		setSize(22, 22);
	}
	
	public void render(GameContainer container, Graphics g) {
		g.setFont(container.getDefaultFont());
		
		g.setColor((isActive() ? new Color(0f, 0f, 0f, 0.70f) : new Color(0f, 0f, 0f, 0.65f)));
		if(isRadial)
			g.fill(new Circle(getX() + (getWidth() / 2), getY()  + (getHeight() / 2), getWidth() / 2, getHeight() / 2));
		else
			g.fill(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		
		g.setColor(Color.white);

		if(isRadial)
			g.draw(new Circle(getX() + (getWidth() / 2), getY() + (getHeight() / 2), getWidth() / 2, getHeight() / 2));
		else
			g.draw(new Rectangle(getX() - 1, getY(), getWidth() + 1, getHeight() + 1));
				
		if(isChecked)
			if(isRadial)
				g.fill(new Circle(getX() + (getWidth() / 2), getY() + (getHeight() / 2), ((getWidth() - 10) / 2), ((getHeight() - 10) / 2)));
			else
				g.drawString("X", getX() + 6, getY() + 6);
	}
		
	public void update(GameContainer container) {
		super.update(container);
		
		if(isActive() && Board.mouseButtons.wasReleased(0)) {
			isChecked = !isChecked;
			onValueChange();
		}
	}
	
	public boolean getValue() {
		return isChecked;
	}
	
	public void setValue(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public void onValueChange() {
		//Override
	}
}
