package com.hakimen.nodeImageEditor.utils;

import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.containers.ExportNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.converterNodes.ColorToHSLContainer;
import com.hakimen.nodeImageEditor.core.containers.converterNodes.ColorToRGBContainer;
import com.hakimen.nodeImageEditor.core.containers.converterNodes.HSLToColorContainer;
import com.hakimen.nodeImageEditor.core.containers.converterNodes.RGBtoColorContainer;
import com.hakimen.nodeImageEditor.core.containers.mathNodes.*;
import com.hakimen.nodeImageEditor.core.containers.modifierNodes.*;
import com.hakimen.nodeImageEditor.core.containers.shapeNodes.OvalShapeNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.shapeNodes.RectangleShapeNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.shapeNodes.RoundRectShapeNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.utilityNodes.*;
import com.hakimen.nodeImageEditor.core.containers.viewNodes.ImageViewNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.viewNodes.ValueViewNodeContainer;

import java.awt.*;
import java.util.function.BiFunction;

public class MenuUtils {


    public static void makeMenu(Menu menu, NodeEditor editor){
        Menu converterNodes = new Menu("Converter Nodes");
        converterNodes.add(makeItem("Color to RGB Node", editor, ColorToRGBContainer::new));
        converterNodes.add(makeItem("RGB to Color Node", editor, RGBtoColorContainer::new));
        converterNodes.add(makeItem("Color to HLS Node", editor, ColorToHSLContainer::new));
        converterNodes.add(makeItem("HSL to Color Node", editor, HSLToColorContainer::new));

        Menu mathNodes = new Menu("Math Nodes");

        Menu basicMathNodes = new Menu("Basic Nodes");
        basicMathNodes.add(makeItem("Add Node", editor, AddNodeContainer::new));
        basicMathNodes.add(makeItem("Divide Node", editor, DivideNodeContainer::new));
        basicMathNodes.add(makeItem("Multiply Node", editor, MultiplyNodeContainer::new));
        basicMathNodes.add(makeItem("Subtract Node", editor, SubtractNodeContainer::new));
        basicMathNodes.add(makeItem("Modulo Node", editor, ModuloValueNodeContainer::new));
        mathNodes.add(basicMathNodes);

        Menu trigMathNodes = new Menu("Trigonometry Nodes");
        trigMathNodes.add(makeItem("Cosine Node", editor, CosineNodeContainer::new));
        trigMathNodes.add(makeItem("Sine Node", editor, SineNodeContainer::new));
        trigMathNodes.add(makeItem("Tangent Node", editor, TangentNodeContainer::new));
        mathNodes.add(trigMathNodes);

        mathNodes.add(makeItem("Absolute Node", editor, AbsoluteValueNodeContainer::new));
        mathNodes.add(makeItem("Random Node", editor, RandomNodeContainer::new));
        mathNodes.add(makeItem("Lerp Node", editor, LerpValueNodeContainer::new));

        Menu modifierNodes = new Menu("Modifier Nodes");
        modifierNodes.add(makeItem("Alpha Masking Node", editor, AlphaMaskingNodeContainer::new));
        modifierNodes.add(makeItem("Brightness Node", editor, BrightnessNodeContainer::new));
        modifierNodes.add(makeItem("Blur Node", editor, BlurNodeContainer::new));
        modifierNodes.add(makeItem("Layering Node", editor, LayeringNodeContainer::new));
        modifierNodes.add(makeItem("Hue Node", editor, HueNodeContainer::new));
        modifierNodes.add(makeItem("Scaling Node", editor, ScalingNodeContainer::new));
        modifierNodes.add(makeItem("Shape Masking Node", editor, ShapeMaskingNodeContainer::new));
        modifierNodes.add(makeItem("Sub Image Node", editor, SubImageNodeContainer::new));
        modifierNodes.add(makeItem("Tint Node", editor, TintNodeContainer::new));
        modifierNodes.add(makeItem("Fill Shape Node", editor, FillShapeNodeContainer::new));
        modifierNodes.add(makeItem("Invert Node", editor, InvertNodeContainer::new));
        modifierNodes.add(makeItem("Threshold Node", editor, ThresholdNodeContainer::new));
        modifierNodes.add(makeItem("Translate Node", editor, TranslanteNodeContainer::new));
        modifierNodes.add(makeItem("Rotate Node", editor, RotateNodeContainer::new));

        Menu shapeNodes = new Menu("Shape Nodes");
        shapeNodes.add(makeItem("Oval Node",editor, OvalShapeNodeContainer::new));
        shapeNodes.add(makeItem("Round Rectangle Node",editor, RoundRectShapeNodeContainer::new));
        shapeNodes.add(makeItem("Rectangle Node",editor, RectangleShapeNodeContainer::new));

        Menu valueNodes = new Menu("Value Nodes");
        valueNodes.add(makeItem("Clock Node", editor, ClockNodeContainer::new));
        valueNodes.add(makeItem("Color Node", editor, ColorNodeContainer::new));
        valueNodes.add(makeItem("Value Node", editor, ValueNodeContainer::new));
        valueNodes.add(makeItem("Image Node", editor, ImageNodeContainer::new));

        Menu viewNodes = new Menu("View Nodes");
        viewNodes.add(makeItem("Image View Node", editor, ImageViewNodeContainer::new));
        viewNodes.add(makeItem("Value View Node", editor, ValueViewNodeContainer::new));

        menu.add(converterNodes);
        menu.add(mathNodes);
        menu.add(modifierNodes);
        menu.add(shapeNodes);
        menu.add(valueNodes);
        menu.add(viewNodes);
        menu.add(makeItem("Export Node",editor, ExportNodeContainer::new));
    }

    static MenuItem makeItem(String name, NodeEditor editor, BiFunction<Float,Float,NodeContainer> container){
        var item = new MenuItem(name);
        item.addActionListener((e)->{
            NodeContainer toSet = container.apply((float) ViewTransformer.transformedMouseX,(float)ViewTransformer.transformedMouseY);
            editor.containers.put(toSet.uuid,toSet);
        });
        return item;
    }
}
