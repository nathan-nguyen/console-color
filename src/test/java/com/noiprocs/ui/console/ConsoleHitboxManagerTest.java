package com.noiprocs.ui.console;

import com.noiprocs.core.common.Vector3D;
import com.noiprocs.ui.console.hitbox.ConsoleHitboxManager;
import junit.framework.TestCase;

public class ConsoleHitboxManagerTest extends TestCase {

  public void testIsOverlapped() {
    // Rectangles with significant overlap in the middle
    assertTrue(
        ConsoleHitboxManager.isOverlapped(
            new Vector3D(10, 10, 0),
            new Vector3D(30, 30, 0),
            new Vector3D(20, 20, 0),
            new Vector3D(40, 40, 0)));

    // Rectangles far apart from each other
    assertFalse(
        ConsoleHitboxManager.isOverlapped(
            new Vector3D(10, 10, 0),
            new Vector3D(20, 20, 0),
            new Vector3D(50, 50, 0),
            new Vector3D(60, 60, 0)));

    // Rectangles that share an edge but don't overlap
    assertFalse(
        ConsoleHitboxManager.isOverlapped(
            new Vector3D(10, 10, 0),
            new Vector3D(20, 20, 0),
            new Vector3D(20, 10, 0),
            new Vector3D(30, 20, 0)));

    // Rectangle 2 completely contained within Rectangle 1
    assertTrue(
        ConsoleHitboxManager.isOverlapped(
            new Vector3D(10, 10, 0),
            new Vector3D(50, 50, 0),
            new Vector3D(20, 20, 0),
            new Vector3D(30, 30, 0)));

    // Rectangles that overlap only at one corner
    assertTrue(
        ConsoleHitboxManager.isOverlapped(
            new Vector3D(10, 10, 0),
            new Vector3D(30, 30, 0),
            new Vector3D(25, 25, 0),
            new Vector3D(40, 40, 0)));
  }
}
