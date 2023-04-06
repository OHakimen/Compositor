package com.hakimen.nodeImageEditor.core.containers.viewNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ValueViewNodeContainer extends NodeContainer {

    static final String VALUE =  "Value";
    static final String OUTPUT = "Output Value";
    public ValueViewNodeContainer(float x, float y) {
        super(x, y, "Value View Node");
        sx += 64;
        readerNodes.put(VALUE,new NumberNode(this,true,0));
        writerNodes.put(OUTPUT,new NumberNode(this,true, 0));
    }

    @Override
    public void render() {
        super.render();
        RenderUtils.FillRoundedRect(x+64,y + 48,80,32,16,16, Color.DARK_GRAY.darker());
        if(writerNodes.get(OUTPUT) instanceof NumberNode out) {
            RenderUtils.DrawString((int) x + 62 + 16, (int) y + 42 + 16, Color.WHITE, String.format("%.2f", out.getValue().floatValue()));
        }
    }

    @Override
    public void update() {
        super.update();
        if (readerNodes.get(VALUE) instanceof NumberNode node &&
            writerNodes.get(OUTPUT) instanceof NumberNode out) {
            out.setValue(node.getValue());
        }
    }
}
