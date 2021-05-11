package com.diy.Sudoku;

import com.diy.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;
    private Box[] boxes = new Box[9];
    private int squaresPopulated = 0;

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
        populateBoard();

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

        JPanel board = new JPanel();
        board.setLayout(new GridLayout(3,3));
        for(int i = 0; i < 9; i++) {
            Box box = new Box(i);
            boxes[i] = box;
            board.add(boxes[i]);
        }

        add(board, BorderLayout.CENTER);
    }

    private void populateBoard() {
        Random rand = new Random();
        while (this.squaresPopulated <= 81) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {

                    if (getSquareVal(row, col) == 0) {
                        int r = rand.nextInt(9) + 1;
                        if (canAddVal(row, col, r)) {
                            setSquareVal(row, col, r);
                            this.squaresPopulated++;
                        } else {
                            for (int i = 1; i <= 9; i++) {
                                if (canAddVal(row, col, r)) {
                                    setSquareVal(row, col, r);
                                    this.squaresPopulated++;
                                } else if (i == 9) {
                                    for (Box box: this.boxes) {
                                        for (Square sq: box.squares) {
                                            if (
                                                (sq.getRow() == row && sq.getVal() == r) ||
                                                (sq.getCol() == col && sq.getVal() == r) ||
                                                (sq.getBox() == getSquare(row, col).getBox() && sq.getVal() == r)
                                            ) {
                                                sq.resetVal();
                                                this.squaresPopulated--;
                                            }
                                        }
                                    }
                                    setSquareVal(row, col, r);
                                    this.squaresPopulated++;
                                }
                            }
                        }
                    }
                }
            }
        }
        revalidate();
    }

    private Square getSquare(int row, int col) {
        for (Box b: boxes) {
            for (Square sq: b.squares) {
                if (sq.getRow() == row && sq.getCol() == col) {
                    return this.boxes[sq.getBox()].squares[sq.getId()];
                }
            }
        }
        return null;
    }

    private int getSquareVal(int row, int col) {
        return getSquare(row, col).getVal();
    }

    private void setSquareVal(int row, int col, int val) {
        getSquare(row, col).setVal(val);
    }

    private boolean canAddVal(int row, int col, int val) {
        for (Box box: this.boxes) {
            for (Square sq: box.squares) {
                if (
                    (sq.getRow() == row && sq.getVal() == val) ||
                    (sq.getCol() == col && sq.getVal() == val) ||
                    (sq.getBox() == getSquare(row, col).getBox() && sq.getVal() == val)
                ) {
                    return false;
                }
            }
        }
        return true;
    }
}
