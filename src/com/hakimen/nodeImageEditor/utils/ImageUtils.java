package com.hakimen.nodeImageEditor.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    public static BufferedImage[] batchOpenAsImage(File[] files){
        BufferedImage[] images = new BufferedImage[files.length];
        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(files[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return images;
    }
}
