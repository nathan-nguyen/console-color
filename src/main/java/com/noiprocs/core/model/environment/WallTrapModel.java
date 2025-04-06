package com.noiprocs.core.model.environment;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.DurableModel;
import com.noiprocs.core.model.Model;

import java.util.List;

public class WallTrapModel extends Model {
    private int tickCount = 0;

    public WallTrapModel(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void update(int delta) {
        this.tickCount += 1;

        // Deal damage in the first tick only
        if (!this.isClosedFirstTick()) return;
        List<Model> collidingModels = GameContext.get().hitboxManager.getCollidingModel(this, this.posX, this.posY);
        for (Model model: collidingModels) {
            if (model instanceof DurableModel) {
                ((DurableModel) model).updateHealth(-20);
            }
        }
    }

    public boolean isClosed() {
        return this.tickCount % 120 < 20;
    }

    private boolean isClosedFirstTick() {
        return this.tickCount % 120 == 0;
    }
}
