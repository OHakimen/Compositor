package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ScalingNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String WIDTH = "Width";
    static final String HEIGHT = "Height";
    static final String OUTPUT = "Output Image";
    public ScalingNodeContainer(float x, float y) {
        super(x, y, "Scaling Node");

        readerNodes.put(IMAGE, new ImageNode(this,true));
        readerNodes.put(WIDTH, new NumberNode(this,true, 0));
        readerNodes.put(HEIGHT, new NumberNode(this,true, 0));

        writerNodes.put(OUTPUT, new ImageNode(this,false));
    }

    @Override
    public void render() {
        super.render();
        if(writerNodes.get(OUTPUT) instanceof ImageNode node){
            RenderUtils.ClipShape(new RoundRectangle2D.Float(x,y+sy,sx,sx, 16f,16f));
            RenderUtils.DrawImage(x,y+sy,sx,sx,node.getValue());
            RenderUtils.ClipShape(null);
        }
    }

    @Override
    public void update() {
        super.update();
        if (writerNodes.get(OUTPUT) instanceof ImageNode out && readerNodes.get(IMAGE) instanceof ImageNode image && readerNodes.get(WIDTH) instanceof NumberNode width && readerNodes.get(HEIGHT) instanceof NumberNode height) {
            var buff = new BufferedImage(width.getValue().intValue() | 1,height.getValue().intValue() | 1,BufferedImage.TYPE_INT_ARGB);
            var g = buff.createGraphics();
            g.drawImage(image.getValue(),0,0,width.getValue().intValue(),height.getValue().intValue(),null);
            g.dispose();
            out.setValue(buff);
        }
    }
}
