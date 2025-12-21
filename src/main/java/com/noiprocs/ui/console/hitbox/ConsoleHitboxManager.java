package com.noiprocs.ui.console.hitbox;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.hitbox.HitboxManagerInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.ItemModel;
import com.noiprocs.core.model.mob.projectile.ProjectileModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ConsoleHitboxManager implements HitboxManagerInterface {
    private static final Logger logger = LogManager.getLogger(ConsoleHitboxManager.class);

    private GameContext gameContext;
    private final Map<String, Hitbox> hitboxMap = new ConcurrentHashMap<>();

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    private Hitbox getHitbox(Model model) {
        String className = (model instanceof ItemModel)
                ? ((ItemModel) model).itemClass.getName()
                : model.getClass().getName();
        return hitboxMap.computeIfAbsent(className, key -> ConsoleHitboxFactory.generateHitbox(className));
    }

    // Check whether 2 rectangles overlapped, providing top-left and bottom-right positions
    // Note: X is increasing from top to bottom, Y is increasing from left to right.
    public static boolean isOverlapped(
            int tlX1, int tlY1, int brX1, int brY1,
            int tlX2, int tlY2, int brX2, int brY2
    ) {
        return tlX1 < brX2 && brX1 > tlX2 && tlY1 < brY2  && brY1 > tlY2;
    }

    /**
     * Check whether the model could be relocated to provided position.
     * - Find all chunks surrounded the model.
     * - Find all models in Step 1 chunks.
     * - Check whether provided position is valid for the model.
     *
     * @param model: Checking model
     * @param nextX: Next position in X coordinate.
     * @param nextY: Next position in Y coordinate.
     * @return True if model could be relocated.
     */
    @Override
    public boolean isValid(Model model, int nextX, int nextY) {
        Hitbox modelHitbox = getHitbox(model);
        int endX = nextX + modelHitbox.getHeight(model);
        int endY = nextY + modelHitbox.getWidth(model);
        Model projectileSpawner = model instanceof ProjectileModel ? ((ProjectileModel) model).getSpawner() : null;

        return gameContext.modelManager.getSurroundedChunk(model)
                .stream()
                .flatMap(modelChunk -> modelChunk.map.values().stream())
                .noneMatch(surroundedModel -> {
                    if (surroundedModel == model || surroundedModel == projectileSpawner) return false;

                    Hitbox surroundedHitbox = getHitbox(surroundedModel);
                    if ((modelHitbox.maskBit & surroundedHitbox.categoryBit) == 0) return false;

                    return isOverlapped(
                            nextX, nextY, endX, endY,
                            surroundedModel.posX, surroundedModel.posY,
                            surroundedModel.posX + surroundedHitbox.getHeight(surroundedModel),
                            surroundedModel.posY + surroundedHitbox.getWidth(surroundedModel)
                    );
                });
    }

    @Override
    public boolean isColliding(Model m1, Model m2) {
        Hitbox hb1 = getHitbox(m1), hb2 = getHitbox(m2);
        return isOverlapped(
                m1.posX, m1.posY,
                m1.posX + hb1.getHeight(m1), m1.posY + hb1.getWidth(m1),
                m2.posX, m2.posY,
                m2.posX + hb2.getHeight(m2), m2.posY + hb2.getWidth(m2)
        );
    }

    /**
     * Get list of colliding models if providing model is moved to position (nextX, nextY)
     *
     * @param model: Checking model
     * @param nextX: Destination posX
     * @param nextY: Destination posX
     * @return List of colliding models.
     */
    @Override
    public List<Model> getCollidingModel(Model model, int nextX, int nextY) {
        Hitbox targetHitbox = getHitbox(model);
        return getCollidingModel(model, nextX, nextY, targetHitbox.getHeight(model), targetHitbox.getWidth(model));
    }

    /**
     * Get list of colliding models, providing direction, distance to original hitbox, and size of checking hitbox
     * @param model: Checking model
     * @param directionX: DirectionX to checking hitbox
     * @param directionY: DirectionY to checking hitbox
     * @param dx: DistanceX to from original hitbox to checking hitbox
     * @param dy: DistanceY to from original hitbox to checking hitbox
     * @param height: Checking hitbox height
     * @param width: Checking hitbox width
     * @return List of colliding models.
     */
    @Override
    // Facing right: directionY >= 0
    // Facing left: directionY < 0
    public List<Model> getCollidingModel(Model model, int directionX, int directionY, int dx, int dy, int height, int width) {
        Hitbox targetHitbox = getHitbox(model);
        int nextX = model.posX + dx;
        int nextY = model.posY + dy + (directionY >= 0 ? targetHitbox.getWidth(model): -width);

        return getCollidingModel(model, nextX, nextY, height, width);
    }

    /**
     * Get spawn point for provided model, this is usually used for projectile spawning
     * to avoid colliding with the spawner itself.
     * This spawn point is relative to the model's position and depends on the hitbox size.
     *
     * @param model      : Model of spawner.
     * @param directionX : Spawn directionX
     * @param directionY : Spawn directionY
     * @return array size of 2: {spawnPointX, spawnPointY}
     */
    @Override
    public int[] getSpawnPoint(Model model, int directionX, int directionY) {
        Hitbox targetHitbox = getHitbox(model);
        return targetHitbox.getSpawnPoint(directionX, directionY);
    }

    private List<Model> getCollidingModel(Model model, int nextX, int nextY, int height, int width) {
        Model projectileSpawner = model instanceof ProjectileModel ? ((ProjectileModel) model).getSpawner() : null;
        int endX = nextX + height, endY = nextY + width;

        return gameContext.modelManager.getSurroundedChunk(model).stream()
                .flatMap(modelChunk -> modelChunk.map.values().stream())
                .filter(surroundedModel -> {
                    if (surroundedModel == model || surroundedModel == projectileSpawner) return false;

                    Hitbox surroundedHitbox = getHitbox(surroundedModel);
                    return isOverlapped(
                            nextX, nextY, endX, endY,
                            surroundedModel.posX, surroundedModel.posY,
                            surroundedModel.posX + surroundedHitbox.getHeight(surroundedModel),
                            surroundedModel.posY + surroundedHitbox.getWidth(surroundedModel)
                    );
                })
                .collect(Collectors.toList());
    }
}
