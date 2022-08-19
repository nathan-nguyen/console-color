package com.noiprocs.core.model.plant;

public class BirchTreeModel extends TreeModel {
    private static final int HITBOX_HEIGHT = 1, HITBOX_WIDTH = 2;
    public BirchTreeModel(int x, int y) {
        super(x, y, HITBOX_HEIGHT, HITBOX_WIDTH);
    }
}
