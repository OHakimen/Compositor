package com.hakimen.nodeImageEditor.core.containers.utilityNodes.spliters;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.node.StringNode;

public class StringSplitterNodeContainer extends NodeContainer {

    static final String COUNT = "Count";

    int lastCount = 0;

    static final String INPUT = "String Input";
    public StringSplitterNodeContainer(float x, float y) {
        super(x, y, "String Splitter Node");
        this.readerNodes.put(COUNT,new NumberNode(uuid,true));
        this.readerNodes.put(INPUT,new StringNode(uuid,true));
    }

    @Override
    public void update() {
        if(readerNodes.get(COUNT) instanceof NumberNode node){
            if(node.getValue().intValue() != lastCount){
                writerNodes.clear();
                for (int i = 0; i < node.getValue().intValue(); i++) {
                    try {
                        writerNodes.put("Output "+i, new StringNode(uuid,false));
                    } catch (Exception e){

                    }
                }
                lastCount = node.getValue().intValue();
            }
        }

        super.update();
        if(this.readerNodes.get(INPUT) instanceof StringNode node){
            for (int i = 0; i < writerNodes.values().size(); i++) {
                var writer = writerNodes.values().stream().toList().get(i);
                if(writer instanceof StringNode out){
                    out.setValue(node.getValue());
                }
            }
        }
    }

    @Override
    public void render() {
        super.render();
    }
}
