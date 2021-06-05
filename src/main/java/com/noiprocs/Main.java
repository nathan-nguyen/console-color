package com.noiprocs;

import com.noiprocs.core.model.generator.MazeModelGenerator;

public class Main {
    public static void main(String[] args) {
        MazeModelGenerator mmg = new MazeModelGenerator(40);
        mmg.constructMaze(0, 0);
    }
}
