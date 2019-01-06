package resources;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

class KeyboardListener implements KeyListener, FocusListener {
    private HashSet<Integer> keys;

    public KeyboardListener() {
        keys = new HashSet<>();
    }


    public boolean isKeyPressed(int key) {
        return keys.contains(key);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void focusGained(FocusEvent e) {
    }


    @Override
    public void focusLost(FocusEvent e) {
        reset();
    }

    private void reset() {
        keys.clear();
    }
}
