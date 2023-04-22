package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

import java.awt.*;
import java.util.UUID;

public class NumberNode extends Node<Number> {
    public NumberNode(UUID container, boolean isReader, Number value) {
        super(container, isReader, value);
    }

    public NumberNode(UUID container, boolean isReader) {
        super(container, isReader);
        setValue(0f);
    }
}
