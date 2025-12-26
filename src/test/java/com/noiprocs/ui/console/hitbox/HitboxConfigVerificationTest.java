package com.noiprocs.ui.console.hitbox;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.building.FenceModel;
import com.noiprocs.core.model.environment.MazePartModel;
import com.noiprocs.core.model.environment.WallTrapModel;
import com.noiprocs.core.model.item.AxeItem;
import com.noiprocs.core.model.item.WoodLogItem;
import com.noiprocs.core.model.mob.CotPsychoModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.model.mob.projectile.FlyingWoodLogModel;
import com.noiprocs.core.model.plant.BirchTreeModel;
import com.noiprocs.core.model.plant.PineTreeModel;
import com.noiprocs.core.model.plant.TreeModel;
import junit.framework.TestCase;

import static com.noiprocs.ui.console.hitbox.HitboxCategory.*;

public class HitboxConfigVerificationTest extends TestCase {

    public void testPlayerModelSpecialCase() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(PlayerModel.class.getName());
        assertNotNull(hitbox);
        assertEquals(1, hitbox.dimension.x, 0.001);
        assertEquals(3, hitbox.dimension.y, 0.001);
        assertEquals(PLAYER, hitbox.categoryBit);
        assertEquals(WALL | MOB, hitbox.maskBit);
    }

    public void testWallTrapModelSpecialCase() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(WallTrapModel.class.getName());
        assertNotNull(hitbox);
        assertTrue(hitbox instanceof com.noiprocs.ui.console.hitbox.environment.WallTrapHitbox);
    }

    public void testCotPsychoModelFromConfig() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(CotPsychoModel.class.getName());
        assertNotNull(hitbox);
        assertEquals(1, hitbox.dimension.x, 0.001);
        assertEquals(4, hitbox.dimension.y, 0.001);
        assertEquals(MOB, hitbox.categoryBit);
        assertEquals(WALL | PLAYER | MOB, hitbox.maskBit);
    }

    public void testTreeModelFromConfig() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(TreeModel.class.getName());
        assertNotNull(hitbox);
        assertEquals(1, hitbox.dimension.x, 0.001);
        assertEquals(4, hitbox.dimension.y, 0.001);
        assertEquals(WALL, hitbox.categoryBit);
        assertEquals(WALL, hitbox.maskBit);
    }

    public void testPineTreeModelFromConfig() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(PineTreeModel.class.getName());
        assertNotNull(hitbox);
        assertEquals(1, hitbox.dimension.x, 0.001);
        assertEquals(2, hitbox.dimension.y, 0.001);
    }

    public void testBirchTreeModelFromConfig() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(BirchTreeModel.class.getName());
        assertNotNull(hitbox);
        assertEquals(1, hitbox.dimension.x, 0.001);
        assertEquals(2, hitbox.dimension.y, 0.001);
    }

    public void testFenceModelFromConfig() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(FenceModel.class.getName());
        assertNotNull(hitbox);
        assertEquals(2, hitbox.dimension.x, 0.001);
        assertEquals(2, hitbox.dimension.y, 0.001);
    }

    public void testWoodLogItemFromConfig() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(WoodLogItem.class.getName());
        assertNotNull(hitbox);
        assertEquals(1, hitbox.dimension.x, 0.001);
        assertEquals(1, hitbox.dimension.y, 0.001);
        assertEquals(ITEM, hitbox.categoryBit);
        assertEquals(WALL, hitbox.maskBit);
    }

    public void testAxeItemFromConfig() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(AxeItem.class.getName());
        assertNotNull(hitbox);
        assertEquals(3, hitbox.dimension.x, 0.001);
        assertEquals(3, hitbox.dimension.y, 0.001);
    }

    public void testFlyingWoodLogModelFromConfig() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(FlyingWoodLogModel.class.getName());
        assertNotNull(hitbox);
        assertEquals(1, hitbox.dimension.x, 0.001);
        assertEquals(1, hitbox.dimension.y, 0.001);
        assertEquals(PROJECTILE, hitbox.categoryBit);
        assertEquals(MASK_ALL, hitbox.maskBit);
    }

    public void testMazePartModelWithConfigReference() {
        Hitbox hitbox = ConsoleHitboxFactory.generateHitbox(MazePartModel.class.getName());
        assertNotNull(hitbox);
        assertEquals(Config.MAZE_WALL_THICKNESS_HEIGHT, hitbox.dimension.x, 0.001);
        assertEquals(Config.MAZE_WALL_THICKNESS_WIDTH, hitbox.dimension.y, 0.001);
        assertEquals(WALL, hitbox.categoryBit);
        assertEquals(WALL | PLAYER | MOB | PROJECTILE, hitbox.maskBit);
    }

    public void testUnknownModelThrowsException() {
        try {
            ConsoleHitboxFactory.generateHitbox("com.noiprocs.UnknownModel");
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertTrue(e.getMessage().contains("UnknownModel"));
        }
    }
}
