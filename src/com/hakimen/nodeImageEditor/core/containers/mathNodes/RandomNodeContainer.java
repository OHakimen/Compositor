package com.hakimen.nodeImageEditor.core.containers.mathNodes;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.util.Random;

public class RandomNodeContainer extends NodeContainer {

    final static String LOWEST_VALUE = "Lowest Value";
    final static String HIGHEST_VALUE = "Highest Value";
    final static String OUTPUT = "Output Value";

    Random r = new Random();

    public RandomNodeContainer(float x, float y) {
        super(x, y, "Random Node");
        sx += 16;
        readerNodes.put(LOWEST_VALUE, new NumberNode(this,true, 0f));
        readerNodes.put(HIGHEST_VALUE, new NumberNode(this,true, 1f));
        writerNodes.put(OUTPUT, new NumberNode(this,false, 0f));
    }


    @Override
    public void update() {
        super.update();
        if(readerNodes.get(LOWEST_VALUE) instanceof NumberNode n1 && readerNodes.get(HIGHEST_VALUE) instanceof NumberNode n2){
            if(writerNodes.get(OUTPUT) instanceof NumberNode out){
                out.setValue(r.nextFloat(n1.getValue().floatValue(),n2.getValue().floatValue()));
            }
        }
    }
}
