package com.diy.framed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LightButton extends JButton {
    private static final long serialVersionUID = 1L;

    Dimension SIZE = new Dimension(50, 50);

    private int row = 0;
    private int col = 0;

    public LightButton(int row, int col) {
        this.row = row;
        this.col = col;
        setBackground(Color.BLACK);
        setPreferredSize(SIZE);
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void toggle() {
        if (isLit()) {
            setBackground(Color.BLACK);
        } else {
            setBackground(Color.CYAN);
        }
    }

    public boolean isLit() {
        return getBackground().equals(Color.CYAN);
    }

}
