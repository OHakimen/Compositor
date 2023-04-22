package com.hakimen.nodeImageEditor.core.containers.utilityNodes.value;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.node.utility.ColorArrayNode;
import com.hakimen.nodeImageEditor.core.node.utility.NumberArrayNode;

import java.awt.*;

public class NumberArrayNodeContainer extends NodeContainer {

    static final String COUNT = "Count";

    int lastCount = 0;

    static final String OUTPUT = "Number";
    public NumberArrayNodeContainer(float x, float y) {
        super(x, y, "Number Array Node");
        this.readerNodes.put(COUNT,new NumberNode(uuid,true));
        this.writerNodes.put(OUTPUT,new NumberArrayNode(uuid,false));
    }

    @Override
    public void update() {
        if(readerNodes.get(COUNT) instanceof NumberNode node){
            if(node.getValue().intValue() != lastCount){
                readerNodes.forEach((k,v)->{
                    if(!k.equals(COUNT)){
                        readerNodes.remove(v);
                    }
                });
                for (int i = 0; i < node.getValue().intValue(); i++) {
                    try {
                        readerNodes.put("Number Input "+i, new NumberNode(uuid,true));
                    } catch (Exception e){

                    }
                }
                lastCount = node.getValue().intValue();
            }
        }
        Number[] numbers = new Number[lastCount];
        for (int i = 0; i < lastCount; i++) {
            numbers[i] = ((NumberNode)readerNodes.values().stream().toList().get(i+1)).getValue();
        }
        if(writerNodes.get(OUTPUT) instanceof NumberArrayNode numberArrayNode){
            numberArrayNode.setValue(numbers);
        }
        super.update();
    }

    @Override
    public void render() {
        super.render();
    }
}
