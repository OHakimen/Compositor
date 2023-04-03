package com.hakimen.nodeImageEditor.core.containers.utilityNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.*;

public class ClockNodeContainer extends NodeContainer {

    final static String OUTPUT = "Value";
    public ClockNodeContainer(float x, float y) {
        super(x, y, "Clock Node");

        writerNodes.put(OUTPUT,new NumberNode(this,false, 0));
    }

    @Override
    public void update() {
        super.update();
        if(writerNodes.get(OUTPUT) instanceof NumberNode out){
            out.setValue(Window.ticks);
        }
    }

    @Override
    public void render() {
        super.render();
        RenderUtils.FillRoundedRect(x+8,y + 48,64,32,16,16, Color.DARK_GRAY.darker());
        RenderUtils.DrawString((int)x+6+16,(int)y+42+16,Color.WHITE,this.writerNodes.get(OUTPUT).getValue());
    }
}
