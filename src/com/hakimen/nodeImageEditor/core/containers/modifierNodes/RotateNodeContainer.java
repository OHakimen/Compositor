package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class RotateNodeContainer extends NodeContainer {

    static final String ANGLE = "Angle";
    static final String IMAGE = "Image";
    static final String X = "X";
    static final String Y = "Y";

    static final String OUTPUT = "Output Image";

    public RotateNodeContainer(float x, float y) {
        super(x, y, "Rotate Node");
        readerNodes.put(IMAGE, new ImageNode(this,true, new BufferedImage(1,1,2)));
        readerNodes.put(ANGLE, new NumberNode(this,true, 0));
        readerNodes.put(X, new NumberNode(this,true, 0));
        readerNodes.put(Y, new NumberNode(this,true, 0));

        writerNodes.put(OUTPUT, new ImageNode(this,false));
    }

    @Override
    public void update() {
        super.update();
        if(readerNodes.get(IMAGE) instanceof ImageNode img &&
                readerNodes.get(ANGLE) instanceof NumberNode angle &&
                readerNodes.get(X) instanceof NumberNode x &&
                readerNodes.get(Y) instanceof NumberNode y &&
                writerNodes.get(OUTPUT) instanceof ImageNode out){
            var buff = new BufferedImage(img.getValue().getWidth(),img.getValue().getHeight(),2);
            var g = buff.createGraphics();
            g.setTransform(AffineTransform.getRotateInstance(angle.getValue().floatValue(),x.getValue().intValue(),y.getValue().intValue()));
            g.drawImage(img.getValue(),0,0,null);
            g.dispose();
            out.setValue(buff);
        }
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
}
