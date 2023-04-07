package com.hakimen.nodeImageEditor.core.containers.mathNodes;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

public class SineNodeContainer extends NodeContainer {

    final static String FIRST_VALUE = "Value";
    final static String OUTPUT = "Output Value";

    public SineNodeContainer(float x, float y) {
        super(x, y, "Sine Node");
        readerNodes.put(FIRST_VALUE, new NumberNode(uuid,true, 0f));
        writerNodes.put(OUTPUT, new NumberNode(uuid,false, 0f));
    }


    @Override
    public void update() {
        super.update();
        if(readerNodes.get(FIRST_VALUE) instanceof NumberNode n1){
            if(writerNodes.get(OUTPUT) instanceof NumberNode out){
                out.setValue(Math.sin(n1.getValue().floatValue()));
            }
        }
    }
}
