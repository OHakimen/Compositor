package com.hakimen.nodeImageEditor.core.node.utility;

import com.hakimen.nodeImageEditor.core.Node;

import java.awt.*;
import java.util.UUID;

public class ColorArrayNode extends Node<Color[]> {

    public ColorArrayNode(UUID container, boolean isReader, Color[] value) {
        super(container, isReader, value);
    }

    public ColorArrayNode(UUID container, boolean isReader) {
        super(container, isReader);
        setValue(new Color[0]);
    }
}
