package com.hakimen.nodeImageEditor;

import com.hakimen.engine.core.io.Keyboard;
import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.utils.Collisions;
import com.hakimen.nodeImageEditor.utils.Pair;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import javax.swing.text.View;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NodeEditor {

    public ArrayList<NodeContainer> containers = new ArrayList<>();
    public boolean borderClicked;

    public Node<?> currentNode;
    public NodeContainer clickedContainer;
    public ArrayList<Pair<Node<?>,Node<?>>> connections = new ArrayList<>();
    public void update(){
        for (NodeContainer container:containers) {
            if(Mouse.mouseButtons[MouseEvent.BUTTON1].down && !borderClicked){
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

                if(Keyboard.keys[KeyEvent.VK_X].pressed && Collisions.pointToCircle(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY,calcX,calcY,4)){
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

                if(Keyboard.keys[KeyEvent.VK_DELETE].pressed && Collisions.pointToCircle(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY,calcX,calcY,4)){
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
            }else if(pair.getSecond().isReader()){
                if(pair.getSecond() instanceof NumberNode toWrite && pair.getFirst() instanceof NumberNode toRead)
                    toWrite.setValue(toRead.getValue());
                if(pair.getSecond() instanceof ColorNode toWrite && pair.getFirst() instanceof ColorNode toRead)
                    toWrite.setValue(toRead.getValue());
                if(pair.getSecond() instanceof ImageNode toWrite && pair.getFirst() instanceof ImageNode toRead)
                    toWrite.setValue(toRead.getValue());
            }
        }
    }

    public void render(){
        for (NodeContainer container:containers) {
            container.render();
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
            RenderUtils.DrawLine(fnodeX+2,fnodeY+2,snodeX+2,snodeY+2,Color.RED);
        }
    }
}
