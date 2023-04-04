package com.hakimen.nodeImageEditor.core.containers.modifiers;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class LayeringNodeContainer extends NodeContainer {

    static final String LAYER = "Layer Image";
    static final String BASE = "Base Image";
    static final String X = "X";
    static final String Y = "Y";

    static final String OUTPUT = "Output Image";
    public LayeringNodeContainer(float x, float y) {
        super(x, y, "Layering Node");
        readerNodes.put(X, new NumberNode(this,true, 0));
        readerNodes.put(Y, new NumberNode(this,true, 0));
        readerNodes.put(LAYER, new ImageNode(this,true));
        readerNodes.put(BASE, new ImageNode(this,true));

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
        if(readerNodes.get(LAYER) instanceof ImageNode layer && readerNodes.get(BASE) instanceof ImageNode base && readerNodes.get(X) instanceof NumberNode x && readerNodes.get(Y) instanceof NumberNode y){
            if(layer.getValue() != null && base.getValue() != null){
                if(writerNodes.get(OUTPUT) instanceof ImageNode out){
                    if(Window.ticks % 20 == 0){
                        var image = new BufferedImage(base.getValue().getWidth(),base.getValue().getHeight(),BufferedImage.TYPE_INT_ARGB);
                        var toWrite = image.createGraphics();
                        toWrite.drawImage(base.getValue(),0,0,null);
                        toWrite.drawImage(layer.getValue(),x.getValue().intValue(),y.getValue().intValue(),null);
                        toWrite.dispose();
                        out.setValue(image);
                    }
                }
            }
        }
    }
}
