package com.hakimen.nodeImageEditor.core.containers;

import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.StringNode;
import com.hakimen.nodeImageEditor.core.notifications.notification.SuccessNotification;
import com.hakimen.nodeImageEditor.core.notifications.notification.WarningNotification;
import com.hakimen.nodeImageEditor.utils.Collisions;
import com.hakimen.nodeImageEditor.utils.FileUtils;
import com.hakimen.nodeImageEditor.utils.ImageUtils;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.hakimen.nodeImageEditor.core.notifications.NotificationHandler.NOTIFY_NORMAL;

public class BulkProcessNodeContainer extends NodeContainer {

    static final String NAME_TEMPLATE = "Name Template";
    static final String IMAGE_IN = "Export Image";
    static final String IMAGE_OUT = "Image Output";


    public BulkProcessNodeContainer(float x, float y) {
        super(x, y, "Bulk Process Node");
        sx += 64;
        readerNodes.put(NAME_TEMPLATE,new StringNode(uuid,true));
        readerNodes.put(IMAGE_IN,new ImageNode(uuid,true));
        writerNodes.put(IMAGE_OUT,new ImageNode(uuid,false));
    }

    BufferedImage lastInputImage = new BufferedImage(1,1,2);
    BufferedImage[] images;
    int imgCurrent = 0;
    File file;

    boolean started;
    JFileChooser chooser = new JFileChooser("/");
    @Override
    public void update() {
        super.update();


        if(Mouse.mouseButtons[MouseEvent.BUTTON1].pressed) {
            //Try loading a folder
            if (Collisions.pointToRect(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY, x + 8, y + 48 + 64, 100, 32)) {
                chooser.showOpenDialog(null);
                try {
                    if (chooser.getSelectedFile() != null) {
                        if (file != chooser.getSelectedFile()) {
                            file = chooser.getSelectedFile().getParentFile();
                            NodeEditor.handler.push(new SuccessNotification("Loaded Folder", "" + file, NOTIFY_NORMAL));
                        } else {
                            NodeEditor.handler.push(new WarningNotification("Couldn't load", "Path isn't folder", NOTIFY_NORMAL));
                        }
                    } else {
                        NodeEditor.handler.push(new WarningNotification("Couldn't load", "No file provided", NOTIFY_NORMAL));
                    }
                } catch (Exception ignored) {
                    NodeEditor.handler.push(new WarningNotification("Processing "+ images.length + " images", "This might take a while", NOTIFY_NORMAL));
                }
            }

            if (Collisions.pointToRect(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY, x + 8 + 128, y + 48 + 64, 100, 32)) {
                var files = FileUtils.getBatch(file, ".png", ".gif", ".jpeg", ".jpg");
                images = ImageUtils.batchOpenAsImage(files);
                started = true;
                imgCurrent = 0;
            }
        }
        //Process images in bulk
        if(started){
            if (readerNodes.get(NAME_TEMPLATE) instanceof StringNode stringNode &&
                    readerNodes.get(IMAGE_IN) instanceof ImageNode input &&
                    writerNodes.get(IMAGE_OUT) instanceof ImageNode out) {
                if (input.getValue() != null && lastInputImage != input.getValue()) {
                    String temp = "img";
                    if (!stringNode.getValue().equals("")) {
                        temp = stringNode.getValue();
                    }
                    try {
                        ImageIO.write(input.getValue(), "PNG", FileUtils.createFilesFor(new File(file.getAbsolutePath() + "/out/" + temp + "_" + imgCurrent + ".png")));
                    } catch (IOException ignored) {

                    }
                    if (imgCurrent+1 < images.length) {
                        imgCurrent++;
                        lastInputImage = input.getValue();
                        out.setValue(images[imgCurrent]);
                    }else{
                        NodeEditor.handler.push(new SuccessNotification("Processed "+ images.length + " images", "", NOTIFY_NORMAL));
                        started = false;
                    }
                }
            }
        }
        sy += 32;
    }

    @Override
    public void render() {
        super.render();
        RenderUtils.FillRoundedRect(x+8,y + 48+64,106,32,16,16, Color.DARK_GRAY.darker());
        RenderUtils.DrawString((int)x+6+12,(int)y+42+16+64,Color.WHITE,"Change Folder");
        RenderUtils.FillRoundedRect(x+8+128,y + 48+64,100,32,16,16, Color.DARK_GRAY.darker());
        RenderUtils.DrawString((int)x+6+12+128,(int)y+42+16+64,Color.WHITE,"Export");
    }
}
