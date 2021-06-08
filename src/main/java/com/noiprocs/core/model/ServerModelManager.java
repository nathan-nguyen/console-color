package com.noiprocs.core.model;

import com.noiprocs.core.model.mob.character.PlayerModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class acts as a Wrapper for all Server Models, used to broadcast to Clients for synchronization
 */
public class ServerModelManager implements Serializable {
    public Map<String, ModelChunkManager> modelMap = new HashMap<>();
    public Map<String, ModelChunkManager> chunkMap = new HashMap<>();
    public Map<String, PlayerModel> playerModelMap = new HashMap<>();
}
