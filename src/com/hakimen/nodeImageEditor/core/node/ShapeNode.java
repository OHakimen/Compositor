package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.UUID;

public class ShapeNode extends Node<Shape> {
    public ShapeNode(UUID container, boolean isReader, Shape value) {
        super(container, isReader, value);
    }

    public ShapeNode(UUID container, boolean isReader) {
        super(container, isReader);
        setValue(new Rectangle2D.Float(1,1,1,1));
    }
}
