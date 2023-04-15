package com.hakimen.nodeImageEditor.core;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

public class Node<T> implements Serializable{
    boolean isReader;
    T value;

    Color nodeColor;
    UUID container;

    public UUID getContainer() {
        return container;
    }

    public void setContainer(UUID container) {
        this.container = container;
    }

    public Node(UUID container, boolean isReader, T value) {
        this.container = container;
        this.isReader = isReader;
        this.value = value;
    }

    public Node(UUID container,boolean isReader) {
        this.container = container;
        this.isReader = isReader;
    }

    public T getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = (T) value;
    }

    public boolean isReader() {
        return isReader;
    }

    public void setReader(boolean reader) {
        isReader = reader;
    }

    public Color getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(Color nodeColor) {
        this.nodeColor = nodeColor;
    }
}
