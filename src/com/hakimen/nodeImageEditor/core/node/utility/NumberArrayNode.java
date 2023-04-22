package com.hakimen.nodeImageEditor.core.node.utility;

import com.hakimen.nodeImageEditor.core.Node;

import java.awt.*;
import java.util.UUID;

public class NumberArrayNode extends Node<Number[]> {
    public NumberArrayNode(UUID container, boolean isReader, Number[] value) {
        super(container, isReader, value);
        setNodeColor(new Color(0xff005d));
    }

    public NumberArrayNode(UUID container, boolean isReader) {
        super(container, isReader);
        setValue(new Number[0]);
        setNodeColor(new Color(0xff005d));
    }
}
