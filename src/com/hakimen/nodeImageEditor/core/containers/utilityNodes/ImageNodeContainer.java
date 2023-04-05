package com.hakimen.nodeImageEditor.core.containers.utilityNodes;

import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.utils.Collisions;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

public class ImageNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String WIDTH = "Width";
    static final String HEIGHT = "Height";
    JFileChooser chooser = new JFileChooser("/");
    public ImageNodeContainer(float x, float y) {
        super(x, y, "Image Node");
        this.sx = name.length() * 8 + (4*32);
        writerNodes.put(IMAGE, new ImageNode(this,false));
        writerNodes.put(WIDTH, new NumberNode(this,false,0));
        writerNodes.put(HEIGHT, new NumberNode(this,false,0));
    }



    @Override
    public void update() {
        if(Mouse.mouseButtons[MouseEvent.BUTTON1].pressed){
            if(Collisions.pointToRect(ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY,x+8,y + 48,64,32)){
                if(writerNodes.get(IMAGE) instanceof ImageNode node &&
                        writerNodes.get(WIDTH) instanceof NumberNode width &&
                        writerNodes.get(HEIGHT) instanceof NumberNode height){
                    chooser.showOpenDialog(null);
                    try {
                        node.setValue(ImageIO.read(chooser.getSelectedFile()));
                        width.setValue(node.getValue().getWidth());
                        height.setValue(node.getValue().getHeight());
                    } catch (Exception ignored) {

                    }
                }
            }
        }
        super.update();
    }

    @Override
    public void render() {
        super.render();

        RenderUtils.FillRoundedRect(x+8,y + 48,64,32,16,16, Color.DARK_GRAY.darker());
        RenderUtils.DrawString((int)x+6+12,(int)y+42+16,Color.WHITE,"Change");

        if(writerNodes.get(IMAGE) instanceof ImageNode node){
            RenderUtils.ClipShape(new RoundRectangle2D.Float(x,y+sy,sx,sx, 16f,16f));
            RenderUtils.DrawImage(x,y+sy,sx,sx,node.getValue());
            RenderUtils.ClipShape(null);
        }
    }
}
