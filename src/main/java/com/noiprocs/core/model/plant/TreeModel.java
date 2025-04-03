package com.noiprocs.core.model.plant;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.*;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.util.Helper;

public class TreeModel extends Model implements InteractiveInterface {
    private static final int MAX_DURABILITY = 20;

    private static final int MATURE_AGE = 3600;
    private static final int MIDDLE_AGE = 1800;

    private int durability = MAX_DURABILITY;

    public int treeAge;

    public TreeModel(int x, int y) {
        this(x, y, MATURE_AGE);
    }

    public TreeModel(int x, int y, int treeAge) {
        super(x, y, true);
        this.treeAge = treeAge;
    }

    @Override
    public void update(int delta) {
        ++treeAge;
    }

    @Override
    public void interact(Model model, Item item) {
        if (item instanceof AxeItem) {
            durability -= 4;
        }
        else --durability;
        if (durability == 0 || !this.isOldAge()) this.destroy(model);
    }

    private void destroy(Model destroyer) {
        this.destroy();

        if (!(destroyer instanceof PlayerModel)) return;

        if (this.isOldAge()) {
            GameContext.get().modelManager.addSpawnModel(new WoodLogItemModel(posX, posY));
            GameContext.get().modelManager.addSpawnModel(new WoodLogItemModel(posX + 1, posY + 1));

            int seedDrop = Helper.random.nextInt(10);
            // 0 drop: 20% - 1 drop: 50% - 2 drop: 30%
            if (seedDrop >= 2) {
                GameContext.get().modelManager.addSpawnModel(new SaplingItemModel(posX, posY + 2));
            }
            if (seedDrop >= 7)
                GameContext.get().modelManager.addSpawnModel(
                        new SaplingItemModel(posX + 1, posY + 2)
                );
        }
        else if (this.isMiddleAge()) {
            GameContext.get().modelManager.addSpawnModel(new WoodLogItemModel(posX, posY));
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
