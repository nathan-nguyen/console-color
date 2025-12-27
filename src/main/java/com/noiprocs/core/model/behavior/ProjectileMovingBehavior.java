package com.noiprocs.core.model.behavior;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.MobModel;
import com.noiprocs.core.model.mob.projectile.ProjectileModel;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProjectileMovingBehavior extends MovingBehavior {
  private static final Logger logger = LogManager.getLogger(ProjectileMovingBehavior.class);

  @Override
  public void update(Model model) {
    super.update(model);

    ProjectileModel projectileModel = (ProjectileModel) model;
    projectileModel.updateTtl(-1);
  }

  @Override
  protected boolean isNextMoveValid(MobModel mobModel, Vector3D nextPosition) {
    List<Model> collidingModels =
        GameContext.get().hitboxManager.getCollidingModel(mobModel, nextPosition);
    if (collidingModels.isEmpty()) return true;

    Model model = collidingModels.get(0);
    logger.info("Hit {}", model);
    if (model instanceof InteractiveInterface) {
      ((InteractiveInterface) model).interact(mobModel, null);
    }
    mobModel.destroy();
    return false;
  }
}
