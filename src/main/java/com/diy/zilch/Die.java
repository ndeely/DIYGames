package com.diy.zilch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Die extends JPanel {
    private static final long SerialVersionUID = 1L;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    private static final int STATE_AVAILABLE = 0;
    private static final int STATE_SELECTED = 1;
    private static final int STATE_HELD = 2;
    private int value = 6;
    private int state = STATE_AVAILABLE;
    private Random rand = new Random();

    public Die() {
        roll();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                click();
            }
        });
    }

    public Die(int value) {
        this.value = value;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                click();
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public void paintComponent(Graphics g) {
        //fill background
        switch(state) {
            case STATE_AVAILABLE:
                g.setColor(Color.WHITE);
                break;
            case STATE_SELECTED:
                g.setColor(Color.RED);
                break;
            case STATE_HELD:
                g.setColor(Color.LIGHT_GRAY);
                break;
        }
        g.fillRect(0,0, WIDTH, HEIGHT);

        //draw border
        g.setColor(Color.BLACK);
        g.drawRect(0,0,WIDTH - 1, HEIGHT - 1);

        //draw dots
        switch (this.value) {
            case 5:
                drawDot(g, WIDTH / 4, HEIGHT / 4);
                drawDot(g, 3 * WIDTH / 4, 3 * HEIGHT / 4);
            case 3:
                drawDot(g, WIDTH / 4, 3 * HEIGHT / 4);
                drawDot(g, 3 * WIDTH / 4, HEIGHT / 4);
            case 1:
                drawDot(g, WIDTH / 2, HEIGHT / 2);
                break;
            case 6:
                drawDot(g, WIDTH / 2, HEIGHT / 4);
                drawDot(g, WIDTH / 2, 3 * HEIGHT / 4);
            case 4:
                drawDot(g, WIDTH / 4, HEIGHT / 4);
                drawDot(g, 3 * WIDTH / 4, 3 * HEIGHT / 4);
            case 2:
                drawDot(g, WIDTH / 4, 3 * HEIGHT / 4);
                drawDot(g, 3 * WIDTH / 4, HEIGHT / 4);
                break;
        }
    }

    private void drawDot(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.fillOval(x - 5, y - 5, 10, 10);
    }

    public int roll() {
        this.value = rand.nextInt(6) + 1;
        repaint();
        return this.value;
    }

    public void click() {
        this.state = this.state == STATE_SELECTED ? STATE_AVAILABLE : this.state == STATE_AVAILABLE ? STATE_SELECTED : this.state;
        repaint();
    }

    public boolean isAvailable() {
        return this.state == STATE_AVAILABLE;
    }

    public boolean isSelected() {
        return this.state == STATE_SELECTED;
    }

    public boolean isHeld() {
        return this.state == STATE_HELD;
    }

    public int getValue() {
        return this.value;
    }

    public void hold() {
        this.state = STATE_HELD;
        repaint();
    }

    public void makeAvailable() {
        this.state = STATE_AVAILABLE;
        repaint();
    }
}
