package com.hakimen.nodeImageEditor.core.containers.modifiers;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TintNodeContainer extends NodeContainer {
    public TintNodeContainer(float x, float y) {
        super(x, y, "Tint Node");
        readerNodes.put("Image", new ImageNode(this,true));
        readerNodes.put("Color", new ColorNode(this,true, Color.white));
        writerNodes.put("Output Image", new ImageNode(this,false));
    }

    @Override
    public void render() {
        if(writerNodes.get("Output Image") instanceof ImageNode node){
            RenderUtils.ClipShape(new RoundRectangle2D.Float(x,y+sy,sx,sx, 16f,16f));
            RenderUtils.DrawImage(x,y+sy,sx,sx,node.getValue());
            RenderUtils.ClipShape(null);
        }
        super.render();
    }

    @Override
    public void update() {
        if(readerNodes.get("Image") instanceof ImageNode node){
            if(node.getValue() != null){
                if(writerNodes.get("Output Image") instanceof ImageNode out &&
                        readerNodes.get("Color")instanceof ColorNode color){
                    if(Window.ticks % 20 == 0){
                        out.setValue(RenderUtils.GetTintedImage(node.getValue(),color.getValue()));
                    }
                }
            }
        }
        super.update();
    }
}
