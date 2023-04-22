package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;

import java.awt.*;
import java.util.UUID;

public class StringNode extends Node<String> {
    public StringNode(UUID container, boolean isReader, String value) {
        super(container, isReader, value);
    }

    public StringNode(UUID container, boolean isReader) {
        super(container, isReader,"");
    }
}
