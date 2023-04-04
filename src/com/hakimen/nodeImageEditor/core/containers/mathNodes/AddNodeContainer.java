package com.hakimen.nodeImageEditor.core.containers.mathNodes;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

public class AddNodeContainer extends NodeContainer {

    final static String FIRST_VALUE = "First Value";
    final static String SECOND_VALUE = "Second Value";
    final static String OUTPUT = "Output Value";

    public AddNodeContainer(float x, float y) {
        super(x, y, "Add Node");
        sx = sx + 64;
        readerNodes.put(FIRST_VALUE, new NumberNode(this,true, 0f));
        readerNodes.put(SECOND_VALUE, new NumberNode(this,true, 0f));

        writerNodes.put(OUTPUT, new NumberNode(this,false, 0f));
    }


    @Override
    public void update() {
        super.update();

        if(readerNodes.get(FIRST_VALUE) instanceof NumberNode n1 && readerNodes.get(SECOND_VALUE) instanceof NumberNode n2){
            if(writerNodes.get(OUTPUT) instanceof NumberNode out){
                out.setValue(n1.getValue().floatValue()+n2.getValue().floatValue());
            }
        }
    }
}