package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

public class ImageNode extends Node<BufferedImage> {
    public ImageNode(UUID container, boolean isReader, BufferedImage value) {
        super(container, isReader, value);
    }

    public ImageNode(UUID container, boolean isReader) {
        super(container, isReader);
        setValue(new BufferedImage(1,1,2));
    }
}
