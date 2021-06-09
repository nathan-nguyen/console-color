package com.noiprocs.core.model.plant;

import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.util.Helper;

public class TreeModel extends Model implements InteractiveInterface {
    private static final int MAX_DURABILITY = 10;

    private int durability = MAX_DURABILITY;

    public TreeModel(int x, int y, boolean isVisible) {
        super(x, y, isVisible);
    }

    @Override
    public void interact(Model model) {
        --durability;
        if (durability == 0) this.destroy();
    }

    @Override
    protected void destroy() {
        super.destroy();
        Helper.GAME_CONTEXT.modelManager.addSpawnModel(new WoodLogModel(posX, posY, true));
        Helper.GAME_CONTEXT.modelManager.addSpawnModel(new WoodLogModel(posX + 1, posY + 1, true));

        int seedDrop = Helper.random.nextInt(10);
        // 0 drop: 20%
        // 1 drop: 50%
        // 2 drop: 30%
        if (seedDrop >= 2) Helper.GAME_CONTEXT.modelManager.addSpawnModel(new SaplingModel(posX,posY + 2, true));
        if (seedDrop >= 7) Helper.GAME_CONTEXT.modelManager.addSpawnModel(new SaplingModel(posX + 1,posY + 2, true));
    }
}
