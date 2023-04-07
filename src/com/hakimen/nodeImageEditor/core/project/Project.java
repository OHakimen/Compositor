package com.hakimen.nodeImageEditor.core.project;

import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.*;
import com.hakimen.nodeImageEditor.utils.Pair;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class Project implements Serializable {
    ArrayList<SContainerData> containersData = new ArrayList<>();
    ArrayList<SConnectionData> consData = new ArrayList<>();

    public Project(Map<UUID, NodeContainer> containers, ArrayList<Pair<Node<?>, Node<?>>> connections) {
        containers.forEach((k,v)->{

            var containerInstance = new SContainerData(v.x, v.y, k, v.getClass());
            var writers = v.writerNodes;
            Node[] writerNodeArr = new Node[writers.size()];
            String[] writerStrArr = new String[writers.size()];
            writers.values().stream().toList().toArray(writerNodeArr);
            writers.keySet().stream().toList().toArray(writerStrArr);

            for (int i = 0; i < writerStrArr.length; i++) {
                if(writerNodeArr[i] instanceof ImageNode){
                    containerInstance.writerNodes.put(writerStrArr[i],new PlaceholderNode(v.uuid,false));
                }else{
                    containerInstance.writerNodes.put(writerStrArr[i],writerNodeArr[i]);
                }
            }

            var reader =v.readerNodes;
            Node[] readerNodeArr = new Node[reader.size()];
            String[] readerStrArr = new String[reader.size()];
            reader.values().stream().toList().toArray(readerNodeArr);
            reader.keySet().stream().toList().toArray(readerStrArr);

            for (int i = 0; i < readerStrArr.length; i++) {
                if(readerNodeArr[i] instanceof ImageNode){
                    containerInstance.readerNodes.put(readerStrArr[i],new PlaceholderNode(v.uuid,false));
                }else{
                    containerInstance.readerNodes.put(readerStrArr[i],readerNodeArr[i]);
                }
            }
            containersData.add(containerInstance);
        });
            

        for (Pair<Node<?>, Node<?>> connection : connections) {

            var containerFirst = containers.get(connection.getFirst().getContainer());
            var containerSecond = containers.get(connection.getSecond().getContainer());
            var nodeFirst = connection.getFirst();
            var nodeSecond = connection.getSecond();


            var firstNodeType = nodeFirst.isReader() ? "reader" : "writer";
            var secondNodeType = nodeSecond.isReader() ? "reader" : "writer";
            var firstNodeIndex = firstNodeType.equals("reader") ? containerFirst.readerNodes.values().stream().toList().indexOf(nodeFirst) : containerFirst.writerNodes.values().stream().toList().indexOf(nodeFirst);
            var secondNodeIndex = secondNodeType.equals("reader") ? containerSecond.readerNodes.values().stream().toList().indexOf(nodeSecond) : containerSecond.writerNodes.values().stream().toList().indexOf(nodeSecond);

            consData.add(new SConnectionData(new Pair<>(containerFirst.uuid,containerSecond.uuid), new Pair<>(new Pair<>(firstNodeType,firstNodeIndex), new Pair<>(secondNodeType,secondNodeIndex))));
        }
    }

    public Project() {
        consData = new ArrayList<>();
        containersData = new ArrayList<>();
    }

    public void serialize(File f){
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos
                    = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Project deserialize(File f){
        try {
            FileInputStream fileInputStream = new FileInputStream(f);
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            var project = (Project)objectInputStream.readObject();
            objectInputStream.close();
            return project;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadProject(NodeEditor editor){
        var containers = new LinkedHashMap<UUID,NodeContainer>();
        var connections = new ArrayList<Pair<Node<?>,Node<?>>>();
        containersData.forEach((conData)->{
            try{
                var temp = (NodeContainer) (conData.type.getConstructors()[0].newInstance(conData.x,conData.y));
                NodeContainer instance = temp.setUuid(conData.uuid);
                conData.writerNodes.forEach((k,v)->{
                    if(v instanceof PlaceholderNode){
                        instance.writerNodes.put(k, new ImageNode(instance.uuid,false, new BufferedImage(1,1,2)));
                    }else{
                        instance.writerNodes.put(k,v);
                    }
                });
                conData.readerNodes.forEach((k,v)->{
                    if(v instanceof PlaceholderNode){
                        instance.readerNodes.put(k, new ImageNode(instance.uuid,true, new BufferedImage(1,1,2)));
                    }else{
                        instance.readerNodes.put(k,v);
                    }
                });
                containers.put(instance.uuid,instance);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        consData.forEach((conData)->{
            var con = conData.getContainers();
            NodeContainer first = null;
            NodeContainer second = null;
            for(int i = 0; i < containers.size(); i++) {
                var key = containers.keySet().stream().toList().get(i);
                var value = containers.values().stream().toList().get(i);
                if(key != null){
                    if(key.equals(con.getFirst()) && first == null && value.getUuid() == con.getFirst()){
                        first = value;
                    }
                    if(key.equals(con.getSecond()) && second == null && value.getUuid() == con.getSecond()){
                        second = value;
                    }
                }
            }

            var firstPair = conData.indexByType.getFirst();
            var secondPair = conData.indexByType.getSecond();

            var firstType = firstPair.getFirst();
            var firstIndex = firstPair.getSecond();

            var secondType = secondPair.getFirst();
            var secondIndex = secondPair.getSecond();

            if(first != null && second != null) {
                Node<?> firstRef = (firstType.equals("reader")) ? first.readerNodes.values().stream().toList().get(firstIndex) : first.writerNodes.values().stream().toList().get(firstIndex);
                Node<?> secondRef = (secondType.equals("reader")) ? second.readerNodes.values().stream().toList().get(secondIndex) : second.writerNodes.values().stream().toList().get(secondIndex);
                connections.add(new Pair<>(firstRef,secondRef));
            }
        });
        editor.containers = containers;
        editor.connections = connections;

    }
    public ArrayList<SContainerData> getContainersData() {
        return containersData;
    }

    public void setContainersData(ArrayList<SContainerData> containersData) {
        this.containersData = containersData;
    }

    public ArrayList<SConnectionData> getConsData() {
        return consData;
    }

    public void setConsData(ArrayList<SConnectionData> consData) {
        this.consData = consData;
    }
}
