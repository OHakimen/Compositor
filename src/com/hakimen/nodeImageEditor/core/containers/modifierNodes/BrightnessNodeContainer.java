package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class BrightnessNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String BRIGHTNESS = "Brightness";
    static final String OUTPUT = "Output Image";
    public BrightnessNodeContainer(float x, float y) {
        super(x, y, "Brightness Node");
        readerNodes.put(IMAGE,new ImageNode(this,true, new BufferedImage(1,1,2)));
        readerNodes.put(BRIGHTNESS,new NumberNode(this,true, 0));
        writerNodes.put(OUTPUT,new ImageNode(this,false, new BufferedImage(1,1,2)));
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
            readerNodes.get(BRIGHTNESS) instanceof NumberNode bright &&
            writerNodes.get(OUTPUT) instanceof ImageNode out) {
            if(node.getValue() != null && Window.ticks % 20 == 0) {
                var buff = new BufferedImage(node.getValue().getWidth(),node.getValue().getHeight(),2);
                RescaleOp op = new RescaleOp(bright.getValue().floatValue(), 0, null);
                buff = op.filter(node.getValue(),buff);
                out.setValue(buff);
            }
        }
    }
}
