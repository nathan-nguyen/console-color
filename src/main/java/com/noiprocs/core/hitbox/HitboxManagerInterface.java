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

    boolean isColliding(Model m1, Model m2);

    /**
     * Get list of colliding models if providing model is moved to position (nextX, nextY)
     *
     * @param model: Checking model
     * @param nextX: Destination posX
     * @param nextY: Destination posX
     * @return List of colliding models.
     */
    List<Model> getCollidingModel(Model model, int nextX, int nextY);

    /**
     * Get list of colliding models, providing direction, distance to original hitbox, and size of checking hitbox
     * @param model: Checking model
     * @param directionX: DirectionX to checking hitbox
     * @param directionY: DirectionY to checking hitbox
     * @param dx: DistanceX to from original hitbox to checking hitbox
     * @param dy: DistanceY to from original hitbox to checking hitbox
     * @param height: Checking hitbox height
     * @param width: Checking hitbox width
     * @return List of colliding models.
     */
    List<Model> getCollidingModel(Model model, int directionX, int directionY, int dx, int dy, int height, int width);

    /**
     * Get spawn point for provided model
     *
     * @param model      : Model of spawner.
     * @param directionX : Spawn directionX
     * @param directionY : Spawn directionY
     * @return array size of 2: {spawnPointX, spawnPointY}
     */
    int[] getSpawnPoint(Model model, int directionX, int directionY);
}
