package com.hakimen.nodeImageEditor.core.containers.utilityNodes;

import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.utils.Collisions;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ValueNodeContainer extends NodeContainer {

    static final String VALUE = "Value";
    public ValueNodeContainer(float x, float y) {
        super(x, y, "Value Node");
        this.sx = name.length() * 8 + (4*32);
        writerNodes.put(VALUE, new NumberNode(this,false,0));
    }


    @Override
    public void update() {

        if(Mouse.mouseButtons[MouseEvent.BUTTON1].pressed){
            if(Collisions.pointToRect(ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY,x+8,y + 48,128,32)){
                var str = JOptionPane.showInputDialog("Insert a value for the value node");
                if(!str.isEmpty()) {
                    var n = Float.parseFloat(str);
                    if (writerNodes.get(VALUE) instanceof NumberNode node) {
                        node.setValue(n);
                    }
                }
            }
        }
        super.update();
    }

    @Override
    public void render() {
        super.render();
        RenderUtils.FillRoundedRect(x+8,y + 48,128,32,16,16, Color.DARK_GRAY.darker());
        RenderUtils.DrawString((int)x+6+16,(int)y+42+16,Color.WHITE,this.writerNodes.get(VALUE).getValue());
    }
}
