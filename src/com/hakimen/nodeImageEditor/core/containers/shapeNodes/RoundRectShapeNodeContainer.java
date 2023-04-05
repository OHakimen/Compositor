package com.hakimen.nodeImageEditor.core.containers.shapeNodes;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.node.ShapeNode;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class RoundRectShapeNodeContainer extends NodeContainer {

    static final String X = "X";
    static final String Y = "Y";
    static final String WIDTH = "Width";
    static final String HEIGHT = "Height";
    static final String ARC_WIDTH = "Arc Width";
    static final String ARC_HEIGHT = "Arc Height";
    static final String OUTPUT = "Shape";

    public RoundRectShapeNodeContainer(float x, float y) {
        super(x, y, "Round Rectangle Shape Node");

        readerNodes.put(X, new NumberNode(this, true, 0));
        readerNodes.put(Y, new NumberNode(this, true, 0));
        readerNodes.put(WIDTH, new NumberNode(this, true, 0));
        readerNodes.put(HEIGHT, new NumberNode(this, true, 0));
        readerNodes.put(ARC_WIDTH, new NumberNode(this, true, 0));
        readerNodes.put(ARC_HEIGHT, new NumberNode(this, true, 0));

        writerNodes.put(OUTPUT, new ShapeNode(this, false, new RoundRectangle2D.Float(0, 0, 0, 0,0,0)));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void update() {
        super.update();
        if (readerNodes.get(X) instanceof NumberNode x &&
                readerNodes.get(Y) instanceof NumberNode y &&
                readerNodes.get(WIDTH) instanceof NumberNode width &&
                readerNodes.get(HEIGHT) instanceof NumberNode height &&
                readerNodes.get(ARC_WIDTH) instanceof NumberNode arcW &&
                readerNodes.get(ARC_HEIGHT) instanceof NumberNode arcH &&
                writerNodes.get(OUTPUT) instanceof ShapeNode out) {
            out.setValue(new RoundRectangle2D.Float(x.getValue().intValue(), y.getValue().intValue(), width.getValue().intValue(), height.getValue().intValue(),arcW.getValue().intValue(),arcH.getValue().intValue()));
        }
    }
}
