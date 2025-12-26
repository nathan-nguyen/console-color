package com.noiprocs.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ModelChunk implements Serializable {
  public static final int CHUNK_HEIGHT = 120;
  public static final int CHUNK_WIDTH = 120;

  private final int posX, posY;
  public final Map<String, Model> map = new HashMap<>();

  public ModelChunk(int posX, int posY) {
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

  protected String getChunkId() {
    return posX + "_" + posY;
  }
}
