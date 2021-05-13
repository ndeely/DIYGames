package com.diy.zilch;

import javax.swing.*;
import java.awt.*;

public class BasicDie extends JPanel {
    private static final long SerialVersionUID = 1L;

    private int value;
    private int size;

    public BasicDie(int value, int size) {
        this.value = value;
        this.size = size;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }

    public void paintComponent(Graphics g) {
        //fill background
        g.setColor(Color.WHITE);
        g.fillRect(0,0, size, size);

        //draw border
        g.setColor(Color.BLACK);
        g.drawRect(0,0,size - 1, size - 1);

        //draw dots
        switch (this.value) {
            case 5:
                drawDot(g, size / 4, size / 4);
                drawDot(g, 3 * size / 4, 3 * size / 4);
            case 3:
                drawDot(g, size / 4, 3 * size / 4);
                drawDot(g, 3 * size / 4, size / 4);
            case 1:
                drawDot(g, size / 2, size / 2);
                break;
            case 6:
                drawDot(g, size / 2, size / 4);
                drawDot(g, size / 2, 3 * size / 4);
            case 4:
                drawDot(g, size / 4, size / 4);
                drawDot(g, 3 * size / 4, 3 * size / 4);
            case 2:
                drawDot(g, size / 4, 3 * size / 4);
                drawDot(g, 3 * size / 4, size / 4);
                break;
        }
    }

    private void drawDot(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.fillOval(x - 3, y - 3, 6, 6);
    }
}
