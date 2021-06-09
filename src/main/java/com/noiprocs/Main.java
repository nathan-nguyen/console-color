package com.noiprocs;

import com.noiprocs.ui.console.sprite.plant.PineTreeSprite;

public class Main {
    public static void main(String[] args) {
        PineTreeSprite pts = new PineTreeSprite("Pine Tree");
        for (char[] texture: pts.getTexture()) {
            for (char c: texture) System.out.print(c);
            System.out.println();
        }
    }
}
