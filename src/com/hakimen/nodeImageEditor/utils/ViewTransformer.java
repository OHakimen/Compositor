package com.hakimen.nodeImageEditor.utils;

import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;

import java.awt.event.MouseEvent;

public class ViewTransformer {

    public static int lastX,lastY, x, y;
    public static int transformedMouseX, transformedMouseY;
    public static int diffX,diffY;
    static boolean mouseDrag;
    public static void update(){
        diffX = (x-lastX);
        diffY = (y-lastY);
        if(Mouse.mouseButtons[MouseEvent.BUTTON3].down && !mouseDrag){
            lastX = Mouse.x - diffX;
            lastY = Mouse.y - diffY;
            mouseDrag = true;
        }
        if(mouseDrag){
            x = Mouse.x;
            y = Mouse.y;
        }
        if(!Mouse.mouseButtons[MouseEvent.BUTTON3].down && mouseDrag){
            mouseDrag = false;
        };
        transformedMouseX = Mouse.x - diffX;
        transformedMouseY = Mouse.y - diffY;
        RenderUtils.Translate( x - lastX , y - lastY);
    }
}
