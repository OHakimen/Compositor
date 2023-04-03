package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

import java.awt.image.BufferedImage;

public class ImageNode extends Node<BufferedImage> {
    public ImageNode(NodeContainer container, boolean isReader, BufferedImage value) {
        super(container, isReader, value);
    }

    public ImageNode(NodeContainer container, boolean isReader) {
        super(container, isReader);
    }
}
