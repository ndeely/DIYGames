package com.diy.zilch;

import javax.swing.*;
import java.awt.*;

public class BasicDie extends JPanel {
    private static final long SerialVersionUID = 1L;

    private final int VALUE;
    private final int SIZE;

    public BasicDie(int value, int size) {
        this.VALUE = value;
        this.SIZE = size;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIZE, SIZE);
    }

    public void paintComponent(Graphics g) {
        //fill background
        g.setColor(Color.WHITE);
        g.fillRect(0,0, SIZE, SIZE);

        //draw border
        g.setColor(Color.BLACK);
        g.drawRect(0,0,SIZE - 1, SIZE - 1);

        //draw dots
        switch (this.VALUE) {
            case 5:
                drawDot(g, SIZE / 4, SIZE / 4);
                drawDot(g, 3 * SIZE / 4, 3 * SIZE / 4);
            case 3:
                drawDot(g, SIZE / 4, 3 * SIZE / 4);
                drawDot(g, 3 * SIZE / 4, SIZE / 4);
            case 1:
                drawDot(g, SIZE / 2, SIZE / 2);
                break;
            case 6:
                drawDot(g, SIZE / 2, SIZE / 4);
                drawDot(g, SIZE / 2, 3 * SIZE / 4);
            case 4:
                drawDot(g, SIZE / 4, SIZE / 4);
                drawDot(g, 3 * SIZE / 4, 3 * SIZE / 4);
            case 2:
                drawDot(g, SIZE / 4, 3 * SIZE / 4);
                drawDot(g, 3 * SIZE / 4, SIZE / 4);
                break;
        }
    }

    private void drawDot(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.fillOval(x - 3, y - 3, 6, 6);
    }
}
