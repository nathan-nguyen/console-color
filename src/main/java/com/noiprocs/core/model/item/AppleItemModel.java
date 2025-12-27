package com.noiprocs.core.model.item;

import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.CotMobModel;
import com.noiprocs.core.model.mob.character.Humanoid;

public class AppleItemModel extends ItemModel {
  public AppleItemModel(Vector3D position) {
    super(position, AppleItem.class);
  }

  @Override
  public void interact(Model model, Item item) {
    if (model instanceof Humanoid) {
      this.addToModelInventory((Humanoid) model);
    } else if (model instanceof CotMobModel) {
      ((CotMobModel) model).updateHealth(AppleItem.HEALTH_RESTORE);
      this.destroy();
    }
  }
}
