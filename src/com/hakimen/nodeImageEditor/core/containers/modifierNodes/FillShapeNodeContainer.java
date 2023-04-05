package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.ShapeNode;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class FillShapeNodeContainer extends NodeContainer {

    static final String OUTPUT = "Output Image";
    static final String SHAPE = "Shape";
    static final String COLOR = "Color";
    public FillShapeNodeContainer(float x, float y) {
        super(x, y, "Fill Shape Node");
        readerNodes.put(SHAPE,new ShapeNode(this,true));
        readerNodes.put(COLOR,new ColorNode(this,true, Color.WHITE));

        writerNodes.put(OUTPUT, new ImageNode(this, false));
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
            readerNodes.get(COLOR) instanceof ColorNode colorNode &&
            writerNodes.get(OUTPUT) instanceof ImageNode out){
            if(shape.getValue() != null && colorNode.getValue() != null){
                var buff = new BufferedImage(1 | (int)shape.getValue().getBounds2D().getWidth(),1 | (int)shape.getValue().getBounds2D().getHeight(),2);
                var g = buff.createGraphics();
                g.setColor(colorNode.getValue());
                g.fill(shape.getValue());
                g.dispose();
                out.setValue(buff);
            }
        }
    }
}
