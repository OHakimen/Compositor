package com.hakimen.nodeImageEditor.core.containers.mathNodes;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

public class TangentNodeContainer extends NodeContainer {

    final static String VALUE = "Value";
    final static String OUTPUT = "Output Value";

    public TangentNodeContainer(float x, float y) {
        super(x, y, "Tangent Node");
        readerNodes.put(VALUE, new NumberNode(uuid,true, 0f));
        writerNodes.put(OUTPUT, new NumberNode(uuid,false, 0f));
    }


    @Override
    public void update() {
        super.update();
        if(readerNodes.get(VALUE) instanceof NumberNode n1){
            if(writerNodes.get(OUTPUT) instanceof NumberNode out){
                out.setValue(Math.tan(n1.getValue().floatValue()));
            }
        }
    }
}
