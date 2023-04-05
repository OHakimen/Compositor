package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.ShapeNode;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ShapeMaskingNodeContainer extends NodeContainer {

    static final String SHAPE = "Shape";
    static final String IMAGE = "Image";
    static final String OUTPUT = "Output Image";
    public ShapeMaskingNodeContainer(float x, float y) {
        super(x, y, "Shape Masking Node");

        readerNodes.put(SHAPE,new ShapeNode(this,true, new Rectangle2D.Float(0,0,0,0)));
        readerNodes.put(IMAGE,new ImageNode(this,true, new BufferedImage(1,1,2)));

        writerNodes.put(OUTPUT,new ImageNode(this,false));
    }

    @Override
    public void render() {
        super.render();
        if(writerNodes.get(OUTPUT) instanceof ImageNode node){
            RenderUtils.ClipShape(new RoundRectangle2D.Float(x,y+sy,sx,sx, 16f,16f));
            RenderUtils.DrawImage(x,y+sy,sx,sx,node.getValue());
            RenderUtils.ClipShape(null);
        }
    }

    @Override
    public void update() {
        super.update();
        if(readerNodes.get(SHAPE) instanceof ShapeNode shape &&
            readerNodes.get(IMAGE) instanceof ImageNode image &&
            writerNodes.get(OUTPUT) instanceof ImageNode out){
            if(image.getValue() != null) {
                var tempImage = new BufferedImage(image.getValue().getWidth() | 1, image.getValue().getHeight() | 1, 2);
                var g = tempImage.createGraphics();
                g.setClip(shape.getValue());
                g.drawImage(image.getValue(), 0, 0, null);
                g.setClip(null);
                g.dispose();
                out.setValue(tempImage);
            }
        }
    }
}
