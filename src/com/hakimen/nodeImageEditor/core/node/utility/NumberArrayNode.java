package com.hakimen.nodeImageEditor.core.node.utility;

import com.hakimen.nodeImageEditor.core.Node;

import java.awt.*;
import java.util.UUID;

public class NumberArrayNode extends Node<Number[]> {
    public NumberArrayNode(UUID container, boolean isReader, Number[] value) {
        super(container, isReader, value);
    }

    public NumberArrayNode(UUID container, boolean isReader) {
        super(container, isReader);
        setValue(new Number[0]);
    }
}
