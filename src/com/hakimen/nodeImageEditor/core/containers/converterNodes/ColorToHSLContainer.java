package com.hakimen.nodeImageEditor.core.containers.converterNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.*;

public class ColorToHSLContainer extends NodeContainer {

    static final String HUE = "Hue", SATURATION = "Saturation", LUMINANCE = "Luminance", COLOR = "Color";

    public ColorToHSLContainer(float x, float y) {
        super(x, y, "Color to HLS Node");

        writerNodes.put(HUE, new NumberNode(uuid, false, 0));
        writerNodes.put(SATURATION, new NumberNode(uuid, false, 0));
        writerNodes.put(LUMINANCE, new NumberNode(uuid, false, 0));

        readerNodes.put(COLOR, new ColorNode(uuid, true, Color.BLACK));
    }

    @Override
    public void update() {
        super.update();
        if (readerNodes.get(COLOR) instanceof ColorNode in &&
                writerNodes.get(HUE) instanceof NumberNode h &&
                writerNodes.get(SATURATION) instanceof NumberNode s &&
                writerNodes.get(LUMINANCE) instanceof NumberNode l) {
            var hsl = new float[3];
            if(in.getValue() != null){
                Color.RGBtoHSB(in.getValue().getRed(),in.getValue().getGreen(),in.getValue().getBlue(),hsl);
                h.setValue(hsl[0]);
                s.setValue(hsl[1]);
                l.setValue(hsl[2]);
            }
        }
    }

    @Override
    public void render() {
        super.render();
        RenderUtils.FillRoundedRect(x + 48, y + 64, 64, 64, 16, 16, ((ColorNode)readerNodes.get(COLOR)).getValue());
    }
}
