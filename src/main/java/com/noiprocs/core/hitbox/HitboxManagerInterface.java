package com.noiprocs.core.hitbox;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;

import java.util.List;

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

    List<Model> getCollidingModel(Model model);
    List<Model> getCollidingModel(Model model, int directionX, int directionY, int dx, int dy, int height, int width);
}
