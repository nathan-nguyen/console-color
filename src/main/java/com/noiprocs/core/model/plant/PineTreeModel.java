package com.noiprocs.core.model.plant;

public class PineTreeModel extends TreeModel {
    private static final int HITBOX_HEIGHT = 1, HITBOX_WIDTH = 2;
    public PineTreeModel(int x, int y) {
        super(x, y, HITBOX_HEIGHT, HITBOX_WIDTH);
    }
}
