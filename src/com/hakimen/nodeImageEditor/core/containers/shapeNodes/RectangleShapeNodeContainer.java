package com.hakimen.nodeImageEditor.core.containers.shapeNodes;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.node.ShapeNode;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class RectangleShapeNodeContainer extends NodeContainer {

    static final String X = "X";
    static final String Y = "Y";
    static final String WIDTH = "Width";
    static final String HEIGHT = "Height";
    static final String OUTPUT = "Shape";

    public RectangleShapeNodeContainer(float x, float y) {
        super(x, y, "Rectangle Shape Node");

        readerNodes.put(X, new NumberNode(uuid, true, 0));
        readerNodes.put(Y, new NumberNode(uuid, true, 0));
        readerNodes.put(WIDTH, new NumberNode(uuid, true, 0));
        readerNodes.put(HEIGHT, new NumberNode(uuid, true, 0));

        writerNodes.put(OUTPUT, new ShapeNode(uuid, false, new Rectangle2D.Float(0, 0, 0, 0)));
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
                writerNodes.get(OUTPUT) instanceof ShapeNode out) {
            out.setValue(new Rectangle2D.Float(x.getValue().intValue(), y.getValue().intValue(), width.getValue().intValue(), height.getValue().intValue()));
        }
    }
}
