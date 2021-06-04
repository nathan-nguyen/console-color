package com.noiprocs;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyPressApp {
    public static void main(String[] argv) {
        KeyPressApp kpa = new KeyPressApp();
        kpa.run();
    }

    private void run() {
        JFrame jframe = new JFrame();
        jframe.setSize(400, 350);
        jframe.setVisible(true);

        jframe.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                System.out.println(event.getKeyChar());
            }
        });
    }
}
