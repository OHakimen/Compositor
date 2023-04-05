package com.hakimen.nodeImageEditor.core.containers.converterNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.*;

public class RGBtoColorContainer extends NodeContainer {

    static final String R = "R", G = "G", B = "B", COLOR = "Color";

    public RGBtoColorContainer(float x, float y) {
        super(x, y, "RGB to Color Node");

        readerNodes.put(R, new NumberNode(this, true, 255));
        readerNodes.put(G, new NumberNode(this, true, 255));
        readerNodes.put(B, new NumberNode(this, true, 255));

        writerNodes.put(COLOR, new ColorNode(this, false, Color.WHITE));
    }

    @Override
    public void update() {
        super.update();
        if (writerNodes.get(COLOR) instanceof ColorNode out &&
                readerNodes.get(R) instanceof NumberNode r &&
                readerNodes.get(G) instanceof NumberNode g &&
                readerNodes.get(B) instanceof NumberNode b) {
            out.setValue(new Color(r.getValue().intValue(),g.getValue().intValue(),b.getValue().intValue()));
        }
    }

    @Override
    public void render() {
        super.render();
        RenderUtils.FillRoundedRect(x + 8 + 32, y + 64, 64, 64, 16, 16, ((ColorNode)writerNodes.get(COLOR)).getValue());
    }
}
