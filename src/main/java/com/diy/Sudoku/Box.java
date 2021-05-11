package com.diy.Sudoku;

import javax.swing.JPanel;
import java.awt.*;

public class Box extends JPanel {
    private static final long SerialVersionUID = 1L;
    public Square[] squares = new Square[9];

    private final int SIZE = 3;
    private final int ID;

    public Box(int id) {
        this.ID = id;
        initGUI();
    }

    private void initGUI() {
        setLayout(new GridLayout(SIZE, SIZE));
        // build a 3x3 box of squares
        for (int i = 0; i < 9; i++) {
            int row = this.ID % 3 * 3 + i % 3;
            int col = (this.ID / 3) * 3 + i / 3;
            Square sq = new Square(row, col, this.ID, i);
            squares[i] = sq;
            add(squares[i]);
        }
    }

    public int getBoxId() {
        return this.ID;
    }

    public Square[] getSquares() {
        return this.squares;
    }
}
