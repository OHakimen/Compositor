package com.hakimen.nodeImageEditor.utils;

import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.containers.BulkProcessNodeContainer;
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
import com.hakimen.nodeImageEditor.core.containers.utilityNodes.spliters.*;
import com.hakimen.nodeImageEditor.core.containers.viewNodes.ImageViewNodeContainer;
import com.hakimen.nodeImageEditor.core.containers.viewNodes.ValueViewNodeContainer;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiFunction;

public class MenuUtils {
    public static void makeJMenu(JPopupMenu menu, NodeEditor editor){
        JMenu converterNodes = new JMenu("Converter Nodes");
        converterNodes.add(makeJItem("Color to HLS Node", editor, ColorToHSLContainer::new));
        converterNodes.add(makeJItem("Color to RGB Node", editor, ColorToRGBContainer::new));
        converterNodes.add(makeJItem("HSL to Color Node", editor, HSLToColorContainer::new));
        converterNodes.add(makeJItem("RGB to Color Node", editor, RGBtoColorContainer::new));

        JMenu mathNodes = new JMenu("Math Nodes");

        JMenu basicMathNodes = new JMenu("Basic Nodes");
        basicMathNodes.add(makeJItem("Add Node", editor, AddNodeContainer::new));
        basicMathNodes.add(makeJItem("Divide Node", editor, DivideNodeContainer::new));
        basicMathNodes.add(makeJItem("Modulo Node", editor, ModuloValueNodeContainer::new));
        basicMathNodes.add(makeJItem("Multiply Node", editor, MultiplyNodeContainer::new));
        basicMathNodes.add(makeJItem("Subtract Node", editor, SubtractNodeContainer::new));
        mathNodes.add(basicMathNodes);

        JMenu trigMathNodes = new JMenu("Trigonometry Nodes");
        trigMathNodes.add(makeJItem("Cosine Node", editor, CosineNodeContainer::new));
        trigMathNodes.add(makeJItem("Sine Node", editor, SineNodeContainer::new));
        trigMathNodes.add(makeJItem("Tangent Node", editor, TangentNodeContainer::new));
        mathNodes.add(trigMathNodes);

        mathNodes.add(makeJItem("Absolute Node", editor, AbsoluteValueNodeContainer::new));
        mathNodes.add(makeJItem("Lerp Node", editor, LerpValueNodeContainer::new));
        mathNodes.add(makeJItem("Random Node", editor, RandomNodeContainer::new));

        JMenu modifierNodes = new JMenu("Modifier Nodes");
        modifierNodes.add(makeJItem("Alpha Masking Node", editor, AlphaMaskingNodeContainer::new));
        modifierNodes.add(makeJItem("Blur Node", editor, BlurNodeContainer::new));
        modifierNodes.add(makeJItem("Brightness Node", editor, BrightnessNodeContainer::new));
        modifierNodes.add(makeJItem("Fill Shape Node", editor, FillShapeNodeContainer::new));
        modifierNodes.add(makeJItem("Grayscale Node", editor, GrayScaleNodeContainer::new));
        modifierNodes.add(makeJItem("Hue Node", editor, HueNodeContainer::new));
        modifierNodes.add(makeJItem("Invert Node", editor, InvertNodeContainer::new));
        modifierNodes.add(makeJItem("Layering Node", editor, LayeringNodeContainer::new));
        modifierNodes.add(makeJItem("Luminance Map Node", editor, LuminanceMapNodeContainer::new));
        modifierNodes.add(makeJItem("Rotate Node", editor, RotateNodeContainer::new));
        modifierNodes.add(makeJItem("Scaling Node", editor, ScalingNodeContainer::new));
        modifierNodes.add(makeJItem("Shape Masking Node", editor, ShapeMaskingNodeContainer::new));
        modifierNodes.add(makeJItem("Sub Image Node", editor, SubImageNodeContainer::new));
        modifierNodes.add(makeJItem("Tint Node", editor, TintNodeContainer::new));
        modifierNodes.add(makeJItem("Threshold Node", editor, ThresholdNodeContainer::new));
        modifierNodes.add(makeJItem("Translate Node", editor, TranslanteNodeContainer::new));

        JMenu shapeNodes = new JMenu("Shape Nodes");
        shapeNodes.add(makeJItem("Oval Node",editor, OvalShapeNodeContainer::new));
        shapeNodes.add(makeJItem("Round Rectangle Node",editor, RoundRectShapeNodeContainer::new));
        shapeNodes.add(makeJItem("Rectangle Node",editor, RectangleShapeNodeContainer::new));

        JMenu valueNodes = new JMenu("Value Nodes");

        JMenu splitterNodes = new JMenu("Splitter Nodes");
        splitterNodes.add(makeJItem("Number Splitter Node", editor, NumberSplitterNodeContainer::new));
        splitterNodes.add(makeJItem("Shape Splitter Node", editor, ShapeSplitterNodeContainer::new));
        splitterNodes.add(makeJItem("Image Splitter Node", editor, ImageSplitterNodeContainer::new));
        splitterNodes.add(makeJItem("Color Splitter Node", editor, ColorSplitterNodeContainer::new));
        splitterNodes.add(makeJItem("String Splitter Node", editor, StringSplitterNodeContainer::new));
        valueNodes.add(splitterNodes);

        valueNodes.add(makeJItem("Clock Node", editor, ClockNodeContainer::new));
        valueNodes.add(makeJItem("Color Node", editor, ColorNodeContainer::new));
        valueNodes.add(makeJItem("Image Node", editor, ImageNodeContainer::new));
        valueNodes.add(makeJItem("Value Node", editor, ValueNodeContainer::new));
        valueNodes.add(makeJItem("String Node", editor, StringNodeContainer::new));

        JMenu viewNodes = new JMenu("View Nodes");
        viewNodes.add(makeJItem("Image View Node", editor, ImageViewNodeContainer::new));
        viewNodes.add(makeJItem("Value View Node", editor, ValueViewNodeContainer::new));

        menu.add(converterNodes);
        menu.add(mathNodes);
        menu.add(modifierNodes);
        menu.add(shapeNodes);
        menu.add(valueNodes);
        menu.add(viewNodes);
        menu.add(makeJItem("Export Node",editor, ExportNodeContainer::new));
        menu.add(makeJItem("Bulk Process Node",editor, BulkProcessNodeContainer::new));
    }

    static JMenuItem makeJItem(String name, NodeEditor editor, BiFunction<Float,Float,NodeContainer> container){
        var item = new JMenuItem(name);
        item.addActionListener((e)->{
            NodeContainer toSet = container.apply((float) ViewTransformer.transformedMouseX,(float)ViewTransformer.transformedMouseY);
            editor.containers.put(toSet.uuid,toSet);
        });
        return item;
    }
}
