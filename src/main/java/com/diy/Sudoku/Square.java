package com.diy.Sudoku;

import javax.swing.JButton;
import java.awt.Dimension;

public class Square extends JButton {
    private static final long SerialVersionUID = 1L;

    private final Dimension SIZE = new Dimension(100,50);
    private int val = 0;
    private int row = 0;
    private int col = 0;
    private int box = -1;
    private int id = -1;

    public Square(int row, int col, int box, int id) {
        setRowCol(row, col);
        setBox(box);
        setId(id);
        setText("" + this.val);
        setPreferredSize(SIZE);
    }

    public int getVal() {
        return this.val;
    }

    public void setVal(int val) {
        this.val = val;
        setText("" + this.val);
    }

    public void resetVal() { this.val = 0; }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    private void setRowCol(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getBox() {
        return this.box;
    }

    private void setBox(int box) {
        this.box = box;
    }

    public int getId() { return this.id; }

    private void setId(int id) { this.id = id; }
}
