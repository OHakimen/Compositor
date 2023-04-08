package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.Mathf;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.ImageNode;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class SaturationMapNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String COLOR_MAX = "Color Max";
    static final String COLOR_MIN = "Color Min";
    static final String OUTPUT = "Output Image";

    public SaturationMapNodeContainer(float x, float y) {
        super(x, y, "Saturation Map Node");
        readerNodes.put(IMAGE, new ImageNode(uuid, true, new BufferedImage(1, 1, 2)));
        readerNodes.put(COLOR_MAX, new ColorNode(uuid, true));
        readerNodes.put(COLOR_MIN, new ColorNode(uuid, true));
        writerNodes.put(OUTPUT, new ImageNode(uuid, false, new BufferedImage(1, 1, 2)));
    }

    @Override
    public void render() {
        super.render();
        if (writerNodes.get(OUTPUT) instanceof ImageNode node) {
            if (node.getValue() != null) {
                RenderUtils.ClipShape(new RoundRectangle2D.Float(x, y + sy, sx, sx, 16f, 16f));
                RenderUtils.DrawImage(x, y + sy, sx, sx, node.getValue());
                RenderUtils.ClipShape(null);
            }
        }
    }


    BufferedImage lastImage;
    Color lastMax;
    Color lastMin;

    @Override
    public void update() {
        super.update();
        if (readerNodes.get(IMAGE) instanceof ImageNode node &&
                readerNodes.get(COLOR_MAX) instanceof ColorNode max &&
                readerNodes.get(COLOR_MIN) instanceof ColorNode min &&
                writerNodes.get(OUTPUT) instanceof ImageNode out) {
            if (node.getValue() != null && Window.ticks % 20 == 0 && (
                    lastImage == null || lastImage != node.getValue()
                            || lastMax == null || lastMax != max.getValue()
                            || lastMin == null || lastMin != min.getValue())) {
                var buff = new BufferedImage(node.getValue().getWidth(), node.getValue().getHeight(),2);
                for (int x = 0; x < node.getValue().getWidth(); x++) {
                    for (int y = 0; y < node.getValue().getHeight(); y++) {
                        var hsl = new float[3];
                        Color color = new Color(node.getValue().getRGB(x, y));
                        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsl);

                        var max_r = max.getValue().getRed();
                        var max_g = max.getValue().getGreen();
                        var max_b = max.getValue().getBlue();

                        var min_r = min.getValue().getRed();
                        var min_g = min.getValue().getGreen();
                        var min_b = min.getValue().getBlue();

                        float temp = hsl[2];


                        var newR = (int) (Mathf.lerp(min_r, max_r, temp));
                        var newG = (int) (Mathf.lerp(min_g, max_g, temp));
                        var newB = (int) (Mathf.lerp(min_b, max_b, temp));

                        buff.setRGB(x, y, new Color(newR, newG, newB).getRGB());
                    }
                }
                out.setValue(buff);
                lastImage = node.getValue();
                lastMax = max.getValue();
                lastMin = max.getValue();
            }
        }
    }
}
