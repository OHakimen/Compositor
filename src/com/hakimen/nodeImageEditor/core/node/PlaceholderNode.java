package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

import java.util.UUID;

public class PlaceholderNode extends Node<Object> {
    public PlaceholderNode(UUID container, boolean isReader, Object value) {
        super(container, isReader, value);
    }

    public PlaceholderNode(UUID container, boolean isReader) {
        super(container, isReader);
    }
}
