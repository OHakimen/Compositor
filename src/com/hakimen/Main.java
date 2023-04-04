package com.hakimen;

import com.hakimen.engine.core.io.Keyboard;
import com.hakimen.engine.core.io.Mouse;

import com.hakimen.engine.core.render.camera.CameraStack;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.containers.mathNodes.*;
import com.hakimen.nodeImageEditor.core.containers.modifiers.LayeringNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.modifiers.ScalingNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.utilityNodes.ClockNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.ColorNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.ImageNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.ValueNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.converters.ColorToRGBContainer;
import com.hakimen.nodeImageEditor.core.containers.converters.RGBtoColorContainer;
import com.hakimen.nodeImageEditor.core.containers.modifiers.TintNodeContainer;
import com.hakimen.nodeImageEditor.utils.Pair;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;
import com.sun.jdi.Value;

import java.awt.*;
import java.time.Clock;

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
        nodeEditor.containers.add(new MultiplyNodeContainer(300,  200));
        nodeEditor.containers.add(new AbsoluteValueNode(300*2,  200*3));
        nodeEditor.containers.add(new ValueNodeContainer(300*2,  200));

        nodeEditor.containers.add(new ClockNodeContainer(300,  200*2));
        nodeEditor.containers.add(new SineNodeContainer(300*2,200*2));

        nodeEditor.containers.add(new RGBtoColorContainer(300*3,  200));
        nodeEditor.containers.add(new TintNodeContainer(300*3,  200*2));

        nodeEditor.containers.add(new ImageNodeContainer(300*3,  200*3));

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