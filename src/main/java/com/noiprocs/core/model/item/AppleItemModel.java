package com.noiprocs.core.model.item;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.CotMobModel;

public class AppleItemModel extends ItemModel {
    public AppleItemModel(int x, int y) {
        super(x, y, AppleItem.class);
    }

    @Override
    public void interact(Model model, Item item) {
        if (model instanceof CotMobModel) {
            this.destroy();
        }
    }
}
