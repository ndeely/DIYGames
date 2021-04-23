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

    private boolean current = false;
    private boolean end = false;

    private boolean[] path = {false,false,false,false};

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
        // draw the background
        g.setColor(Color.WHITE);
        g.fillRect(0,0, this.SIZE, this.SIZE);
        g.setColor(Color.BLACK);

        //draw the walls
        if (this.wall[TOP]) {
            g.drawLine(0, 0, this.SIZE - 1, 0);
        }
        if (this.wall[RIGHT]) {
            g.drawLine(this.SIZE, 0, this.SIZE, this.SIZE);
        }
        if (this.wall[BOTTOM]) {
            g.drawLine(0, this.SIZE, this.SIZE, this.SIZE);
        }
        if (this.wall[LEFT]) {
            g.drawLine(0, 0, 0, this.SIZE);
        }

        // draw the path
        g.setColor(Color.GREEN);
        for (int i = 0; i < path.length; i++) {
            if (path[i]) {
                switch(i) {
                    case TOP: g.drawLine(this.SIZE / 2, 0, this.SIZE / 2, this.SIZE / 2);
                        break;
                    case RIGHT: g.drawLine(this.SIZE, this.SIZE / 2, this.SIZE / 2, this.SIZE / 2);
                        break;
                    case BOTTOM: g.drawLine(this.SIZE / 2, this.SIZE, this.SIZE / 2, this.SIZE / 2);
                        break;
                    case LEFT: g.drawLine(0, this.SIZE / 2, this.SIZE / 2, this.SIZE / 2);
                        break;
                }
            }
        }

        // draw the balls
        if (this.current) {
            g.setColor(Color.GREEN);
            g.fillOval(3, 3, this.SIZE - 7, this.SIZE - 7);
        } else if (this.end) {
            g.setColor(Color.RED);
            g.fillOval(3, 3, this.SIZE - 7, this.SIZE - 7);
        }
    }

    public boolean hasAllWalls() {
        return this.isWall(TOP) &&
                this.isWall((RIGHT)) &&
                this.isWall((BOTTOM)) &&
                this.isWall((LEFT));
    }

    public void removeWall(int w) {
        this.wall[w] = false;
        this.repaint();
    }

    public void openTo(Cell neighbour) {
        if (this.row < neighbour.getRow()) {
            removeWall(BOTTOM);
            neighbour.removeWall(TOP);
        } else if (this.row > neighbour.getRow()) {
            removeWall(TOP);
            neighbour.removeWall(BOTTOM);
        } else if (this.col < neighbour.getCol()) {
            removeWall(RIGHT);
            neighbour.removeWall(LEFT);
        } else if (this.col > neighbour.getCol()) {
            removeWall(LEFT);
            neighbour.removeWall(RIGHT);
        }
    }

    public void setCurrent(boolean current) {
        this.current = current;
        this.repaint();
    }

    public void setEnd(boolean end) {
        this.end = end;
        this.repaint();
    }

    public void addPath(int side) {
        this.path[side] = true;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIZE, SIZE);
    }


}
