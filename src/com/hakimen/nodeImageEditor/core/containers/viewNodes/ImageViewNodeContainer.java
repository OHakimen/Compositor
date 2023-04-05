package com.hakimen.nodeImageEditor.core.containers.viewNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ImageViewNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    public ImageViewNodeContainer(float x, float y) {
        super(x, y, "Image Node View");
        readerNodes.put(IMAGE,new ImageNode(this,true, new BufferedImage(1,1,2)));
    }

    @Override
    public void render() {
        super.render();
        if (readerNodes.get(IMAGE) instanceof ImageNode node) {
            if(node.getValue() != null) {
                RenderUtils.ClipShape(new RoundRectangle2D.Float(x, y + sy, node.getValue().getWidth(), node.getValue().getHeight(), 16f, 16f));
                RenderUtils.DrawImage(x, y + sy, node.getValue());
                RenderUtils.ClipShape(null);
            }
        }
    }

}
