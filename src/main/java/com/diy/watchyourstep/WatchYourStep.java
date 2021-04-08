package com.diy.watchyourstep;

import com.diy.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class WatchYourStep extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int GRIDSIZE = 10;
    private static final int NUMBEROFHOLES = 10;
    private TerrainButton[][] terrain = new TerrainButton[GRIDSIZE][GRIDSIZE];
    private int totalRevealed = 0;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {

        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WatchYourStep();
            }
        });
    }

    private void newGame() {
        totalRevealed = 0;
        for (int r = 0; r < GRIDSIZE; r++) {
            for (int c = 0; c < GRIDSIZE; c++) {
                terrain[r][c].reset();
            }
        }
        setHoles();
    }

    public WatchYourStep() {
        initGUI();

        setHoles();

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initGUI() {
        TitleLabel titleLabel = new TitleLabel();
        titleLabel.setText("Watch Your Step");
        add(titleLabel, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(GRIDSIZE, GRIDSIZE));
        add(centerPanel, BorderLayout.CENTER);

        for (int row = 0; row < GRIDSIZE; row++) {
            for (int col = 0; col < GRIDSIZE; col++) {
                TerrainButton tb = new TerrainButton(row, col);
                tb.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            bigReveal(tb.getRow(), tb.getCol());
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            flag(tb.getRow(), tb.getCol());
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                terrain[row][col] = tb;
                centerPanel.add(terrain[row][col]);
            }
        }
    }

    private void setHoles() {
        Random rand = new Random();
        for (int i = 0; i < NUMBEROFHOLES; i++) {
            int pickRow = rand.nextInt(GRIDSIZE);
            int pickCol = rand.nextInt(GRIDSIZE);
            while (terrain[pickRow][pickCol].hasHole()) {
                pickRow = rand.nextInt(GRIDSIZE);
                pickCol = rand.nextInt(GRIDSIZE);
            }
            terrain[pickRow][pickCol].setHole(true);

            addToNeighborsHoleCount(pickRow, pickCol);
        }

    }

    private void addToNeighborsHoleCount(int row, int col) {
        ArrayList<int[]> valid = getValidNeighbors(row, col);
        for (int[] v: valid) {
            if (!terrain[v[0]][v[1]].hasHole()) {
                terrain[v[0]][v[1]].increaseHoleCount();
            }
        }
    }

    //reveals clicked button, and neighbors if it's blank
    private void bigReveal(int row, int col) {

        if (!terrain[row][col].isRevealed()) {
            terrain[row][col].reveal(true);
            terrain[row][col].setFont(new Font("Dialog", Font.BOLD, 12));
            totalRevealed += 1;
            if (terrain[row][col].hasHole()) {
                endGame(0);
            } else if (totalRevealed == (GRIDSIZE * GRIDSIZE) - NUMBEROFHOLES) {
                endGame(1);
            } else if (terrain[row][col].getText().equals("") && !terrain[row][col].hasHole()) {
                revealNeighbors(row, col);
            }
        }
    }

    //reveals valid neighbors
    private void revealNeighbors(int row, int col) {
        ArrayList<int[]> valid = getValidNeighbors(row, col);
        for (int[] v: valid) {
            if (
                !terrain[v[0]][v[1]].isRevealed() &&
                terrain[v[0]][v[1]].getText().equals("")
            ) {
                bigReveal(v[0], v[1]);
            }
        }
    }

    private ArrayList<int[]> getValidNeighbors(int row, int col) {
        ArrayList<int[]> valid = new ArrayList<int[]>();
        if (isValidNeighbor(row - 1, col)) {valid.add(new int[]{row - 1, col});}
        if (isValidNeighbor(row - 1, col - 1)) {valid.add(new int[]{row - 1, col - 1});}
        if (isValidNeighbor(row - 1, col + 1)) {valid.add(new int[]{row - 1, col + 1});}
        if (isValidNeighbor(row, col - 1)) {valid.add(new int[]{row, col - 1});}
        if (isValidNeighbor(row, col + 1)) {valid.add(new int[]{row, col + 1});}
        if (isValidNeighbor(row + 1, col - 1)) {valid.add(new int[]{row + 1, col - 1});}
        if (isValidNeighbor(row + 1, col)) {valid.add(new int[]{row + 1, col});}
        if (isValidNeighbor(row + 1, col + 1)) {valid.add(new int[]{row + 1, col + 1});}
        return valid;
    }

    private boolean isValidNeighbor(int row, int col) {
        return ((row >= 0) && (col >= 0) && (row < GRIDSIZE) && (col < GRIDSIZE));
    }

    //0 for lose, 1 for win
    private void endGame(int result) {
        int option = JOptionPane.showConfirmDialog(
                this,
                ((result == 0) ? "You fell in a hole!" : "You win!") + " Play again?");
        if (option == 0) {
            newGame();
        } else {
            System.exit(0);
        }
    }

    private void flag(int row, int col) {
        if (!terrain[row][col].isRevealed()) {
            terrain[row][col].setFont(new Font("Dialog", Font.BOLD, 16));
            terrain[row][col].setText("\u2620");

        }
    }
}
