package com.hakimen.nodeImageEditor;

import com.hakimen.engine.core.io.Keyboard;
import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.node.ShapeNode;
import com.hakimen.nodeImageEditor.utils.Collisions;
import com.hakimen.nodeImageEditor.utils.MenuUtils;
import com.hakimen.nodeImageEditor.utils.Pair;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import javax.management.monitor.MonitorSettingException;
import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;

public class NodeEditor {


    static int NodeConnectionRemoveKey = KeyEvent.VK_DELETE;
    static int ContainerRemoveKey = KeyEvent.VK_DELETE;
    MenuBar popupMenu = new MenuBar();

    public NodeEditor(){
        Menu popup = new Menu("Nodes");
        MenuUtils.makeMenu(popup,this);
        popupMenu.add(popup);
        Window.frame.setMenuBar(popupMenu);
    }

    public ArrayList<NodeContainer> containers = new ArrayList<>();
    public boolean borderClicked;

    public Node<?> currentNode;
    public NodeContainer clickedContainer;
    public ArrayList<Pair<Node<?>,Node<?>>> connections = new ArrayList<>();
    public void update(){

        for(int j = 0; j < containers.size(); j++){
            NodeContainer container = containers.get(j);
            if(Keyboard.keys[NodeConnectionRemoveKey].pressed && Collisions.pointToRect(ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY,container.x,container.y,container.sx,40)){
                containers.remove(j);
                for (int i = 0; i < connections.size(); i++) {
                    var con = connections.get(i);
                    if(con.getFirst().getContainer() == container){
                        connections.remove(i);
                    }else if(con.getSecond().getContainer() == container){
                        connections.remove(i);
                    }
                }
            }

            if(Mouse.mouseButtons[MouseEvent.BUTTON1].down && !borderClicked && currentNode == null){
                if(Collisions.pointToRect(ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY,container.x,container.y,container.sx,40)){
                    borderClicked = true;
                    clickedContainer = container;
                }
            }
            if(!Mouse.mouseButtons[MouseEvent.BUTTON1].down && borderClicked){
                borderClicked = false;
            }
            if(borderClicked){
                clickedContainer.x = ViewTransformer.transformedMouseX;
                clickedContainer.y = ViewTransformer.transformedMouseY;
            }
            for (var node:container.writerNodes.values()) {
                var calcX = container.x + container.sx - 2;
                var calcY = container.y + (container.writerNodes.values().stream().toList().indexOf(node)-1) * 24 + 96;

                if(Keyboard.keys[NodeConnectionRemoveKey].pressed && Collisions.pointToCircle(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY,calcX,calcY,4)){
                    for (int i = 0; i < connections.size(); i++) {
                        var con = connections.get(i);
                        if(con.getFirst() == node){
                            connections.remove(i);
                        }else if(con.getSecond() == node){
                            connections.remove(i);
                        }
                    }
                }

                if(Mouse.mouseButtons[MouseEvent.BUTTON1].down && Collisions.pointToCircle(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY,calcX,calcY,4)){
                    if(currentNode == null){
                        currentNode = node;
                    }else if(currentNode != node){
                        connections.add(new Pair<>(currentNode,node));
                        currentNode = null;
                    }
                }else if(!Mouse.mouseButtons[MouseEvent.BUTTON1].down){
                    currentNode = null;
                }
            }
            for (var node:container.readerNodes.values()) {
                var calcX = container.x - 2;
                var calcY = container.y + (container.readerNodes.values().stream().toList().indexOf(node)-1) * 24 + 96;

                if(Keyboard.keys[NodeConnectionRemoveKey].pressed && Collisions.pointToCircle(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY,calcX,calcY,4)){
                    for (int i = 0; i < connections.size(); i++) {
                        var con = connections.get(i);
                        if(con.getFirst() == node){
                            connections.remove(i);
                        }else if(con.getSecond() == node){
                            connections.remove(i);
                        }
                    }
                }
                if(Mouse.mouseButtons[MouseEvent.BUTTON1].down && Collisions.pointToCircle(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY,calcX,calcY,4)){
                    if(currentNode == null){
                        currentNode = node;
                    }else if(currentNode != node){
                        connections.add(new Pair<>(currentNode,node));
                        currentNode = null;
                    }
                }else if(!Mouse.mouseButtons[MouseEvent.BUTTON1].down){
                    currentNode = null;
                }
            }
            container.update();
        }
        for (var pair:connections) {
            if(pair.getFirst().isReader()){
                if(pair.getFirst() instanceof NumberNode toWrite && pair.getSecond() instanceof NumberNode toRead)
                    toWrite.setValue(toRead.getValue());
                if(pair.getFirst() instanceof ColorNode toWrite && pair.getSecond() instanceof ColorNode toRead)
                    toWrite.setValue(toRead.getValue());
                if(pair.getFirst() instanceof ImageNode toWrite && pair.getSecond() instanceof ImageNode toRead)
                    toWrite.setValue(toRead.getValue());
                if(pair.getFirst() instanceof ShapeNode toWrite && pair.getSecond() instanceof ShapeNode toRead)
                    toWrite.setValue(toRead.getValue());
            }else if(pair.getSecond().isReader()){
                if(pair.getSecond() instanceof NumberNode toWrite && pair.getFirst() instanceof NumberNode toRead)
                    toWrite.setValue(toRead.getValue());
                if(pair.getSecond() instanceof ColorNode toWrite && pair.getFirst() instanceof ColorNode toRead)
                    toWrite.setValue(toRead.getValue());
                if(pair.getSecond() instanceof ImageNode toWrite && pair.getFirst() instanceof ImageNode toRead)
                    toWrite.setValue(toRead.getValue());
                if(pair.getSecond() instanceof ShapeNode toWrite && pair.getFirst() instanceof ShapeNode toRead)
                    toWrite.setValue(toRead.getValue());
            }
        }
    }

    public void render(){
        if(currentNode!= null){
            float fnodeX = 0,fnodeY = 0;
            if(currentNode.isReader()){
                fnodeX = currentNode.getContainer().x - 2;
                fnodeY = currentNode.getContainer().y + (currentNode.getContainer().readerNodes.values().stream().toList().indexOf(currentNode)-1) * 24 + 96;

                var shape = new CubicCurve2D.Float(fnodeX+2,fnodeY+2,fnodeX-64,fnodeY+2,ViewTransformer.transformedMouseX + 64,ViewTransformer.transformedMouseY+2,ViewTransformer.transformedMouseX+2,ViewTransformer.transformedMouseY+2);
                RenderUtils.DrawShape(shape,currentNode.getNodeColor());
            }else{
                fnodeX = currentNode.getContainer().x + currentNode.getContainer().sx - 2;
                fnodeY = currentNode.getContainer().y + (currentNode.getContainer().writerNodes.values().stream().toList().indexOf(currentNode) - 1) * 24+ 96;

                var shape = new CubicCurve2D.Float(fnodeX+2,fnodeY+2,fnodeX+128,fnodeY+2,ViewTransformer.transformedMouseX - 128,ViewTransformer.transformedMouseY+2,ViewTransformer.transformedMouseX+2,ViewTransformer.transformedMouseY+2);
                RenderUtils.DrawShape(shape,currentNode.getNodeColor());
            }
        }
        for (var pair:connections) {
            float fnodeX = 0,fnodeY = 0;
            float snodeX = 0,snodeY = 0;

            if(!pair.getFirst().isReader()){
                fnodeX = pair.getFirst().getContainer().x + pair.getFirst().getContainer().sx - 2;
                fnodeY = pair.getFirst().getContainer().y + (pair.getFirst().getContainer().writerNodes.values().stream().toList().indexOf(pair.getFirst()) - 1) * 24+ 96;

            }else{
                fnodeX = pair.getFirst().getContainer().x - 2;
                fnodeY = pair.getFirst().getContainer().y + (pair.getFirst().getContainer().readerNodes.values().stream().toList().indexOf(pair.getFirst())-1) * 24 + 96;

            }

            if(!pair.getSecond().isReader()){
                snodeX = pair.getSecond().getContainer().x + pair.getSecond().getContainer().sx - 2;
                snodeY = pair.getSecond().getContainer().y + (pair.getSecond().getContainer().writerNodes.values().stream().toList().indexOf(pair.getSecond()) - 1) * 24+ 96;
            }else {
                snodeX = pair.getSecond().getContainer().x - 2;
                snodeY = pair.getSecond().getContainer().y + (pair.getSecond().getContainer().readerNodes.values().stream().toList().indexOf(pair.getSecond())-1) * 24 + 96;
            }
            if(pair.getFirst().isReader()){
                var shape = new CubicCurve2D.Float(fnodeX+2,fnodeY+2,fnodeX-128,fnodeY+2,snodeX + 128,snodeY+2,snodeX+2,snodeY+2);
                RenderUtils.DrawShape(shape,pair.getFirst().getNodeColor());
            }else if(pair.getSecond().isReader()){
                var shape = new CubicCurve2D.Float(fnodeX+2,fnodeY+2,fnodeX+128,fnodeY+2,snodeX - 128,snodeY+2,snodeX+2,snodeY+2);
                RenderUtils.DrawShape(shape,pair.getFirst().getNodeColor());
            }

        }
        for (int i = 0; i < containers.size(); i++) {
            var container = containers.get(i);
            container.render();
        }
    }
}
