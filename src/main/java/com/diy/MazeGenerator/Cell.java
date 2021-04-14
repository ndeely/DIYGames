package com.diy.MazeGenerator;

import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int SIZE = 20;
    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    private int row = -1;
    private int col = -1;

    private boolean[] wall = {true, true, true, true};

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isWall(int index) {
        return wall[index];
    }

    public void paintComponent(Graphics g) {
        g.fillRect(0, 0,SIZE,SIZE);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIZE, SIZE);
    }


}
