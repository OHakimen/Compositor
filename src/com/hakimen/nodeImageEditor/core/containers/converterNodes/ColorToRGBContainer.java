package com.hakimen.nodeImageEditor.core.containers.converterNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.*;

public class ColorToRGBContainer extends NodeContainer {

    static final String R = "R", G = "G", B = "B", COLOR = "Color";

    public ColorToRGBContainer(float x, float y) {
        super(x, y, "RGB to Color Node");

        writerNodes.put(R, new NumberNode(this, false, 0));
        writerNodes.put(G, new NumberNode(this, false, 0));
        writerNodes.put(B, new NumberNode(this, false, 0));

        readerNodes.put(COLOR, new ColorNode(this, true, Color.BLACK));
    }

    @Override
    public void update() {
        super.update();
        if (readerNodes.get(COLOR) instanceof ColorNode in &&
                writerNodes.get(R) instanceof NumberNode r &&
                writerNodes.get(G) instanceof NumberNode g &&
                writerNodes.get(B) instanceof NumberNode b) {
            r.setValue(in.getValue().getRed());
            g.setValue(in.getValue().getGreen());
            b.setValue(in.getValue().getBlue());
        }
    }

    @Override
    public void render() {
        super.render();
        RenderUtils.FillRoundedRect(x + 48, y + 64, 64, 64, 16, 16, ((ColorNode)readerNodes.get(COLOR)).getValue());
    }
}
