package com.hakimen.nodeImageEditor.core.project;

import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.utils.Pair;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Project implements Serializable {
    ArrayList<SContainerData> containersData = new ArrayList<>();
    ArrayList<SConnectionData> consData = new ArrayList<>();

    public Project(ArrayList<Pair<UUID, NodeContainer>> containers, ArrayList<Pair<Node<?>, Node<?>>> connections) {
        for (Pair<UUID, NodeContainer> uuidNodeContainerPair : containers) {
            var containerUUID = uuidNodeContainerPair.getFirst();
            var container = uuidNodeContainerPair.getSecond();

            var containerInstance = new SContainerData(container.x, container.y, containerUUID, container.getClass());
            var writers = container.writerNodes;
            for (int i = 0; i < writers.values().size(); i++) {
                var writerValue = writers.values().stream().toList().get(i);
                var writerKey = writers.keySet().stream().toList().get(i);
                if(!(writerValue instanceof ImageNode)){
                    containerInstance.writerNodes.put(writerKey,writerValue);
                }
            }
            containersData.add(containerInstance);
        }

        for (Pair<Node<?>, Node<?>> connection : connections) {
            var containerFirst = connection.getFirst().getContainer();
            var containerSecond = connection.getSecond().getContainer();
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
        var containers = new ArrayList<Pair<UUID,NodeContainer>>();
        var connections = new ArrayList<Pair<Node<?>,Node<?>>>();
        containersData.forEach((conData)->{
            try{
                var instance = (NodeContainer) conData.type.getConstructors()[0].newInstance(conData.x,conData.y);
                instance.setUuid(conData.uuid);
                instance.writerNodes = conData.writerNodes;
                containers.add(new Pair<>(instance.uuid,instance));
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        consData.forEach((conData)->{
            var con = conData.getContainers();
            NodeContainer first = null;
            NodeContainer second = null;
            for(int i = 0; i < containers.size(); i++) {
                var cont = containers.get(i);
                if(cont.getFirst() != null){
                    if(cont.getFirst().equals(con.getFirst()) && first == null && cont.getSecond().getUuid() == con.getFirst()){
                        first = cont.getSecond();
                    }
                    if(cont.getFirst().equals(con.getSecond()) && second == null && cont.getSecond().getUuid() == con.getSecond()){
                        second = cont.getSecond();
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
