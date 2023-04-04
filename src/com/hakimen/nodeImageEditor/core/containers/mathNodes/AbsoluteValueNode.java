package com.hakimen.nodeImageEditor.core.containers.mathNodes;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

public class AbsoluteValueNode extends NodeContainer {

    final static String VALUE = "Value";
    final static String OUTPUT = "Output Value";

    public AbsoluteValueNode(float x, float y) {
        super(x, y, "Abs Node");
        readerNodes.put(VALUE, new NumberNode(this,true, 0f));
        writerNodes.put(OUTPUT, new NumberNode(this,false, 0f));
    }


    @Override
    public void update() {
        super.update();
        if(readerNodes.get(VALUE) instanceof NumberNode n1){
            if(writerNodes.get(OUTPUT) instanceof NumberNode out){
                out.setValue(Math.abs(n1.getValue().floatValue()));
            }
        }
    }
}
