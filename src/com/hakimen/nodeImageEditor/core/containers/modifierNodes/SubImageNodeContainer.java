package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class SubImageNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String WIDTH = "Width";
    static final String HEIGHT = "Height";
    static final String X = "X";
    static final String Y = "Y";
    static final String OUTPUT = "Output Image";

    public SubImageNodeContainer(float x, float y) {
        super(x, y, "SubImage Node");
        readerNodes.put(IMAGE, new ImageNode(uuid,true));
        readerNodes.put(X, new NumberNode(uuid,true, 0));
        readerNodes.put(Y, new NumberNode(uuid,true, 0));
        readerNodes.put(WIDTH, new NumberNode(uuid,true, 1));
        readerNodes.put(HEIGHT, new NumberNode(uuid,true, 1));

        writerNodes.put(OUTPUT, new ImageNode(uuid,false));
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

    BufferedImage lastImage;
    @Override
    public void update() {
        super.update();
        if(readerNodes.get(IMAGE) instanceof ImageNode img &&
            readerNodes.get(X) instanceof NumberNode x &&
            readerNodes.get(Y) instanceof NumberNode y &&
            readerNodes.get(WIDTH) instanceof NumberNode width &&
            readerNodes.get(HEIGHT) instanceof NumberNode height){
            if(writerNodes.get(OUTPUT) instanceof ImageNode out){
                if(Window.ticks % 20 == 0 && x.getValue() != null && y.getValue() != null && width.getValue() != null && height.getValue() != null){
                    var temp = img.getValue().getSubimage(x.getValue().intValue(),y.getValue().intValue(),1 | width.getValue().intValue(), 1 | height.getValue().intValue());
                    if(lastImage != temp){
                        out.setValue(img.getValue().getSubimage(x.getValue().intValue(),y.getValue().intValue(),1 | width.getValue().intValue(), 1 | height.getValue().intValue()));
                    }
                }
            }
        }
    }
}
