package com.noiprocs.core.model.building;

import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.FenceItem;
import com.noiprocs.core.model.mob.character.PlayerModel;

public class FenceModel extends Model implements InteractiveInterface {
    private static final int HITBOX_WIDTH = 2, HITBOX_HEIGHT = 2;

    public FenceModel(int x, int y) {
        super(x, y, true, HITBOX_HEIGHT, HITBOX_WIDTH);
    }

    @Override
    public void update(int delta) {

    }

    @Override
    public void interact(Model model) {
        if (model instanceof PlayerModel) {
            if (((PlayerModel) model).addInventoryItem(new FenceItem(1))) {
                this.destroy();
            }
        }
    }

    @Override
    protected void destroy() {
        super.destroy();
    }
}
