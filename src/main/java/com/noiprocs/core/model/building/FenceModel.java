package com.noiprocs.core.model.building;

import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.FenceItem;
import com.noiprocs.core.model.item.Item;
import com.noiprocs.core.model.mob.character.Humanoid;

public class FenceModel extends Model implements InteractiveInterface {
  public FenceModel(Vector3D position) {
    super(position, true);
  }

  @Override
  public void interact(Model model, Item item) {
    if (model instanceof Humanoid) {
      if (((Humanoid) model).addInventoryItem(new FenceItem(1))) {
        this.destroy();
      }
    }
  }

  @Override
  public void update(int delta) {}
}
