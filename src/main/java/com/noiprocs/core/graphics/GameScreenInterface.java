package com.noiprocs.core.graphics;

import com.noiprocs.core.GameContext;

public interface GameScreenInterface {
    void setGameContext(GameContext gameContext);

    void render(int delta);
}
