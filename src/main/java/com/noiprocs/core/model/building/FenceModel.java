package com.noiprocs.core.model.building;

import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.FenceItem;
import com.noiprocs.core.model.item.Item;
import com.noiprocs.core.model.mob.character.PlayerModel;

public class FenceModel extends Model implements InteractiveInterface {
    public FenceModel(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void interact(Model model, Item item) {
        if (model instanceof PlayerModel) {
            if (((PlayerModel) model).addInventoryItem(new FenceItem(1))) {
                this.destroy();
            }
        }
    }

    @Override
    public void update(int delta) {}
}
