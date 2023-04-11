package com.hakimen.nodeImageEditor.core.notifications.notification;

import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.utils.Collisions;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class Notification {

    public String title;
    public String[] subtitles;
    public int timeAlive = 0;
    public int maxTimeAlive;

    public int x,y;
    public BufferedImage img = new BufferedImage(1,1,2);

    public Notification(String title, String sub1, String sub2, int maxTimeAlive) {
        this.title = title;
        this.subtitles = new String[]{sub1,sub2};
        this.maxTimeAlive = maxTimeAlive;
    }

    public BufferedImage getImg() {
        return img;
    }

    public Notification setImg(BufferedImage img) {
        this.img = img;
        return this;
    }

    public void render(int x, int y){
        this.x = x;
        this.y = y;
        if(title.length() > 20){
            title = title.substring(0,20) + "...";
        }

        RenderUtils.FillRoundedRect(x,y,256,72,8,8, Color.DARK_GRAY);
        Font f = RenderUtils.g.getFont();
        RenderUtils.g.setFont(f.deriveFont(Font.BOLD,16));
        RenderUtils.DrawString(x+72,y+12,Color.WHITE,title);
        RenderUtils.g.setFont(f);
        RenderUtils.DrawString(x+72,y+32,Color.WHITE.darker(),subtitles[0]);
        RenderUtils.DrawString(x+72,y+32 + 16,Color.WHITE.darker().darker(),subtitles[1]);
        RenderUtils.DrawString(x + 238, y + 8,Color.WHITE,"⛌");

        RenderUtils.FillRoundedRect(x+8,y+8,56,56,8,8, Color.DARK_GRAY.darker());
        RenderUtils.ClipShape(new RoundRectangle2D.Float(x+8,y+8,56,56,8,8));
        RenderUtils.DrawImage(x+8,y+8,56,56,img);
        RenderUtils.ClipShape(null);
    }

    public void update(){
        if(Mouse.mouseButtons[MouseEvent.BUTTON1].pressed && Collisions.pointToRect(Mouse.x,Mouse.y,x+236,y+6,16,16)){
            timeAlive = maxTimeAlive - 10;
        }
        timeAlive++;
    }
}
