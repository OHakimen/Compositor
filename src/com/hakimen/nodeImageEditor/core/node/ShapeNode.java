package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ShapeNode extends Node<Shape> {
    public ShapeNode(NodeContainer container, boolean isReader, Shape value) {
        super(container, isReader, value);
        setNodeColor(Color.MAGENTA.darker());
    }

    public ShapeNode(NodeContainer container, boolean isReader) {
        super(container, isReader);
        setValue(new Rectangle2D.Float(1,1,1,1));
        setNodeColor(Color.MAGENTA.darker());
    }
}
