package com.diy.watchyourstep;

import javax.swing.*;
import java.awt.*;

public class TerrainButton extends JButton {
    private static final long serialVersionUID = 1L;
    private static final int SIZE = 50;
    private int row = 0;
    private int col = 0;
    private int nextToHoles = 0;
    private boolean hole = false;
    private boolean revealed = false;
    private int status = 0;

    public TerrainButton(int row, int col) {
        this.row = row;
        this.col = col;

        Dimension size = new Dimension(SIZE, SIZE);
        setPreferredSize(size);
        setFont(new Font("Dialog", Font.BOLD, 16));
    }

    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }
    public int getNextToHoles() {
        return this.nextToHoles;
    }
    public boolean hasHole() {
        return this.hole;
    }
    public boolean isRevealed() {
        return this.revealed;
    }
    public int getStatus() { return this.status; }
    public void setHole(boolean hasHole) {
        this.hole = hasHole;
    }
    public void increaseHoleCount() {
        this.nextToHoles++;
    }
    public void reveal(boolean reveal) {
        this.revealed = reveal;
        if (this.revealed) {
            setBackground((this.hole ? Color.BLACK : Color.CYAN));
            if ((this.nextToHoles > 0) && !this.hasHole()) {
                setText("" + this.nextToHoles);
            }
        } else {
            setBackground(null);
            setText("");
        }
    }
    public void reset() {
        this.hole = false;
        this.revealed = false;
        this.nextToHoles = 0;
        setText("");
        setBackground(null);
    }

    // adds warning symbol (1), then ? (2), then blank (0)
    public void switchStatus() {
        if (!this.revealed) {
            switch (this.status) {
                case 0:
                    setText("\u2620");
                    this.status++;
                    break;
                case 1:
                    setText("?");
                    this.status++;
                    break;
                case 2:
                    setText("");
                    this.status = 0;
                    break;
            }
        }
    }


}
