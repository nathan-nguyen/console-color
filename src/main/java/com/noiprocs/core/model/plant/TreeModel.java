package com.noiprocs.core.model.plant;

import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.util.Helper;

public class TreeModel extends Model implements InteractiveInterface {
    private static final int MAX_DURABILITY = 10;

    private static final int OLD_AGE = 3600;
    private static final int MIDDLE_AGE = 1800;

    private int durability = MAX_DURABILITY;

    public int treeAge = 0;


    public TreeModel(int x, int y, boolean isVisible) {
        super(x, y, isVisible);
        this.treeAge = OLD_AGE;
    }

    public TreeModel(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void update(int delta) {
        ++treeAge;
    }

    @Override
    public void interact(Model model) {
        --durability;
        if (durability == 0 || !this.isOldAge()) this.destroy();
    }

    @Override
    protected void destroy() {
        super.destroy();

        if (this.isOldAge()) {
            Helper.GAME_CONTEXT.modelManager.addSpawnModel(new WoodLogModel(posX, posY, true));
            Helper.GAME_CONTEXT.modelManager.addSpawnModel(new WoodLogModel(posX + 1, posY + 1, true));

            int seedDrop = Helper.random.nextInt(10);
            // 0 drop: 20%
            // 1 drop: 50%
            // 2 drop: 30%
            if (seedDrop >= 2) {
                Helper.GAME_CONTEXT.modelManager.addSpawnModel(new SaplingModel(posX, posY + 2, true));
            }
            if (seedDrop >= 7)
                Helper.GAME_CONTEXT.modelManager.addSpawnModel(
                        new SaplingModel(posX + 1, posY + 2, true)
                );
        }
        else if (this.isMiddleAge()) {
            Helper.GAME_CONTEXT.modelManager.addSpawnModel(new WoodLogModel(posX, posY, true));
        }
    }

    public boolean isOldAge() {
        return treeAge >= OLD_AGE;
    }

    public boolean isMiddleAge() {
        return treeAge >= MIDDLE_AGE && treeAge < OLD_AGE;
    }

    public boolean isYoungAge() {
        return treeAge < MIDDLE_AGE;
    }
}
