package com.diy.zilch;

import com.diy.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class Zilch extends JFrame {
    public static final long SerialVersionUID = 1L;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {}
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Zilch();
            }
        });
    }

    public Zilch() {
        setTitle("Zilch");

        initGUI();

        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        TitleLabel tl = new TitleLabel();
        tl.setText("Zilch");
        add(tl, BorderLayout.PAGE_START);

        //main panel
        JPanel mainPanel = new JPanel();
        Die die = new Die();
        mainPanel.add(die);
        add(mainPanel, BorderLayout.CENTER);

        //score panel

        //dice row panel

        //points panel

        //dice panel

        //high score panel

        //button panel
    }
}
