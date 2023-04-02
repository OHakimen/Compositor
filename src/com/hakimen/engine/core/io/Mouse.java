package com.hakimen.engine.core.io;

import com.hakimen.engine.core.utils.Window;

import java.awt.event.*;

public class Mouse {

    public static int mouseScrool = 0;
    public static int x, y;
    public static MouseButton[] mouseButtons = new MouseButton[7];

    static {
        for (int i = 0; i < mouseButtons.length; i++) {
            mouseButtons[i] = new MouseButton();
        }
    }

    public static MouseWheelListener getWheelListener() {
        return new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mouseScrool = e.getWheelRotation();
            }
        };
    }

    public static MouseMotionListener getMoveListener() {
        return new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = (int) ((e.getX() - (Window.width - Window.rectWidth) / 2) / Window.scalingFactor);
                y = (int) ((e.getY() - (Window.height - Window.rectHeight) / 2) / Window.scalingFactor);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                x = (int) ((e.getX() - (Window.width - Window.rectWidth) / 2) / Window.scalingFactor);
                y = (int) ((e.getY() - (Window.height - Window.rectHeight) / 2) / Window.scalingFactor);
            }
        };
    }

    public static MouseListener getListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseButtons[e.getButton()].down = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseButtons[e.getButton()].down = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public static void update() {
        for (MouseButton button : mouseButtons) {
            button.pressed = button.down && !button.last;
            button.last = button.down;
        }

    }

    public static void tick() {
        for (MouseButton button : mouseButtons) {
            button.pressedTick = button.down && !button.lastTick;
            button.lastTick = button.down;
        }

    }
}
