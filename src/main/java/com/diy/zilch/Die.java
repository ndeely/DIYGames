package com.diy.zilch;

import javax.swing.*;
import java.awt.*;

public class Die extends JPanel {
    private static final long SerialVersionUID = 1L;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    private static final int STATE_AVAILABLE = 0;
    private static final int STATE_SELECTED = 1;
    private static final int STATE_HELD = 2;
    private int value = 1;
    private int state = STATE_AVAILABLE;

    public Die() {
        setPreferredSize(getPreferredSize());
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
    }
}
