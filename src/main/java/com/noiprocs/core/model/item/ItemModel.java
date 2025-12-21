package com.noiprocs.core.model.item;

import com.noiprocs.core.model.ItemModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.common.Helper;
import com.noiprocs.core.common.Vector3D;

public class ItemModel extends Model implements ItemModelInterface {
    public final Class<?> itemClass;

    public ItemModel(Vector3D position, Class<?> itemClass) {
        super(position, true);
        this.itemClass = itemClass;
    }

    @Override
    public void interact(Model model, Item item) {
        if (model instanceof PlayerModel) {
            Item createItem = (Item) Helper.createObject(itemClass, 1);
            if (((PlayerModel) model).addInventoryItem(createItem)) {
                this.destroy();
            }
        }
    }

    @Override
    public void update(int delta) {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + itemClass.getSimpleName() + " at " + position + ")";
    }
}
