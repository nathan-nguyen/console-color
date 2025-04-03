package com.noiprocs.core.model.item;

import com.noiprocs.core.model.ItemModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.character.PlayerModel;

public class AxeItemModel extends Model implements ItemModelInterface {
    public AxeItemModel(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void interact(Model model, Item item) {
        if (model instanceof PlayerModel) {
            if (((PlayerModel) model).addInventoryItem(new AxeItem())) {
                this.destroy();
            }
        }
    }

    @Override
    public void update(int delta) {}
}
