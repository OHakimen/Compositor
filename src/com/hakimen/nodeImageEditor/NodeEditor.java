package com.hakimen.nodeImageEditor;

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

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NodeEditor {

    public ArrayList<NodeContainer> containers = new ArrayList<>();
    public boolean borderClicked,nodeClicked;

    public Node<?> clickedNode;
    public NodeContainer clickedContainer;
    public ArrayList<Pair<Node<?>,Node<?>>> connections = new ArrayList<>();
    public void update(){
        for (NodeContainer container:containers) {
            if(Mouse.mouseButtons[MouseEvent.BUTTON1].down && !borderClicked && !nodeClicked){
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

            container.readerNodes.forEach((k,v)->{
                float nodeX = container.x - 2,nodeY = container.y + (container.readerNodes.keySet().stream().toList().indexOf(k)-1) * 24 + 96;
                if(Mouse.mouseButtons[MouseEvent.BUTTON1].down && !nodeClicked){
                    if(Collisions.pointToCircle(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY, nodeX,nodeY,8)){
                        nodeClicked = true;
                        clickedNode = v;
                        if(clickedNode != null){
                            for (int i = 0; i < connections.size(); i++) {
                                if(connections.get(i).getFirst() == clickedNode || connections.get(i).getSecond() == clickedNode){
                                    connections.remove(i);
                                }
                            }
                        }
                    }
                }
                if(!Mouse.mouseButtons[MouseEvent.BUTTON1].down && nodeClicked){
                    if(clickedNode != null){
                        connections.add(new Pair<>(clickedNode,v));
                        clickedNode = null;
                    }
                    nodeClicked = false;
                }

                if(nodeClicked && v == clickedNode){
                    RenderUtils.DrawLine(nodeX+2,nodeY+2, ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY, Color.RED);
                }
            });
            container.writerNodes.forEach((k,v)->{
                float nodeX = container.x + container.sx - 2,nodeY = container.y + (container.writerNodes.keySet().stream().toList().indexOf(k)-1) * 24 + 96;

                if(Mouse.mouseButtons[MouseEvent.BUTTON1].down && !nodeClicked){
                    if(Collisions.pointToCircle(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY, nodeX,nodeY,8)){
                        nodeClicked = true;
                        clickedNode = v;
                        if(clickedNode != null){
                            for (int i = 0; i < connections.size(); i++) {
                                if(connections.get(i).getFirst() == clickedNode || connections.get(i).getSecond() == clickedNode){
                                    connections.remove(i);
                                }
                            }
                        }
                    }
                }
                if(!Mouse.mouseButtons[MouseEvent.BUTTON1].down && nodeClicked){
                    if(Collisions.pointToCircle(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY, nodeX,nodeY,4)) {
                        if (clickedNode != null) {
                            connections.add(new Pair<>(v,clickedNode));
                            clickedNode = null;
                        }
                    }
                    nodeClicked = false;
                }
                if(nodeClicked && v == clickedNode){
                    RenderUtils.DrawLine(nodeX+2,nodeY+2, ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY,Color.GREEN);
                }
            });
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
