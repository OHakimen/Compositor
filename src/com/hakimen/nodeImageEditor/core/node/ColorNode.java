package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

import java.awt.*;
import java.util.UUID;

public class ColorNode extends Node<Color> {

    public ColorNode(UUID container, boolean isReader, Color value) {
        super(container, isReader, value);
        setNodeColor(Color.WHITE);
    }

    public ColorNode(UUID container, boolean isReader) {
        super(container, isReader);
        setValue(Color.WHITE);
        setNodeColor(Color.WHITE);
    }
}
