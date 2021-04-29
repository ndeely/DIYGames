package com.diy.MazeGenerator;

import com.diy.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator extends JFrame {
    private static final long serialVersionUID = 1L;
    private int rows = 30;
    private int cols = 30;
    private Cell[][] cells = new Cell[rows][cols];
    private JPanel mazePanel = new JPanel();

    private int row = 0;
    private int col = 0;
    private int endRow = this.rows - 1;
    private int endCol = this.cols - 1;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MazeGenerator();
            }
        });
    }

    public MazeGenerator() {
        initGUI();

        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        setTitle("Maze Generator");

        TitleLabel tl = new TitleLabel();
        tl.setText("Maze");
        add(tl, BorderLayout.PAGE_START);

        //center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        add(centerPanel, BorderLayout.CENTER);

        //maze panel
        newMaze();
        centerPanel.add(mazePanel);

        //button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel, BorderLayout.PAGE_END);
        JButton newMazeButton = new JButton();
        newMazeButton.setText("New Maze");
        newMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newMaze();
            }
        });
        newMazeButton.setFocusable(false);
        buttonPanel.add(newMazeButton);

        //listeners
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                moveBall(e.getKeyCode());
            }
        });

        //show instructions
        JOptionPane.showMessageDialog(
                this,
                "Move the green ball through the maze to reach the red ball.\n" +
                        "Movement\n" +
                        "Up: 'W' or Up Arrow Key\n" +
                        "Down: 'S' or Down Arrow Key\n" +
                        "Left: 'A' or Left Arrow Key\n" +
                        "Right: 'D' or Right Arrow Key\n"
        );
    }

    private void newMaze() {
        mazePanel.removeAll();
        mazePanel.setLayout(new GridLayout(this.rows, this.cols));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Cell(row, col);
                mazePanel.add(cells[row][col]);
            }
        }
        generateMaze();

        this.row = 0;
        this.col = 0;
        this.endRow = this.rows - 1;
        this.endCol = this.cols - 1;
        this.cells[this.row][this.col].setCurrent(true);
        this.cells[this.endRow][this.endCol].setEnd(true);

        mazePanel.revalidate();
    }

    private void generateMaze() {
        ArrayList<Cell> tryLater = new ArrayList<Cell>();
        int totalCells = this.rows * this.cols;
        int visitedCells = 1;
        Random rand = new Random();
        int r = rand.nextInt(this.rows);
        int c = rand.nextInt(this.cols);

        while(visitedCells < totalCells) {
            // find neighbors with all walls intact
            ArrayList<Cell> neighbors = new ArrayList<Cell>();
            if (isAvailable(r - 1, c)) {
                neighbors.add(cells[r - 1][c]);
            }
            if (isAvailable(r + 1, c)) {
                neighbors.add(cells[r + 1][c]);
            }
            if (isAvailable(r, c - 1)) {
                neighbors.add(cells[r][c - 1]);
            }
            if (isAvailable(r, c + 1)) {
                neighbors.add(cells[r][c + 1]);
            }

            // if one or more was found
            if (neighbors.size() > 0) {
                // if more than one was found, add this cell to the list to try again
                if (neighbors.size() > 1) {
                    tryLater.add(cells[r][c]);
                }
                // pick a neighbor to remove the wall
                Cell neighbor = neighbors.get(rand.nextInt(neighbors.size()));
                cells[r][c].openTo(neighbor);

                // go to the neighbor and increment visited
                r = neighbor.getRow();
                c = neighbor.getCol();
                visitedCells++;
            } else {
                // go to a previously saved cell
                Cell nextCell = tryLater.remove(0);
                r = nextCell.getRow();
                c = nextCell.getCol();
            }
        }
    }

    private boolean isAvailable(int r, int c) {
        return r >= 0 &&
                c >= 0 &&
                r < this.rows &&
                c < this.cols &&
                cells[r][c].hasAllWalls();
    }

    private void moveBall(int direction) {
        switch(direction) {
            //up
            case 38:
            case 87:
                if (!cells[this.row][this.col].isWall(Cell.TOP)) {
                    this.moveTo(this.row - 1, this.col, Cell.TOP, Cell.BOTTOM);
                }
                break;
            //right
            case 39:
            case 68:
                if (!cells[this.row][this.col].isWall(Cell.RIGHT)) {
                    this.moveTo(this.row, this.col + 1, Cell.RIGHT, Cell.LEFT);
                }
                break;
            //down
            case 40:
            case 83:
                if (!cells[this.row][this.col].isWall(Cell.BOTTOM)) {
                    this.moveTo(this.row + 1, this.col, Cell.BOTTOM, Cell.TOP);
                }
                break;
            //left
            case 37:
            case 65:
                if (!cells[this.row][this.col].isWall(Cell.LEFT)) {
                    this.moveTo(this.row, this.col - 1, Cell.LEFT, Cell.RIGHT);
                }
                break;
        }
        checkIfWin();
    }

    private void moveTo(int nextRow, int nextCol, int firstDirection, int secondDirection) {
        cells[this.row][this.col].setCurrent(false);
        cells[this.row][this.col].addPath(firstDirection);
        cells[nextRow][nextCol].setCurrent(true);
        cells[nextRow][nextCol].addPath(secondDirection);
        this.row = nextRow;
        this.col = nextCol;
    }

    private void checkIfWin() {
        if (this.row == this.endRow && this.col == this.endCol) {
            JOptionPane.showMessageDialog(this,"Congratulations, you win!");
        }
    }
}
