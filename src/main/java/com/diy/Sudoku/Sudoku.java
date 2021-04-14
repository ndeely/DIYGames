package com.diy.Sudoku;

import com.diy.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Sudoku();
            }
        });
    }

    public Sudoku() {
        initGUI();

        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        setTitle("Sudoku");

        TitleLabel tl = new TitleLabel();
        tl.setText("Sudoku");
        add(tl, BorderLayout.PAGE_START);
    }
}
