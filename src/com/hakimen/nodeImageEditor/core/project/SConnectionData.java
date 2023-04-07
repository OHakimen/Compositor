package com.hakimen.nodeImageEditor.core.project;

import com.hakimen.nodeImageEditor.utils.Pair;

import java.io.Serializable;
import java.util.UUID;

public class SConnectionData implements Serializable {
    Pair<UUID, UUID> containers;
    Pair<Pair<String, Integer>,Pair<String, Integer>> indexByType;


    public SConnectionData(Pair<UUID, UUID> containers, Pair<Pair<String, Integer>, Pair<String, Integer>> indexByType) {
        this.containers = containers;
        this.indexByType = indexByType;
    }

    public Pair<UUID, UUID> getContainers() {
        return containers;
    }

    public void setContainers(Pair<UUID, UUID> containers) {
        this.containers = containers;
    }

    public Pair<Pair<String, Integer>, Pair<String, Integer>> getIndexByType() {
        return indexByType;
    }

    public void setIndexByType(Pair<Pair<String, Integer>, Pair<String, Integer>> indexByType) {
        this.indexByType = indexByType;
    }
}
