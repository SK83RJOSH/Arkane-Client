package com.sk83rsplace.arkane.client;

/**
 * 
 * @author SK83RJOSH
 */
public class MouseButtons {
    private boolean[] currentState = new boolean[4];
    private boolean[] nextState = new boolean[4];
    
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

    public void update() {
        for (int i = 0; i < currentState.length; i++) {
            currentState[i] = nextState[i];
        }
    }

    public void releaseAll() {
        for (int i = 0; i < nextState.length; i++) {
            nextState[i] = false;
        }
    }
}
