package com.hakimen.engine.core.io;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard extends KeyAdapter {

    public static Key[] keys = new Key[1024];

    static {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = new Key();
        }
    }
    public static char lastTyped = Character.MIN_VALUE;

    public static KeyListener getListener() {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                lastTyped = e.getKeyChar();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Keyboard.keys[e.getKeyCode()].down = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Keyboard.keys[e.getKeyCode()].down = false;
            }
        };
    }
    public static void update() {
        for (Key key : keys) {
            key.pressed = key.down && !key.last;
            key.last = key.down;
        }
    }
    public static void tick() {

        for (Key key : keys) {
            key.pressedTick = key.down && !key.lastTick;
            key.lastTick = key.down;
        }

    }

}
