package com.hakimen;

import com.hakimen.engine.core.io.Keyboard;
import com.hakimen.engine.core.io.Mouse;

import com.hakimen.engine.core.utils.Window;

public class Main implements Runnable{
    public static void main(String[] args) {
        Window.init("Game",800,600,800,600,true,false);
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

    }
    private void shader(int[] buffer){

    }
}