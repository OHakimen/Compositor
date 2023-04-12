package com.hakimen.nodeImageEditor.core.containers.utilityNodes;

import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.utils.Collisions;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ColorNodeContainer extends NodeContainer {

    static final String COLOR = "Color";
    public ColorNodeContainer(float x, float y) {
        super(x, y, "Color Node");
        this.sx = name.length() * 8 + (4*32);
        writerNodes.put(COLOR, new ColorNode(uuid,false,Color.WHITE));
    }


    @Override
    public void update() {
        if (Mouse.mouseButtons[MouseEvent.BUTTON1].pressed) {
            if (Collisions.pointToRect(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY, x + 8, y + 48, 128, 32)) {
                if (writerNodes.get(COLOR) instanceof ColorNode node) {
                    Color color = JColorChooser.showDialog(null, "Pick a Color", node.getValue(), true);
                    if (color != null) {
                        node.setValue(color);
                    }
                }
            }
        }
        super.update();
    }

    @Override
    public void render() {
        super.render();
        if(writerNodes.get(COLOR) instanceof ColorNode node){
            RenderUtils.FillRoundedRect(x+8,y + 48,128,32,16,16, node.getValue());
        }
    }
}
