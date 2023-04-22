package com.hakimen.nodeImageEditor.utils;

import com.hakimen.nodeImageEditor.core.node.*;
import com.hakimen.nodeImageEditor.core.node.utility.ColorArrayNode;
import com.hakimen.nodeImageEditor.core.node.utility.NumberArrayNode;

import java.awt.*;
import java.util.HashMap;

public class NodeColorsUtils {
    public static HashMap<Class<?>, Color> nodeColors = new HashMap<>();
    public static void init(){
        nodeColors.put(ColorArrayNode.class, new Color(0xC70046));
        nodeColors.put(ColorNode.class, new Color(0xFF2BA8));
        nodeColors.put(NumberArrayNode.class, new Color(0x7BFFAA));
        nodeColors.put(NumberNode.class, new Color(0x00FF15));
        nodeColors.put(ImageNode.class,new Color(0x4E80FF));
        nodeColors.put(ShapeNode.class,new Color(0xFF6200));
        nodeColors.put(StringNode.class,new Color(0x6600FF));
    }
}
