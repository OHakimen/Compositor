package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.ImageNode;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TintNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String COLOR = "Color";
    static final String OUTPUT = "Output Image";

    public TintNodeContainer(float x, float y) {
        super(x, y, "Tint Node");
        readerNodes.put(IMAGE, new ImageNode(uuid,true));
        readerNodes.put(COLOR, new ColorNode(uuid,true, Color.white));
        writerNodes.put(OUTPUT, new ImageNode(uuid,false));
    }

    @Override
    public void render() {
        if(writerNodes.get(OUTPUT) instanceof ImageNode node){
            RenderUtils.ClipShape(new RoundRectangle2D.Float(x,y+sy,sx,sx, 16f,16f));
            RenderUtils.DrawImage(x,y+sy,sx,sx,node.getValue());
            RenderUtils.ClipShape(null);
        }
        super.render();
    }

    @Override
    public void update() {
        if(readerNodes.get(IMAGE) instanceof ImageNode node){
            if(node.getValue() != null){
                if(writerNodes.get(OUTPUT) instanceof ImageNode out &&
                        readerNodes.get(COLOR)instanceof ColorNode color){
                    if(Window.ticks % 20 == 0){
                        out.setValue(RenderUtils.GetTintedImage(node.getValue(),color.getValue()));
                    }
                }
            }
        }
        super.update();
    }
}
