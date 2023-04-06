package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.geom.RoundRectangle2D;

public class SubImageNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String WIDTH = "Width";
    static final String HEIGHT = "Height";
    static final String X = "X";
    static final String Y = "Y";
    static final String OUTPUT = "Output Image";

    public SubImageNodeContainer(float x, float y) {
        super(x, y, "SubImage Node");
        readerNodes.put(IMAGE, new ImageNode(this,true));
        readerNodes.put(X, new NumberNode(this,true, 0));
        readerNodes.put(Y, new NumberNode(this,true, 0));
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
        if(readerNodes.get(IMAGE) instanceof ImageNode img &&
            readerNodes.get(X) instanceof NumberNode x &&
            readerNodes.get(Y) instanceof NumberNode y &&
            readerNodes.get(WIDTH) instanceof NumberNode width &&
            readerNodes.get(HEIGHT) instanceof NumberNode height){
            if(writerNodes.get(OUTPUT) instanceof ImageNode out){
                if(Window.ticks % 20 == 0 && x.getValue() != null && y.getValue() != null && width.getValue() != null && height.getValue() != null){
                    out.setValue(img.getValue().getSubimage(x.getValue().intValue(),y.getValue().intValue(),width.getValue().intValue(),height.getValue().intValue()));
                }
            }
        }
    }
}
