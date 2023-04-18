package com.hakimen.nodeImageEditor.core.containers.utilityNodes;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

public class ImageDataNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";

    static final String OUTPUT = "Image Out";
    static final String WIDTH = "Width";
    static final String HEIGHT = "Height";

    public ImageDataNodeContainer(float x, float y) {
        super(x, y, "Image Data Node");
        readerNodes.put(IMAGE, new ImageNode(uuid, true));
        writerNodes.put(OUTPUT, new ImageNode(uuid, false));
        writerNodes.put(WIDTH, new NumberNode(uuid, false));
        writerNodes.put(HEIGHT, new NumberNode(uuid, false));
    }

    @Override
    public void update() {
        super.update();
        if(readerNodes.get(IMAGE) instanceof ImageNode inNode &&
            writerNodes.get(OUTPUT) instanceof ImageNode outNode &&
            writerNodes.get(WIDTH) instanceof NumberNode widthNode &&
            writerNodes.get(HEIGHT) instanceof NumberNode heightNode){
            var img = inNode.getValue();
            outNode.setValue(img);
            widthNode.setValue(img.getWidth());
            heightNode.setValue(img.getHeight());
        }
    }
}
