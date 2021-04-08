package com.diy.framed;

import com.diy.guessmycolor.GuessMyColor;
import com.diy.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import static javax.swing.JOptionPane.showConfirmDialog;

public class Framed extends JFrame {
    private static final long serialVersionUID = 1L;
    final int GRIDSIZE = 3;
    private LightButton[][] lightButtons = new LightButton[GRIDSIZE][GRIDSIZE];
    private boolean[][] lightsState = new boolean[GRIDSIZE][GRIDSIZE];
    private boolean loaded = false;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {

        }

        EventQueue.invokeLater(new Runnable(){
            public void run() {
                new Framed();
            }
        });
    }

    public Framed() {
        newGame();
    }

    public void initGUI() {
        TitleLabel t = new TitleLabel();
        t.setText("Framed");
        add(t, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(GRIDSIZE, GRIDSIZE));
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                LightButton lightButton = new LightButton(i, j);
                lightButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        toggleLights(lightButton.getRow(), lightButton.getCol());
                        checkWin();
                    }
                });
                lightButtons[i][j] = lightButton;
                centerPanel.add(lightButtons[i][j]);
            }
        }
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton newGame = new JButton();
        newGame.setText("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        JButton reset = new JButton();
        reset.setText("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        bottomPanel.add(newGame);
        bottomPanel.add(reset);
        add(bottomPanel, BorderLayout.PAGE_END);

    }

    public void toggleLights(int row, int col) {
        lightButtons[row][col].toggle();
        if (row - 1 >= 0) {
            lightButtons[row - 1][col].toggle();
        }
        if (row + 1 < GRIDSIZE) {
            lightButtons[row + 1][col].toggle();
        }
        if (col - 1 >= 0) {
            lightButtons[row][col - 1].toggle();
        }
        if (col + 1 < GRIDSIZE) {
            lightButtons[row][col + 1].toggle();
        }
    }

    private void setupGame() {
        initGUI();

        setTitle("Framed");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        pack();
        setLocationRelativeTo(null);
    }

    private void doRandomMoves() {
        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            int row = rand.nextInt(3);
            int col = rand.nextInt(3);
            toggleLights(row, col);
        }
        saveLights();
    }

    private void saveLights() {
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                lightsState[i][j] = lightButtons[i][j].isLit();
            }
        }
    }

    private void loadLights() {
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                if (lightsState[i][j] != lightButtons[i][j].isLit()) {
                    lightButtons[i][j].toggle();
                }
            }
        }
    }

    private void newGame() {
        if (!loaded) {
            setupGame();
            loaded = true;
            doRandomMoves();
            this.setVisible(true);
        } else {
            doRandomMoves();
            loadLights();
        }
    }

    private void reset() {
        loadLights();
        this.setVisible(true);
    }

    private void checkWin() {
        int count = 0;
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                if (lightButtons[i][j].isLit()) {
                    count++;
                }
            }
        }
        if(count == 9) {
            int option = showConfirmDialog(
                    this,
                    "Congratulations, you are a winner! Do you want to play again?"
            );
            if (option == 0) {
                newGame();
            } else if (option == 1) {
                System.exit(0);
            }
        }

    }
}
