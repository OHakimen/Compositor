package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

import java.awt.*;

public class ColorNode extends Node<Color> {
    public ColorNode(NodeContainer container, boolean isReader, Color value) {
        super(container, isReader, value);
    }

    public ColorNode(NodeContainer container, boolean isReader) {
        super(container, isReader);
    }
}
