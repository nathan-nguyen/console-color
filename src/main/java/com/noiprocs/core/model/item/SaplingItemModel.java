package com.noiprocs.core.model.item;

import com.noiprocs.core.model.ItemModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.character.PlayerModel;

public class SaplingItemModel extends Model implements ItemModelInterface {
    public SaplingItemModel(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void update(int delta) {}

    @Override
    public void interact(Model model, Item item) {
        if (model instanceof PlayerModel) {
            if (((PlayerModel) model).addInventoryItem(new SaplingItem(1))) {
                this.destroy();
            }
        }
    }
}
