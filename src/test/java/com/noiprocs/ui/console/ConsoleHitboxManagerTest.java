package com.noiprocs.ui.console;

import org.junit.Test;

public class ConsoleHitboxManagerTest {
    @Test
    public void testInterationPoint() {
        solve(0, 1);   //  0 -  3 ;  1 -  3
        solve(0, -1);  //  0 - -1 ;  1 - -1
        solve(1, 0);   //  2 -  0 ;  2 -  1;  2 - 2
        solve(-1, 0);  // -1 -  0 ; -1 -  1; -1 - 2
    }

    private void solve(int directionX, int directionY) {
        int hitboxHeight = 2;
        int hitboxWidth = 3;

        int intensityX = 1 - Math.abs(directionX);
        int intensityY = 1 - Math.abs(directionY);
        int count = intensityX * hitboxHeight + intensityY * hitboxWidth;

        for (int i = 0; i < count; ++i) {
            int pointX = intensityX * i + (directionX == -1 ? -1 : directionX * hitboxHeight);
            int pointY = intensityY * i + (directionY == -1 ? -1 : directionY * hitboxWidth);
            System.out.println(pointX + " - " + pointY);
        }
    }
}
