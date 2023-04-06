package com.hakimen.nodeImageEditor.core.project;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.utils.Pair;

import java.io.*;
import java.util.ArrayList;

public class ProjectInstance implements Serializable {

    public ArrayList<NodeContainer> containers = new ArrayList<>();
    public ArrayList<Pair<Node<?>,Node<?>>> connections = new ArrayList<>();

    public ArrayList<NodeContainer> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<NodeContainer> containers) {
        this.containers = containers;
    }

    public ArrayList<Pair<Node<?>, Node<?>>> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Pair<Node<?>, Node<?>>> connections) {
        this.connections = connections;
    }

    public ProjectInstance() {
    }

    public ProjectInstance(ArrayList<NodeContainer> containers, ArrayList<Pair<Node<?>, Node<?>>> connections) {
        this.containers = containers;
        this.connections = connections;
    }

    public void serialize(File name)
            throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(name);
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public ProjectInstance deserialize(File name)
            throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(name);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        var instance = (ProjectInstance)objectInputStream.readObject();
        objectInputStream.close();
        return instance;
    }
}
