package com.sk83rsplace.arkane.client;

import org.lwjgl.input.Mouse;

/**
 * 
 * @author SK83RJOSH
 */
public class MouseButtons {
    private boolean[] currentState;
    private boolean[] nextState;
    private int mousewheel;
    
    public MouseButtons() {
    	currentState = new boolean[Mouse.getButtonCount()];
    	nextState = new boolean[Mouse.getButtonCount()];
    	mousewheel = Mouse.getDWheel();
    }
    
    public void setNextState(int button, boolean value) {
        nextState[button] = value;
    }

    public boolean isDown(int button) {
        return currentState[button];
    }

    public boolean wasPressed(int button) {
        return !currentState[button] && nextState[button];
    }

    public boolean wasReleased(int button) {
        return currentState[button] && !nextState[button];
    }
    
    public int getDWheel() {
    	return mousewheel;
    }

    public void update() {
        for (int i = 0; i < currentState.length; i++) {
            currentState[i] = nextState[i];
        }
        
        mousewheel = Mouse.getDWheel();
    }

    public void releaseAll() {
        for (int i = 0; i < nextState.length; i++) {
            nextState[i] = false;
        }
        
        mousewheel = 0;
    }
}
