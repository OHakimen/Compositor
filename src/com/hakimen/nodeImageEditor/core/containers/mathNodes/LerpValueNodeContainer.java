package com.hakimen.nodeImageEditor.core.containers.mathNodes;

import com.hakimen.engine.core.utils.Mathf;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

public class LerpValueNodeContainer extends NodeContainer {

    final static String FIRST_VALUE = "First Value";
    final static String SECOND_VALUE = "Second Value";
    final static String DELTA = "Delta";
    final static String OUTPUT = "Output Value";

    public LerpValueNodeContainer(float x, float y) {
        super(x, y, "Lerp Node");
        sx += 64;
        readerNodes.put(FIRST_VALUE, new NumberNode(uuid,true, 0f));
        readerNodes.put(SECOND_VALUE, new NumberNode(uuid,true, 0f));
        readerNodes.put(DELTA, new NumberNode(uuid,true, 0f));
        writerNodes.put(OUTPUT, new NumberNode(uuid,false, 0f));
    }


    @Override
    public void update() {
        super.update();
        if(readerNodes.get(FIRST_VALUE) instanceof NumberNode n1 &&
                readerNodes.get(SECOND_VALUE) instanceof NumberNode n2 &&
                readerNodes.get(DELTA) instanceof NumberNode delta){
            if(writerNodes.get(OUTPUT) instanceof NumberNode out){
                out.setValue(Mathf.lerp(n1.getValue().floatValue(),n2.getValue().floatValue(),delta.getValue().floatValue()));
            }
        }
    }
}
