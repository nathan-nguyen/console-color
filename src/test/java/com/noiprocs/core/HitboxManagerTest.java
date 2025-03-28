package com.noiprocs.core;

import junit.framework.TestCase;

public class HitboxManagerTest extends TestCase {

    public void testIsOverlapped() {
        // Rectangles with significant overlap in the middle
        assertTrue(
                HitboxManager.isOverlapped(
                        10, 10, 30, 30,
                        20, 20, 40, 40
                )
        );

        // Rectangles far apart from each other
        assertFalse(
                HitboxManager.isOverlapped(
                        10, 10, 20, 20,
                        50, 50, 60, 60
                )
        );

        // Rectangles that share an edge but don't overlap
        assertFalse(
                HitboxManager.isOverlapped(
                        10, 10, 20, 20,
                        20, 10, 30, 20
                )
        );

        // Rectangle 2 completely contained within Rectangle 1
        assertTrue(
                HitboxManager.isOverlapped(
                        10, 10, 50, 50,
                        20, 20, 30, 30
                )
        );

        // Rectangles that overlap only at one corner
        assertTrue(
                HitboxManager.isOverlapped(
                        10, 10, 30, 30,
                        25, 25, 40, 40
                )
        );
    }
}
