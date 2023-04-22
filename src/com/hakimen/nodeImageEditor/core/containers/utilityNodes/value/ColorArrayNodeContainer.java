package com.hakimen.nodeImageEditor.core.containers.utilityNodes.value;

import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.node.utility.ColorArrayNode;

import java.awt.*;

public class ColorArrayNodeContainer extends NodeContainer {

    static final String COUNT = "Count";

    int lastCount = 0;

    static final String OUTPUT = "Colors";
    public ColorArrayNodeContainer(float x, float y) {
        super(x, y, "Color Array Node");
        this.readerNodes.put(COUNT,new NumberNode(uuid,true));
        this.writerNodes.put(OUTPUT,new ColorArrayNode(uuid,false));
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
                        readerNodes.put("Color Input "+i, new ColorNode(uuid,true));
                    } catch (Exception e){

                    }
                }
                lastCount = node.getValue().intValue();
            }
        }
        Color[] colors = new Color[lastCount];
        for (int i = 0; i < lastCount; i++) {
            colors[i] = ((ColorNode)readerNodes.values().stream().toList().get(i+1)).getValue();
        }
        if(writerNodes.get(OUTPUT) instanceof ColorArrayNode colorsArray){
            colorsArray.setValue(colors);
        }
        super.update();
    }

    @Override
    public void render() {
        super.render();
    }
}
