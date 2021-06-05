package com.noiprocs.core.model.generator;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.environment.MazePartModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.noiprocs.core.model.environment.MazePartModel.*;

public class MazeModelGenerator {
    // CENTER_MODE = true if destination is in the center
    private static final boolean CENTRE_MODE = false;

    private static final char WALL_TEXTURE = '░';

    private static final boolean PRINT_MAZE_PATH = false;

    private static final boolean PRINT_BORDER = true;
    private static final boolean PRINT_ORDER = true;

    private static final int EMPTY = 0;
    private static final int INVALID = -1;

    private final Random random = new Random();

    // Using two dimensions array for easy maintenance
    private int[][] map;
    private final int dimension;
    private int offsetX, offsetY;

    public MazeModelGenerator(int dimension){
        this.dimension = dimension;
    }

    public void constructMaze(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        while (true) {
            long seed = random.nextLong();
            random.setSeed(seed);

            boolean result = this.construct();
            System.out.println("Seed: " + seed + " - Result: " + result);
            if (result) break;
        }

        // Open the entrance
        map[0][1] = 1;

        // Open the exit
        if (!CENTRE_MODE && map[dimension - 2][dimension - 2] > 0) map[dimension - 2][dimension - 1] = 1;

        // printMaze();
        System.out.print("Maze wall: ");
        printWall();

        if (PRINT_MAZE_PATH) {
            System.out.println("\nMaze path: ");
            printMazePath();
        }
    }

    // Return true if maze is constructed successfully
    private boolean construct(){
        List<Integer> expandableList = new ArrayList<>();
        // Using two dimension array for easy maintenance
        map = new int[dimension][dimension];

        for (int i = 0; i < dimension; ++i){
            map[0][i] = INVALID;
            map[i][0] = INVALID;
            map[dimension - 1][i] = INVALID;
            map[i][dimension - 1] = INVALID;
        }

        // Add (1, 1) to expandableList
        expandableList.add(dimension + 1);

        // 0 < next / n < n-1 && 0 < next % n < n-1
        int next = dimension + 1;
        int order = 0;
        map[1][1] = order % 8 + 1;

        // Keep expanding the maze until there is not any space left
        while (true) {
            // If neighbor square is empty but invalid position, set neighbor to INVALID
            if (map[next / dimension - 1][next % dimension] == EMPTY && !isValidPosition(next - dimension, dimension))
                map[next / dimension - 1][next % dimension] = INVALID;
            if (map[next / dimension][next % dimension + 1] ==  EMPTY && !isValidPosition(next + 1, dimension))
                map[next / dimension][next % dimension + 1] = INVALID;
            if (map[next / dimension + 1][next % dimension] == EMPTY && !isValidPosition(next + dimension, dimension))
                map[next / dimension + 1][next % dimension] = INVALID;
            if (map[next / dimension][next % dimension - 1] == EMPTY && !isValidPosition(next - 1, dimension))
                map[next / dimension][next % dimension - 1] = INVALID;

            // If all four neighbors are not EMPTY, remove next from expendableList
            if (map[next / dimension - 1][next % dimension] != EMPTY && map[next / dimension][next % dimension + 1] != EMPTY
                    && map[next / dimension + 1][next % dimension] != EMPTY && map[next / dimension][next % dimension - 1] != EMPTY) {
                expandableList.remove(Integer.valueOf(next));

                // No space left
                if (expandableList.size() == 0) break;

                next = expandableList.get(random.nextInt(expandableList.size()));
                ++order;
                continue;
            }
            int nextSquare = nextSquare(next, dimension);
            expandableList.add(nextSquare);
            map[nextSquare / dimension][nextSquare % dimension] = order % 8 + 1;

            if (CENTRE_MODE && nextSquare / dimension == dimension / 2 && nextSquare % dimension == dimension / 2)
                map[nextSquare / dimension][nextSquare % dimension] = 9;

            next = nextSquare;
        }

        return CENTRE_MODE ? map[dimension / 2][dimension / 2] == 9 : map[dimension - 2][dimension - 2] > 0;
    }

    // Find next random adjacent empty square, always guarantee to terminate
    private int nextSquare(int next, int n){
        while (true) {
            int order = random.nextInt(4);
            switch (order) {
                case 0: if (map[next / n - 1][next % n] == EMPTY && isValidPosition(next - n, n)) return next - n;
                case 1: if (map[next / n][next % n + 1] == EMPTY && isValidPosition(next + 1, n)) return next + 1;
                case 2: if (map[next / n + 1][next % n] == EMPTY && isValidPosition(next + n, n)) return next + n;
                case 3: if (map[next / n][next % n - 1] == EMPTY && isValidPosition(next - 1, n)) return next - 1;
            }
        }
    }

    // Valid position is position with only 1 non-EMPTY and non-INVALID neighbor
    private boolean isValidPosition(int x, int n) {
        int count = 0;
        if (map[x / n - 1][x % n] > 0) ++count;
        if (map[x / n][x % n + 1] > 0) ++count;
        if (map[x / n + 1][x % n] > 0) ++count;
        if (map[x / n][x % n - 1] > 0) ++count;
        return count == 1;
    }

    private void printMazePath(){
        for (int i = 0; i < dimension; ++i){
            for (int j = 0; j < dimension; ++j){
                if (i == 0 || i == dimension - 1 || j == 0 || j == dimension - 1) System.out.print(PRINT_BORDER ? "x " : "  ");
                else if (map[i][j] > 0) System.out.print(PRINT_ORDER ? map[i][j] + " " : "o ");
                else System.out.print("  ");
            }
            System.out.println();
        }
    }

    private void printWall() {
        System.out.println();

        char[][] display = new char[dimension * WALL_THICKNESS_HEIGHT][dimension * WALL_THICKNESS_WIDTH];
        for (int i = 0; i < dimension; ++i){
            for (int j = 0; j < dimension; ++j){
                if (map[i][j] > 0) continue;
                for (int x = 0; x < WALL_THICKNESS_HEIGHT; ++x) {
                    for (int y = 0; y < WALL_THICKNESS_WIDTH; ++y) {
                        display[i * WALL_THICKNESS_HEIGHT + x][j * WALL_THICKNESS_WIDTH + y] = WALL_TEXTURE;
                    }
                }
            }
        }

        // Print the maze
        for (int i = 0; i < dimension * WALL_THICKNESS_HEIGHT; ++i){
            for (int j = 0; j < dimension * WALL_THICKNESS_WIDTH; ++j) {
                if (display[i][j] == 0) System.out.print(" ");
                else System.out.print(display[i][j]);
            }
            System.out.println();
        }
    }

    public List<Model> getMazePartModelList() {
        List<Model> result = new ArrayList<>();

        for (int i = 0; i < dimension / MAZE_PART_DIMENSION; ++i) {
            for (int j = 0; j < dimension / MAZE_PART_DIMENSION; ++j) {
                int[][] partData = new int[MAZE_PART_DIMENSION][MAZE_PART_DIMENSION];
                for (int x = 0; x < MAZE_PART_DIMENSION; ++x) {
                    for (int y = 0; y < MAZE_PART_DIMENSION; ++y) {
                        partData[x][y] = map[i * MAZE_PART_DIMENSION + x][j * MAZE_PART_DIMENSION + y];
                    }
                }
                MazePartModel mpm = new MazePartModel(
                        offsetX + MAZE_PART_DIMENSION * i * WALL_THICKNESS_HEIGHT,
                        offsetY + MAZE_PART_DIMENSION * j * WALL_THICKNESS_WIDTH,
                        true,
                        partData
                );
                result.add(mpm);
            }
        }
        return result;
    }
}
