package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

public class NumberNode extends Node<Number> {
    public NumberNode(NodeContainer container, boolean isReader, Number value) {
        super(container, isReader, value);
    }

    public NumberNode(NodeContainer container, boolean isReader) {
        super(container, isReader);
    }
}
