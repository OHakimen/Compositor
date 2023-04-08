package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class GrayScaleNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String OUTPUT = "Output Image";
    public GrayScaleNodeContainer(float x, float y) {
        super(x, y, "Grayscale Node");
        readerNodes.put(IMAGE,new ImageNode(uuid,true, new BufferedImage(1,1,2)));
        writerNodes.put(OUTPUT,new ImageNode(uuid,false, new BufferedImage(1,1,2)));
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
                        Color color = new Color(node.getValue().getRGB(x, y));
                        float[] af = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

                        float h = af[0];
                        float l = af[2];

                        float saturationNew = 0;

                        buff.setRGB(x, y, Color.getHSBColor(h, saturationNew, l).getRGB());
                    }
                }
                out.setValue(buff);
            }
        }
    }
}
