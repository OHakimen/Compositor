package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class AlphaMaskingNodeContainer extends NodeContainer {
    static final String ALPHA_MASK = "Alpha Mask";
    static final String IMAGE = "Image";
    static final String OUTPUT = "Output Image";

    public AlphaMaskingNodeContainer(float x, float y) {
        super(x, y, "Alpha Masking Node");

        readerNodes.put(ALPHA_MASK, new ImageNode(uuid, true));
        readerNodes.put(IMAGE, new ImageNode(uuid, true));

        writerNodes.put(OUTPUT, new ImageNode(uuid, false));
    }

    @Override
    public void render() {
        super.render();
        if (writerNodes.get(OUTPUT) instanceof ImageNode node) {
            RenderUtils.ClipShape(new RoundRectangle2D.Float(x, y + sy, sx, sx, 16f, 16f));
            RenderUtils.DrawImage(x, y + sy, sx, sx, node.getValue());
            RenderUtils.ClipShape(null);
        }
    }

    @Override
    public void update() {
        super.update();
        if (readerNodes.get(ALPHA_MASK) instanceof ImageNode mask &&
                readerNodes.get(IMAGE) instanceof ImageNode image &&
                writerNodes.get(OUTPUT) instanceof ImageNode node) {
            if (Window.ticks % 20 == 0 && mask.getValue() != null && image.getValue() != null){

                int[] imagePixels = image.getValue().getRGB(0, 0, image.getValue().getWidth(), image.getValue().getHeight(), null, 0, image.getValue().getWidth());
                int[] maskPixels = mask.getValue().getRGB(0, 0, image.getValue().getWidth(), image.getValue().getHeight(), null, 0, image.getValue().getWidth());

                for (int i = 0; i < imagePixels.length; i++) {
                    int color = imagePixels[i] & 0x00ffffff; // Mask preexisting alpha
                    int alpha = maskPixels[i] << 24; // Shift blue to alpha
                    imagePixels[i] = color | alpha;
                }
                var buff = new BufferedImage(image.getValue().getWidth(), image.getValue().getHeight(), 2);
                buff.setRGB(0, 0, image.getValue().getWidth(), image.getValue().getHeight(), imagePixels, 0, image.getValue().getWidth());
                node.setValue(buff);
            }
        }
    }
}
