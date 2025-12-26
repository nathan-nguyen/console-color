package com.noiprocs.core.hitbox;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.Model;
import java.util.List;

public interface HitboxManagerInterface {
  void setGameContext(GameContext gameContext);

  /**
   * Check whether the model could be relocated to provided position.
   *
   * @param model: Checking model
   * @param nextPosition: Next position
   * @return True if model could be relocated.
   */
  boolean isValid(Model model, Vector3D nextPosition);

  boolean isColliding(Model m1, Model m2);

  /**
   * Get list of colliding models if providing model is moved to position (nextX, nextY)
   *
   * @param model: Checking model
   * @param nextPosition: Destination position
   * @return List of colliding models.
   */
  List<Model> getCollidingModel(Model model, Vector3D nextPosition);

  /**
   * Get list of colliding models, providing direction, distance to original hitbox, and size of
   * checking hitbox
   *
   * @param model: Checking model
   * @param direction: Direction vector of checking hitbox
   * @param distance: Distance vector to from original hitbox to checking hitbox
   * @param dimension: Checking hitbox dimension
   * @return List of colliding models.
   */
  List<Model> getCollidingModel(
      Model model, Vector3D direction, Vector3D distance, Vector3D dimension);

  /**
   * Get spawn point for provided model, this is usually used for projectile spawning to avoid
   * colliding with the spawner itself. This spawn point is relative to the model's position and
   * depends on the hitbox size.
   *
   * @param model : Model of spawner.
   * @param direction : Direction vector where the projectile is moving to.
   * @return Spawn point vector relative to the model's position.
   */
  Vector3D getSpawnPoint(Model model, Vector3D direction);
}
