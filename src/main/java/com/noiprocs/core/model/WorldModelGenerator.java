package com.noiprocs.core.model;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.environment.TreeModel;
import com.noiprocs.core.model.mob.character.PlayerModel;

import java.util.Random;

public class WorldModelGenerator {
    private Random random = new Random();

    public void generateWorld(ServerModelManager serverModelManager) {
        serverModelManager.addModel(new PlayerModel(Config.USER_NAME, 0, 0, true));
        for (int i = 0; i < 20; ++i) {
            serverModelManager.addModel(
                    new TreeModel(random.nextInt(60) - 30, random.nextInt(40) - 20, true)
            );
        }
    }
}
