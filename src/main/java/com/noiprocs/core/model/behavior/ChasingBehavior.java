package com.noiprocs.core.model.behavior;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Direction;
import com.noiprocs.core.common.Helper;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.hitbox.HitboxManagerInterface;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.MobModel;
import java.util.List;
import java.util.Optional;

public class ChasingBehavior implements BehaviorInterface {
  private final Class<?> targetClass;
  private final Vector3D searchDistance;
  private final Vector3D searchHitboxDimension;

  public ChasingBehavior(
      final Class<?> targetClass, Vector3D searchDistance, Vector3D searchHitboxDimension) {
    this.targetClass = targetClass;
    this.searchDistance = searchDistance;
    this.searchHitboxDimension = searchHitboxDimension;
  }

  @Override
  public void update(Model model) {
    MobModel mobModel = (MobModel) model;

    HitboxManagerInterface hitboxManager = GameContext.get().hitboxManager;
    // Get list of models in circle radius 20, find nearest AppleItemModel
    List<Model> surroundedModels =
        hitboxManager.getCollidingModel(
            model, Vector3D.ZERO, searchDistance, searchHitboxDimension);
    Optional<Model> nearestTargetOption =
        surroundedModels.stream()
            .filter(surroundModel -> targetClass.isInstance(surroundModel))
            .min(
                (u, v) ->
                    Integer.compare(
                        u.position.distanceTo(model.position),
                        v.position.distanceTo(model.position)));

    if (nearestTargetOption.isPresent()) {
      Model targetModel = nearestTargetOption.get();
      if (hitboxManager.isColliding(model, targetModel)) {
        ((InteractiveInterface) targetModel).interact(model, null);
      } else {
        mobModel.setMovingDirection(this.getFollowDirection(model, targetModel.position));
      }
    }
  }

  private Vector3D getFollowDirection(Model model, Vector3D targetPosition) {
    int up = Math.max(model.position.x - targetPosition.x, 0);
    int right = Math.max(targetPosition.y - model.position.y, 0);
    int down = Math.max(targetPosition.x - model.position.x, 0);
    int left = Math.max(model.position.y - targetPosition.y, 0);
    int v = Helper.random.nextInt(up + right + down + left);
    if (v < up) return Direction.NORTH;
    if (v < up + right) return Direction.EAST;
    if (v < up + right + down) return Direction.SOUTH;
    return Direction.WEST;
  }
}
