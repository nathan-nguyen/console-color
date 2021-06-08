package com.noiprocs.core.graphics;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;

public interface HitboxManagerInterface {
    boolean isValid(Model model, int nextX, int nextY);
    void setGameContext(GameContext spriteManager);
    Model getModel(int x, int y, String ignoreModelId, int[][] interactivePoint);
}
