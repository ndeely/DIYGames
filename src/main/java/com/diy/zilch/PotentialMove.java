package com.diy.zilch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PotentialMove extends JPanel {
    private static final long SerialVersionUID = 1L;

    private int[] diceIndexes;
    private int points;

    private static final int STATE_AVAILABLE = 0;
    private static final int STATE_SELECTED = 1;
    private int state = STATE_AVAILABLE;

    public PotentialMove(int[] diceIndexes, int points) {
        this.diceIndexes = diceIndexes;
        this.points = points;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                click();
            }
        });
    }

    public boolean isSelected() {
        return this.state == STATE_SELECTED;
    }

    public void select() {
        this.state = STATE_SELECTED;
    }

    public void unselect() {
        this.state = STATE_AVAILABLE;
    }

    public int[] getDiceIndexes() {
        return this.diceIndexes;
    }

    public int getPoints() {
        return this.points;
    }

    public void paintComponent(Graphics g) {
        setSize(new Dimension(300, 40));
        //fill background
        switch(state) {
            case STATE_AVAILABLE:
                g.setColor(Color.WHITE);
                break;
            case STATE_SELECTED:
                g.setColor(Color.GREEN);
                break;
        }
        g.fillRect(0,0, this.getWidth(), this.getHeight());
    }

    public void drawMove(Die[] dice) {
        for (int index: this.getDiceIndexes()) {
            BasicDie d = new BasicDie(dice[index].getValue(), 30);
            add(d);
        }
        JLabel potentialMovePointsLabel = new JLabel("" + this.getPoints() + " Points");
        add(potentialMovePointsLabel);
    }

    private void click() {
        this.state = this.state == STATE_SELECTED ? STATE_AVAILABLE : STATE_SELECTED;
        repaint();
    }
}
