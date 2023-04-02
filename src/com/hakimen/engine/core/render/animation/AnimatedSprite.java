package com.hakimen.engine.core.render.animation;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;

import java.awt.image.BufferedImage;

public class AnimatedSprite {
    BufferedImage[] sprites;
    int updateFrame;
    int currentFrame;


    public AnimatedSprite(BufferedImage[] sprites, int updateFrame, int currentFrame) {
        this.sprites = sprites;
        this.updateFrame = updateFrame;
        this.currentFrame = currentFrame;
    }

    public void render(int x,int y,int width, int height){
        //Calculate Frame tick update
        if(Window.ticks % updateFrame == 0){
            currentFrame = (currentFrame + 1) % sprites.length;
        }

        RenderUtils.DrawImage(x,y,width,height,sprites[currentFrame]);
    }

    public void render(int x,int y){
        render(x,y,sprites[currentFrame].getWidth(),sprites[currentFrame].getHeight());
    }
}
