package com.hakimen.nodeImageEditor.core.containers.converterNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.*;

public class HSLToColorContainer extends NodeContainer {

    static final String HUE = "Hue", SATURATION = "Saturation", LUMINANCE = "Luminance", COLOR = "Color";

    public HSLToColorContainer(float x, float y) {
        super(x, y, "HSL to Color Node");

        readerNodes.put(HUE, new NumberNode(uuid, true, 0));
        readerNodes.put(SATURATION, new NumberNode(uuid, true, 0));
        readerNodes.put(LUMINANCE, new NumberNode(uuid, true, 0));

        writerNodes.put(COLOR, new ColorNode(uuid, false, Color.BLACK));
    }

    @Override
    public void update() {
        super.update();
        if (writerNodes.get(COLOR) instanceof ColorNode in &&
                readerNodes.get(HUE) instanceof NumberNode h &&
                readerNodes.get(SATURATION) instanceof NumberNode s &&
                readerNodes.get(LUMINANCE) instanceof NumberNode l) {
            in.setValue(Color.getHSBColor(h.getValue().floatValue(),s.getValue().floatValue(),l.getValue().floatValue()));
        }
    }

    @Override
    public void render() {
        super.render();
        RenderUtils.FillRoundedRect(x + 80, y + 64, 64, 64, 16, 16, ((ColorNode)writerNodes.get(COLOR)).getValue());
    }
}
