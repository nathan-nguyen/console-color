package com.noiprocs.ui.console.hitbox;

public class HitboxCategory {
    public static final int WALL = 1;
    public static final int PLAYER = 2;
    public static final int MOB = 4;
    public static final int ITEM = 8;
    public static final int PROJECTILE = 16;

    public static final int MASK_ALL = WALL | PLAYER | MOB | ITEM | PROJECTILE;
}
