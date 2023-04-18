package com.hakimen.nodeImageEditor.core.containers.utilityNodes.value;

import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.node.StringNode;
import com.hakimen.nodeImageEditor.utils.Collisions;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class StringNodeContainer extends NodeContainer {

    static final String VALUE = "String";
    public StringNodeContainer(float x, float y) {
        super(x, y, "String Node");
        this.sx = name.length() * 8 + (4*32);
        writerNodes.put(VALUE, new StringNode(uuid,false));
    }
    @Override
    public void update() {

        if(Mouse.mouseButtons[MouseEvent.BUTTON1].pressed){
            if(Collisions.pointToRect(ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY,x+8,y + 48,128,32)){
                var str = JOptionPane.showInputDialog("Insert a string");
                str = str == null ? "" : str;
                if (writerNodes.get(VALUE) instanceof StringNode node) {
                    node.setValue(str);
                }
            }
        }
        super.update();
    }

    @Override
    public void render() {
        super.render();
        RenderUtils.FillRoundedRect(x+8,y + 48,128,32,16,16, Color.DARK_GRAY.darker());
        RenderUtils.ClipShape(new RoundRectangle2D.Float(x+8,y+48,120,32,16,16));
        RenderUtils.DrawString((int)x+6+16,(int)y+42+16,Color.WHITE,this.writerNodes.get(VALUE).getValue());
        RenderUtils.ClipShape(null);
    }
}
