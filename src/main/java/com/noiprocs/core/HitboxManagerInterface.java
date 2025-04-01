package com.noiprocs.core;

import com.noiprocs.core.model.Model;

public interface HitboxManagerInterface {
    void setGameContext(GameContext gameContext);

    /**
     * Check whether the model could be relocated to provided position.
     *
     * @param model: Checking model
     * @param nextX: Next position in X coordinate.
     * @param nextY: Next position in Y coordinate.
     * @return True if model could be relocated.
     */
    boolean isValid(Model model, int nextX, int nextY);

    Model getModel(Model targetModel, int directionX, int directionY);
}
