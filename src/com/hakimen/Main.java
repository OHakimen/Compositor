package com.hakimen;

import com.hakimen.engine.core.io.Keyboard;
import com.hakimen.engine.core.io.Mouse;

import com.hakimen.engine.core.render.camera.CameraStack;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.containers.utilityNodes.ClockNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.ColorNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.ImageNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.ValueNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.converters.ColorToRGBContainer;
import com.hakimen.nodeImageEditor.core.containers.converters.RGBtoColorContainer;
import com.hakimen.nodeImageEditor.core.containers.modifiers.TintNodeContainer;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import java.awt.*;

public class Main implements Runnable{
    public static void main(String[] args) {
        Window.init("Game",1280,720,1280,720,true,false);
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

        nodeEditor.containers.add(new ColorToRGBContainer(200,  200));
        nodeEditor.containers.add(new RGBtoColorContainer(200*2,200));
        nodeEditor.containers.add(new ClockNodeContainer( 200*3,200));
        nodeEditor.containers.add(new ImageNodeContainer( 200*4,200));
        nodeEditor.containers.add(new ValueNodeContainer( 200*5,200));
        nodeEditor.containers.add(new ColorNodeContainer( 200*6,200));
        nodeEditor.containers.add(new TintNodeContainer(  200*7,200));
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