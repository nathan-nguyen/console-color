package com.noiprocs.core.model.item;

import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.CotMobModel;

public class AppleItemModel extends ItemModel {
  public AppleItemModel(Vector3D position) {
    super(position, AppleItem.class);
  }

  @Override
  public void interact(Model model, Item item) {
    if (model instanceof CotMobModel) {
      this.destroy();
    }
  }
}
