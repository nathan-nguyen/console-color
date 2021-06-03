package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.model.Model;

public class PlayerModel extends Model {
    public PlayerModel(String id, int x, int y, boolean isPhysical) {
        super(x, y, isPhysical);
        this.id = id;
    }

    public void moveLeft() {
        posY -= 1;
    }

    public void moveRight() {
        posY += 1;
   }

   public void moveUp() {
        posX -= 1;
   }

   public void moveDown() {
        posX += 1;
   }
}
