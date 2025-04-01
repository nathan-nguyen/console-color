package com.noiprocs.core.model.plant;

import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.SaplingItem;
import com.noiprocs.core.model.mob.character.PlayerModel;

public class SaplingModel extends Model implements InteractiveInterface {
    private static final int HITBOX_HEIGHT = 1, HITBOX_WIDTH = 1;

    public SaplingModel(int x, int y) {
        super(x, y, true, HITBOX_HEIGHT, HITBOX_WIDTH);
    }

    @Override
    public void update(int delta) {}

    @Override
    public void interact(Model model) {
        if (model instanceof PlayerModel) {
            if (((PlayerModel) model).addInventoryItem(new SaplingItem(1))) {
                this.destroy();
            }
        }
    }
}
