package com.noiprocs.ui.console.hitbox;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.hitbox.HitboxManagerInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.ItemModel;
import com.noiprocs.core.model.mob.projectile.ProjectileModel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleHitboxManager implements HitboxManagerInterface {
  private static final Logger logger = LogManager.getLogger(ConsoleHitboxManager.class);

  private GameContext gameContext;
  private final Map<String, Hitbox> hitboxMap = new ConcurrentHashMap<>();

  public void setGameContext(GameContext gameContext) {
    this.gameContext = gameContext;
  }

  private Hitbox getHitbox(Model model) {
    String className =
        (model instanceof ItemModel)
            ? ((ItemModel) model).itemClass.getName()
            : model.getClass().getName();
    return hitboxMap.computeIfAbsent(
        className, key -> ConsoleHitboxFactory.generateHitbox(className));
  }

  // Check whether 2 rectangles overlapped, providing top-left and bottom-right
  // positions
  // Note: X is increasing from top to bottom, Y is increasing from left to right.
  public static boolean isOverlapped(
      Vector3D topLeft1, Vector3D bottomRight1, Vector3D topLeft2, Vector3D bottomRight2) {
    return topLeft1.x < bottomRight2.x
        && bottomRight1.x > topLeft2.x
        && topLeft1.y < bottomRight2.y
        && bottomRight1.y > topLeft2.y;
  }

  /**
   * Check whether the model could be relocated to provided position.
   *
   * @param model: Checking model
   * @param nextPosition: Next position
   * @return True if model could be relocated.
   */
  @Override
  public boolean isValid(Model model, Vector3D nextPosition) {
    Hitbox modelHitbox = getHitbox(model);
    Vector3D end = nextPosition.add(modelHitbox.getDimension(model));
    Model projectileSpawner =
        model instanceof ProjectileModel ? ((ProjectileModel) model).getSpawner() : null;

    return gameContext.modelManager.getSurroundedChunk(model).stream()
        .flatMap(modelChunk -> modelChunk.map.values().stream())
        .noneMatch(
            surroundedModel -> {
              if (surroundedModel == model || surroundedModel == projectileSpawner) {
                return false;
              }

              Hitbox surroundedHitbox = getHitbox(surroundedModel);
              if ((modelHitbox.maskBit & surroundedHitbox.categoryBit) == 0) {
                return false;
              }

              Vector3D surroundedHitboxDimension = surroundedHitbox.getDimension(surroundedModel);
              return isOverlapped(
                  nextPosition,
                  end,
                  surroundedModel.position,
                  surroundedModel.position.add(surroundedHitboxDimension));
            });
  }

  /**
   * Check whether 2 models are colliding.
   *
   * @param m1: First model
   * @param m2: Second model
   * @return True if 2 models are colliding.
   */
  @Override
  public boolean isColliding(Model m1, Model m2) {
    Hitbox hb1 = getHitbox(m1), hb2 = getHitbox(m2);
    Vector3D dimension1 = hb1.getDimension(m1);
    Vector3D dimension2 = hb2.getDimension(m2);
    return isOverlapped(
        m1.position, m1.position.add(dimension1), m2.position, m2.position.add(dimension2));
  }

  /**
   * Get list of colliding models if providing model is moved to position (nextX, nextY)
   *
   * @param model: Checking model
   * @param nextPosition: Destination position
   * @return List of colliding models.
   */
  @Override
  public List<Model> getCollidingModel(Model model, Vector3D nextPosition) {
    Hitbox targetHitbox = getHitbox(model);
    Vector3D dimension = targetHitbox.getDimension(model);
    return getCollidingModel(model, nextPosition, dimension);
  }

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
  @Override
  // Facing right: directionY >= 0
  // Facing left: directionY < 0
  public List<Model> getCollidingModel(
      Model model, Vector3D direction, Vector3D distance, Vector3D dimension) {
    Hitbox targetHitbox = getHitbox(model);
    Vector3D targetHitboxDimension = targetHitbox.getDimension(model);
    Vector3D nextPosition =
        model.position.add(
            new Vector3D(
                distance.x,
                distance.y + (direction.y >= 0 ? targetHitboxDimension.y : -dimension.y),
                0));
    return getCollidingModel(model, nextPosition, dimension);
  }

  /**
   * Get spawn point for provided model, this is usually used for projectile spawning to avoid
   * colliding with the spawner itself. This spawn point is relative to the model's position and
   * depends on the hitbox size.
   *
   * @param model : Model of spawner.
   * @param direction : Direction vector where the projectile is moving to.
   * @return Spawn point vector relative to the model's position.
   */
  @Override
  public Vector3D getSpawnPoint(Model model, Vector3D direction) {
    Hitbox targetHitbox = getHitbox(model);
    return targetHitbox.getSpawnPoint(direction);
  }

  private List<Model> getCollidingModel(Model model, Vector3D nextPosition, Vector3D dimension) {
    Model projectileSpawner =
        model instanceof ProjectileModel ? ((ProjectileModel) model).getSpawner() : null;
    Vector3D end = nextPosition.add(dimension);

    return gameContext.modelManager.getSurroundedChunk(model).stream()
        .flatMap(modelChunk -> modelChunk.map.values().stream())
        .filter(
            surroundedModel -> {
              if (surroundedModel == model || surroundedModel == projectileSpawner) return false;

              Hitbox surroundedHitbox = getHitbox(surroundedModel);
              Vector3D surroundedDimension = surroundedHitbox.getDimension(surroundedModel);
              return isOverlapped(
                  nextPosition,
                  end,
                  surroundedModel.position,
                  surroundedModel.position.add(surroundedDimension));
            })
        .collect(Collectors.toList());
  }
}
