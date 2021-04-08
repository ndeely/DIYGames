package com.diy.slidingTiles;

import com.diy.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class SlidingTiles extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SlidingTiles();
            }
        });
    }

    public SlidingTiles() {
        initGUI();
    }

    private void initGUI() {
        TitleLabel tl = new TitleLabel();
        tl.setText("Sliding Tiles");
        add(tl, BorderLayout.PAGE_START);

        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
