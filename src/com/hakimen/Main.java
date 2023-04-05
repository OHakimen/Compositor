package com.hakimen;

import com.hakimen.engine.core.io.Keyboard;
import com.hakimen.engine.core.io.Mouse;

import com.hakimen.engine.core.render.camera.CameraStack;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.containers.modifierNodes.AlphaMaskingNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.modifierNodes.ScalingNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.utilityNodes.ImageNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.utilityNodes.ValueNodeContainer;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import java.awt.*;

public class Main implements Runnable{
    public static void main(String[] args) {
        Window.init("Compositor",1280,720,1280,720,true,false);
        new Thread(new Main()).start();
    }

    @Override
    public void run() {
        try {
            Window.loop(
                    this::init,
                    this::destroy,
                    this::tick,
                    this::update,
                    this::render,
                    this::shader
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    NodeEditor nodeEditor = new NodeEditor();
    private void init(){
    }

    private void destroy(){

    }
    private void tick(){
        Keyboard.tick();
        Mouse.tick();
    }

    private void update(){
        Keyboard.update();
        Mouse.update();
    }


    private void render(){
        CameraStack.pushCamera();
        ViewTransformer.update();
        RenderUtils.SetRenderHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        nodeEditor.render();
        nodeEditor.update();
        CameraStack.popCamera();
    }
    private void shader(int[] buffer){

    }
}