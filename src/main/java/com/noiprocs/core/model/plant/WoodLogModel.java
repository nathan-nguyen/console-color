package com.noiprocs.core.model.plant;

import com.noiprocs.core.model.ItemModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.WoodLogItem;
import com.noiprocs.core.model.mob.character.PlayerModel;

public class WoodLogModel extends Model implements ItemModelInterface {
    public WoodLogModel(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void update(int delta) {}

    @Override
    public void interact(Model model) {
        if (model instanceof PlayerModel) {
            if (((PlayerModel) model).addInventoryItem(new WoodLogItem(1))) {
                this.destroy();
            }
        }
    }
}
