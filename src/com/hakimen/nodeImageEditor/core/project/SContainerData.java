package com.hakimen.nodeImageEditor.core.project;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class SContainerData implements Serializable {
    float x,y;
    UUID uuid;
    Class<? extends NodeContainer> type;

    public Map<String,Node<?>> writerNodes = new LinkedHashMap<>();
    public Map<String,Node<?>> readerNodes = new LinkedHashMap<>();

    public Map<String, Node<?>> getReaderNodes() {
        return readerNodes;
    }

    public SContainerData setReaderNodes(Map<String, Node<?>> readerNodes) {
        this.readerNodes = readerNodes;
        return this;
    }

    public Map<String, Node<?>> getWriterNodes() {
        return writerNodes;
    }

    public SContainerData setWriterNodes(Map<String, Node<?>> writerNodes) {
        this.writerNodes = writerNodes;
        return this;
    }

    public SContainerData(float x, float y, UUID uuid, Class<? extends NodeContainer> type) {
        this.x = x;
        this.y = y;
        this.uuid = uuid;
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Class<?extends NodeContainer> getType() {
        return type;
    }

    public void setType(Class<?extends NodeContainer> type) {
        this.type = type;
    }
}
