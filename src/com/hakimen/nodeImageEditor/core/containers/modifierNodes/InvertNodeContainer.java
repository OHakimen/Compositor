package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class InvertNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String OUTPUT = "Output Image";
    public InvertNodeContainer(float x, float y) {
        super(x, y, "Invert Node");
        readerNodes.put(IMAGE,new ImageNode(this,true, new BufferedImage(1,1,2)));
        writerNodes.put(OUTPUT,new ImageNode(this,true, new BufferedImage(1,1,2)));
    }

    @Override
    public void render() {
        super.render();
        if (writerNodes.get(OUTPUT) instanceof ImageNode node) {
            if(node.getValue() != null) {
                RenderUtils.ClipShape(new RoundRectangle2D.Float(x,y+sy,sx,sx, 16f,16f));
                RenderUtils.DrawImage(x,y+sy,sx,sx,node.getValue());
                RenderUtils.ClipShape(null);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (readerNodes.get(IMAGE) instanceof ImageNode node &&
            writerNodes.get(OUTPUT) instanceof ImageNode out) {
            if(node.getValue() != null && Window.ticks % 20 == 0) {
                var buff = new BufferedImage(node.getValue().getWidth(),node.getValue().getHeight(),2);
                for (int x = 0; x < node.getValue().getWidth(); x++) {
                    for (int y = 0; y < node.getValue().getHeight(); y++) {
                        int rgba = node.getValue().getRGB(x, y);
                        Color col = new Color(rgba, true);
                        col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                        buff.setRGB(x, y, col.getRGB());
                    }
                }
                out.setValue(buff);
            }
        }
    }
}
