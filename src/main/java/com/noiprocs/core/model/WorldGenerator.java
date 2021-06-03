package com.noiprocs.core.model;

import com.noiprocs.core.model.environment.TreeModel;
import com.noiprocs.core.model.mob.character.PlayerModel;

import java.util.Random;

public class WorldGenerator {
    private Random random = new Random();

    public void generateWorld(ServerModelManager serverModelManager) {
        serverModelManager.addModel(new PlayerModel(0, 0, true));
        for (int i = 0; i < 10; ++i) {
            serverModelManager.addModel(
                    new TreeModel(random.nextInt(60) - 30, random.nextInt(40) - 20, true)
            );
        }
    }
}
