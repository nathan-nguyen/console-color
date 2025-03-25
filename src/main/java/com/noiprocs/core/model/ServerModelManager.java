package com.noiprocs.core.model;

import com.noiprocs.core.model.mob.character.PlayerModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class acts as a Wrapper for all Server Models, used to broadcast to Clients for synchronization
 */
public class ServerModelManager implements Serializable {
    /**
     * Map of: ModelId -> ModelChunk
     * This map is used to add/remove models in each chunk when they are moving.
     * This map is only used for managing models and NOT used for rendering.
     */
    public Map<String, ModelChunk> modelMap = new HashMap<>();

    /**
     * Map of: ChunkId (Format: "{posX}_{posY}") -> ModelChunk
     * For rendering in Client (Server does not render anything),
     * all models in player's surrounded chunks are rendered.
     */
    public Map<String, ModelChunk> chunkMap = new HashMap<>();

    public Map<String, PlayerModel> playerModelMap = new HashMap<>();
}
