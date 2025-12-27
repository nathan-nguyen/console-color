package com.noiprocs.core.model.plant;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Helper;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.DurableModel;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.ModelManager;
import com.noiprocs.core.model.item.*;
import com.noiprocs.core.model.mob.character.Humanoid;

public class TreeModel extends DurableModel implements InteractiveInterface {
  private static final int MAX_DURABILITY = 20;

  private static final int MATURE_AGE = 3600;
  private static final int MIDDLE_AGE = 1800;

  public int treeAge;

  public TreeModel(Vector3D position) {
    this(position, MATURE_AGE);
  }

  public TreeModel(Vector3D position, int treeAge) {
    super(position, true, MAX_DURABILITY);
    this.treeAge = treeAge;
  }

  @Override
  public void update(int delta) {
    ++treeAge;
  }

  @Override
  public void interact(Model model, Item item) {
    if (item instanceof AxeItem) {
      this.updateHealth(-4);
    } else {
      this.updateHealth(-1);
    }
    if (this.getHealth() <= 0 || !this.isOldAge()) this.dropItem(model);
  }

  private void dropItem(Model destroyer) {
    if (!(destroyer instanceof Humanoid)) return;

    ModelManager modelManager = GameContext.get().modelManager;
    if (this.isOldAge()) {
      modelManager.spawnModelsIfValid(
          new ItemModel(position, WoodLogItem.class),
          new ItemModel(
              new Vector3D(position.x + 1, position.y + 1, position.z), WoodLogItem.class),
          // AppleItemModel is different with ItemModel with AppleItem class
          new AppleItemModel(new Vector3D(position.x + 2, position.y + 3, position.z)));

      int seedDrop = Helper.random.nextInt(10);
      // 0 drop: 20% - 1 drop: 50% - 2 drop: 30%
      if (seedDrop >= 2) {
        modelManager.spawnModelIfValid(
            new ItemModel(new Vector3D(position.x, position.y + 2, position.z), SaplingItem.class));
      }
      if (seedDrop >= 7) {
        modelManager.spawnModelIfValid(
            new ItemModel(
                new Vector3D(position.x + 1, position.y + 2, position.z), SaplingItem.class));
      }
    } else if (this.isMiddleAge()) {
      modelManager.spawnModelIfValid(new ItemModel(position, WoodLogItem.class));
    }
  }

  public boolean isOldAge() {
    return treeAge >= MATURE_AGE;
  }

  public boolean isMiddleAge() {
    return treeAge >= MIDDLE_AGE && treeAge < MATURE_AGE;
  }

  public boolean isYoungAge() {
    return treeAge < MIDDLE_AGE;
  }
}
