package com.noiprocs.core.model.plant;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.DurableModel;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.ModelManager;
import com.noiprocs.core.model.item.*;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.util.Helper;

public class TreeModel extends DurableModel implements InteractiveInterface {
    private static final int MAX_DURABILITY = 20;

    private static final int MATURE_AGE = 3600;
    private static final int MIDDLE_AGE = 1800;

    public int treeAge;

    public TreeModel(int x, int y) {
        this(x, y, MATURE_AGE);
    }

    public TreeModel(int x, int y, int treeAge) {
        super(x, y, true, MAX_DURABILITY);
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
        if (this.getHealth() <= 0 || !this.isOldAge())
            this.dropItem(model);
    }

    private void dropItem(Model destroyer) {
        if (!(destroyer instanceof PlayerModel))
            return;

        ModelManager modelManager = GameContext.get().modelManager;
        if (this.isOldAge()) {
            modelManager.spawnModelsIfValid(
                    new ItemModel(posX, posY, WoodLogItem.class),
                    new ItemModel(posX + 1, posY + 1, WoodLogItem.class),
                    new AppleItemModel(posX + 2, posY + 3));

            int seedDrop = Helper.random.nextInt(10);
            // 0 drop: 20% - 1 drop: 50% - 2 drop: 30%
            if (seedDrop >= 2) {
                modelManager.spawnModelIfValid(
                        new ItemModel(posX, posY + 2, SaplingItem.class));
            }
            if (seedDrop >= 7) {
                modelManager.spawnModelIfValid(
                        new ItemModel(posX + 1, posY + 2, SaplingItem.class));
            }
        } else if (this.isMiddleAge()) {
            modelManager.spawnModelIfValid(
                    new ItemModel(posX, posY, WoodLogItem.class));
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
