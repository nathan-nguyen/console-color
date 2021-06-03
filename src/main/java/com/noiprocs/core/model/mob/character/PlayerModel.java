package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.util.Helper;

public class PlayerModel extends Model {
    public PlayerModel(String id, int x, int y, boolean isPhysical) {
        super(x, y, isPhysical);
        this.id = id;
    }

    public void moveLeft() {
        if (Helper.GAME_CONTEXT.hitboxManager.isValid(this, posX, posY - 1)) posY -= 1;
    }

    public void moveRight() {
        if (Helper.GAME_CONTEXT.hitboxManager.isValid(this, posX, posY + 1)) posY += 1;
   }

   public void moveUp() {
       if (Helper.GAME_CONTEXT.hitboxManager.isValid(this, posX - 1, posY)) posX -= 1;
   }

   public void moveDown() {
       if (Helper.GAME_CONTEXT.hitboxManager.isValid(this, posX + 1, posY)) posX += 1;
   }
}
