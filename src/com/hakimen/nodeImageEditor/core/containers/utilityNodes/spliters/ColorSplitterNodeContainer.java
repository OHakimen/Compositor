package com.hakimen.nodeImageEditor.core.containers.utilityNodes.spliters;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.node.ShapeNode;

public class ColorSplitterNodeContainer extends NodeContainer {

    static final String COUNT = "Count";

    int lastCount = 0;

    static final String INPUT = "Color Input";
    public ColorSplitterNodeContainer(float x, float y) {
        super(x, y, "Color Splitter Node");
        this.readerNodes.put(COUNT,new NumberNode(uuid,true));
        this.readerNodes.put(INPUT,new ColorNode(uuid,true));
    }

    @Override
    public void update() {
        if(readerNodes.get(COUNT) instanceof NumberNode node){
            if(node.getValue().intValue() != lastCount){
                writerNodes.clear();
                for (int i = 0; i < node.getValue().intValue(); i++) {
                    try {
                        writerNodes.put("Output "+i, new ColorNode(uuid,false));
                    } catch (Exception e){

                    }
                }
                lastCount = node.getValue().intValue();
            }
        }

        super.update();
        if(this.readerNodes.get(INPUT) instanceof ColorNode node){
            for (int i = 0; i < writerNodes.values().size(); i++) {
                var writer = writerNodes.values().stream().toList().get(i);
                if(writer instanceof ColorNode out){
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
