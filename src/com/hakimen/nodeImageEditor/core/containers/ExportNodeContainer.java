package com.hakimen.nodeImageEditor.core.containers;

import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.notifications.notification.Notification;
import com.hakimen.nodeImageEditor.utils.Collisions;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ExportNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";

    JFileChooser chooser = new JFileChooser("/");
    public ExportNodeContainer(float x, float y) {
        super(x, y, "Export Node");
        readerNodes.put(IMAGE,new ImageNode(uuid,true, new BufferedImage(1,1,2)));
    }

    @Override
    public void render() {
        super.render();
        RenderUtils.FillRoundedRect(x+sx - 8 - 64,y + 48,64,32,16,16, Color.DARK_GRAY.darker());
        RenderUtils.DrawString((int)(x+sx - 6 - 64+12),(int)y+42+16,Color.WHITE,"Export");
    }

    @Override
    public void update() {
        super.update();
        if(Mouse.mouseButtons[MouseEvent.BUTTON1].pressed){
            if(readerNodes.get(IMAGE) instanceof ImageNode node && Collisions.pointToRect(ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY,x+sx - 8 - 64,y + 48,64,32)){
                if(node.getValue() != null){
                    chooser.showSaveDialog(null);
                    try {
                        if(chooser.getSelectedFile() != null) {
                            ImageIO.write(node.getValue(), "PNG", chooser.getSelectedFile());
                            NodeEditor.handler.push(new Notification("Export", "Saved image as", chooser.getSelectedFile().getName(),NodeEditor.NOTIFY_NORMAL).setImg(node.getValue()));
                        }
                    } catch (Exception ignored) {

                    }
                }
            }
        }
    }
}
