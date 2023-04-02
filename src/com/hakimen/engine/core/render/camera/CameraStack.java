package com.hakimen.engine.core.render.camera;

import com.hakimen.engine.core.utils.RenderUtils;

import java.util.Stack;

public class CameraStack {
    static Stack<Camera> cameras = new Stack<>();
    public static Camera current = new Camera();

    public static void popCamera(){
        RenderUtils.Translate(current.cx,current.cy);
        current = cameras.pop();
    }

    public static void pushCamera(){
        cameras.push(current);
        RenderUtils.Translate(-current.cx,-current.cy);
        current = new Camera();
    }
}
