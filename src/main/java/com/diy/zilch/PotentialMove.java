package com.diy.zilch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PotentialMove extends JPanel {
    private int[] diceIndexes;
    private int points;

    public PotentialMove(int[] diceIndexes, int points) {
        this.diceIndexes = diceIndexes;
        this.points = points;
    }

    public int[] getDiceIndexes() {
        return this.diceIndexes;
    }

    public int getPoints() {
        return this.points;
    }

    public void drawMove(Die[] dice) {
        for (int index: this.getDiceIndexes()) {
            Die d = new Die(dice[index].getValue());
            add(d);
        }
        JLabel potentialMovePointsLabel = new JLabel("" + this.getPoints() + " Points");
        add(potentialMovePointsLabel);
    }
}
