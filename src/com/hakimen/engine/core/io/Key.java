package com.hakimen.engine.core.io;

import java.awt.event.KeyEvent;

public class Key {
    public boolean down, pressed, pressedTick, last, lastTick;

    public static int fromChar(char key) {
        return KeyEvent.getExtendedKeyCodeForChar(key);
    }
}
