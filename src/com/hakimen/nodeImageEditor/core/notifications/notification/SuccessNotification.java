package com.hakimen.nodeImageEditor.core.notifications.notification;


import java.awt.*;
import java.awt.image.BufferedImage;

public class SuccessNotification extends Notification{
    public SuccessNotification(String sub1, String sub2, int maxTimeAlive) {
        super("Success", sub1, sub2, maxTimeAlive);
        img = new BufferedImage(32,32,2);
        var g = img.createGraphics();
        g.setFont(g.getFont().deriveFont(Font.PLAIN,24));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.GREEN.darker());
        g.drawString("✓",6,25);
        g.dispose();
    }
}
