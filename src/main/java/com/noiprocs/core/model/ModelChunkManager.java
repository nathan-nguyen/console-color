package com.noiprocs.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ModelChunkManager implements Serializable {
    protected static final int CHUNK_HEIGHT = 120;
    protected static final int CHUNK_WIDTH = 120;

    private final int posX, posY;
    public final Map<String, Model> map = new HashMap<>();

    public ModelChunkManager(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    protected Model get(String id) {
        return map.get(id);
    }

    protected void add(String id, Model model) {
        map.put(id, model);
    }

    protected void remove(String id) {
        map.remove(id);
    }

    protected Iterable<Model> getAllModel() {
        return map.values();
    }

    protected String getChunkId() {
        return posX + "_" + posY;
    }
}
