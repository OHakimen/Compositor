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
import com.hakimen.nodeImageEditor.core.notifications.notification.ErrorNotification;
import com.hakimen.nodeImageEditor.core.notifications.NotificationHandler;
import com.hakimen.nodeImageEditor.core.notifications.notification.SuccessNotification;
import com.hakimen.nodeImageEditor.core.project.Project;
import com.hakimen.nodeImageEditor.utils.Collisions;
import com.hakimen.nodeImageEditor.utils.MenuUtils;
import com.hakimen.nodeImageEditor.utils.Pair;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.io.File;
import java.util.*;

public class NodeEditor {

    public static final int NOTIFY_SHORT = 60 * 5;
    public static final int NOTIFY_NORMAL = 60 * 10;
    public static final int NOTIFY_BIG = 60 * 15;

    public static NotificationHandler handler = new NotificationHandler(0.25f);


    static int NodeConnectionRemoveKey = KeyEvent.VK_DELETE;
    MenuBar menuBar = new MenuBar();
    JFileChooser chooser = new JFileChooser("/");

    Project project = new Project();
    String baseTitle = Window.frame.getTitle();
    String title = "";
    public NodeEditor(){
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.toString().contains(".cnp") | f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "CNP File";
            }
        });

        Menu nodeMenus = new Menu("Nodes");
        MenuUtils.makeMenu(nodeMenus,this);
        Menu fileMenus = new Menu("File");
        var save = new MenuItem("Save Project");
        save.addActionListener((a)->{
            project = new Project(containers,connections);
            chooser.showSaveDialog(null);
            if(chooser.getSelectedFile() == null){
                handler.push(new ErrorNotification("Couldn't save project","No file provided",NOTIFY_SHORT));
            }
            if (!chooser.getSelectedFile().getAbsolutePath().endsWith(".cnp")) {
                File f = new File(chooser.getSelectedFile().getAbsolutePath()+".cnp");
                project.serialize(f);
                title = baseTitle + " - " + f.getName();
            }else{
                project.serialize(chooser.getSelectedFile());
                title = baseTitle + " - " + chooser.getSelectedFile().getName();
            }
            handler.push(new SuccessNotification("Project Saved",chooser.getSelectedFile().getName(),NOTIFY_SHORT));
            Window.frame.setTitle(title);
        });
        fileMenus.add(save);
        var open = new MenuItem("Open Project");

        open.addActionListener((a)->{
            chooser.showOpenDialog(null);
            if(chooser.getSelectedFile() == null){
                handler.push(new ErrorNotification("Couldn't load Project","No file provided",NOTIFY_SHORT));
            }
            if (!chooser.getSelectedFile().getAbsolutePath().endsWith(".cnp")) {
                File f = new File(chooser.getSelectedFile().getAbsolutePath()+".cnp");
                project = Project.deserialize(f);
                title = baseTitle + " - " + f.getName();
            }else{
                project = Project.deserialize(chooser.getSelectedFile());
                title = baseTitle + " - " + chooser.getSelectedFile().getName();
            }
            project.loadProject(this);
            handler.push(new SuccessNotification("Project Loaded",chooser.getSelectedFile().getName(),NOTIFY_SHORT));

            Window.frame.setTitle(title);
        });

        fileMenus.add(save);
        fileMenus.add(open);

        menuBar.add(fileMenus);
        menuBar.add(nodeMenus);
        Window.frame.setMenuBar(menuBar);
    }

    public Map<UUID,NodeContainer> containers = new LinkedHashMap<>();
    public boolean borderClicked;

    public Node<?> currentNode;
    public NodeContainer clickedContainer;
    public ArrayList<Pair<Node<?>,Node<?>>> connections = new ArrayList<>();
    public void update(){

        for(int j = 0; j < containers.size(); j++){
            NodeContainer container = containers.values().stream().toList().get(j);
            if(Mouse.mouseButtons[MouseEvent.BUTTON1].pressed && Collisions.pointToRect(ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY,container.x + container.sx - 24,container.y+12,16,16)){
                containers.remove(container.uuid);
            }
            if(Keyboard.keys[NodeConnectionRemoveKey].pressed && Collisions.pointToRect(ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY,container.x,container.y,container.sx,40)){
                for (int i = 0; i < connections.size(); i++) {
                    var con = connections.get(i);
                    if(con.getFirst().getContainer().equals(container.uuid)){
                        connections.remove(i);
                    }else if(con.getSecond().getContainer().equals(container.uuid)){
                        connections.remove(i);
                    }
                }
                containers.remove(container.uuid);
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

                if(!borderClicked && Mouse.mouseButtons[MouseEvent.BUTTON1].down && Collisions.pointToCircle(ViewTransformer.transformedMouseX, ViewTransformer.transformedMouseY,calcX,calcY,4)){
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
        if(currentNode!= null && containers.get(currentNode.getContainer()) != null){
            float fnodeX = 0,fnodeY = 0;
            if(currentNode.isReader()){
                fnodeX = containers.get(currentNode.getContainer()).x - 2;
                fnodeY = containers.get(currentNode.getContainer()).y + (containers.get(currentNode.getContainer()).readerNodes.values().stream().toList().indexOf(currentNode)-1) * 24 + 96;

                float dist = (float)Math.sqrt(Math.pow((fnodeX-ViewTransformer.transformedMouseX),2) + Math.pow((fnodeY-ViewTransformer.transformedMouseY),2)) / 2;

                var shape = new CubicCurve2D.Float(fnodeX+2,fnodeY+2,fnodeX-dist,fnodeY+2,ViewTransformer.transformedMouseX + dist,ViewTransformer.transformedMouseY+2,ViewTransformer.transformedMouseX+2,ViewTransformer.transformedMouseY+2);
                RenderUtils.DrawShape(shape,currentNode.getNodeColor());
            }else{
                fnodeX = containers.get(currentNode.getContainer()).x + containers.get(currentNode.getContainer()).sx - 2;
                fnodeY = containers.get(currentNode.getContainer()).y + (containers.get(currentNode.getContainer()).writerNodes.values().stream().toList().indexOf(currentNode) - 1) * 24+ 96;

                float dist = (float)Math.sqrt(Math.pow((fnodeX-ViewTransformer.transformedMouseX),2) + Math.pow((fnodeY-ViewTransformer.transformedMouseY),2)) / 2;

                var shape = new CubicCurve2D.Float(fnodeX+2,fnodeY+2,fnodeX+dist,fnodeY+2,ViewTransformer.transformedMouseX - dist,ViewTransformer.transformedMouseY+2,ViewTransformer.transformedMouseX+2,ViewTransformer.transformedMouseY+2);
                RenderUtils.DrawShape(shape,currentNode.getNodeColor());
            }
        }
        for (var pair:connections) {
            float fnodeX = 0,fnodeY = 0;
            float snodeX = 0,snodeY = 0;

            if(containers.get(pair.getFirst().getContainer()) != null){
                if(!pair.getFirst().isReader()){
                    fnodeX = containers.get(pair.getFirst().getContainer()).x + containers.get(pair.getFirst().getContainer()).sx - 2;
                    fnodeY = containers.get(pair.getFirst().getContainer()).y + (containers.get(pair.getFirst().getContainer()).writerNodes.values().stream().toList().indexOf(pair.getFirst()) - 1) * 24+ 96;

                }else{
                    fnodeX = containers.get(pair.getFirst().getContainer()).x - 2;
                    fnodeY = containers.get(pair.getFirst().getContainer()).y + (containers.get(pair.getFirst().getContainer()).readerNodes.values().stream().toList().indexOf(pair.getFirst())-1) * 24 + 96;

                }
            }

            if(containers.get(pair.getSecond().getContainer()) != null){
                if (!pair.getSecond().isReader()) {
                    snodeX = containers.get(pair.getSecond().getContainer()).x + containers.get(pair.getSecond().getContainer()).sx - 2;
                    snodeY = containers.get(pair.getSecond().getContainer()).y + (containers.get(pair.getSecond().getContainer()).writerNodes.values().stream().toList().indexOf(pair.getSecond()) - 1) * 24 + 96;
                } else {
                    snodeX = containers.get(pair.getSecond().getContainer()).x - 2;
                    snodeY = containers.get(pair.getSecond().getContainer()).y + (containers.get(pair.getSecond().getContainer()).readerNodes.values().stream().toList().indexOf(pair.getSecond()) - 1) * 24 + 96;
                }
            }
            float dist = (float)Math.sqrt(Math.pow((fnodeX-snodeX),2) + Math.pow((fnodeY-snodeY),2)) / 2;
            if(pair.getFirst().isReader()){
                var shape = new CubicCurve2D.Float(fnodeX+2,fnodeY+2,fnodeX-dist,fnodeY+2,snodeX + dist,snodeY+2,snodeX+2,snodeY+2);
                RenderUtils.DrawShape(shape,pair.getFirst().getNodeColor());
            }else if(pair.getSecond().isReader()){
                var shape = new CubicCurve2D.Float(fnodeX+2,fnodeY+2,fnodeX+dist,fnodeY+2,snodeX - dist,snodeY+2,snodeX+2,snodeY+2);
                RenderUtils.DrawShape(shape,pair.getFirst().getNodeColor());
            }

        }
        for (int i = 0; i < containers.size(); i++) {
            var container = containers.values().stream().toList().get(i);
            container.render();
        }
    }
}
