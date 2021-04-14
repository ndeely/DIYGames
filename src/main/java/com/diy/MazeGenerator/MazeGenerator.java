package com.diy.MazeGenerator;

import com.diy.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class MazeGenerator extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MazeGenerator();
            }
        });
    }

    public MazeGenerator() {
        initGUI();

        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        setTitle("Maze Generator");

        TitleLabel tl = new TitleLabel();
        tl.setText("Maze");
        add(tl, BorderLayout.PAGE_START);

        Cell cell = new Cell();

        add(cell, BorderLayout.CENTER);
    }
}
