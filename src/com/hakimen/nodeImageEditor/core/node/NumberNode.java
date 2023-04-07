package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

import java.awt.*;

public class NumberNode extends Node<Number> {
    public NumberNode(NodeContainer container, boolean isReader, Number value) {
        super(container, isReader, value);
        setNodeColor(Color.GREEN);
    }

    public NumberNode(NodeContainer container, boolean isReader) {
        super(container, isReader);
        setValue(0f);
        setNodeColor(Color.GREEN);
    }
}
