package com.diy.slidingTiles;

import com.diy.mycomponents.TitleLabel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SlidingTiles extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final String FILENAME = "src\\main\\java\\com\\diy\\slidingTiles\\slidingTilesImage.jpg";
    private int tileSize = 50;
    private int gridSize = 4;
    private BufferedImage image = null;
    private TileButton[][] tile = new TileButton[gridSize][gridSize];
    private JPanel centerPanel = new JPanel();
    private ImageIcon missingImage = null;

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SlidingTiles();
            }
        });
    }

    public SlidingTiles() {
        try {
            this.image = ImageIO.read(new File(FILENAME));
            TileButton.setTileSizeAndMaxTiles(this.tileSize,
                    this.gridSize * this.gridSize);
            initGUI();
        } catch (IOException e) {
            String message = "The image file could not be opened.";
            JOptionPane.showMessageDialog(this, message);
        }
    }

    private void initGUI() {
        TitleLabel tl = new TitleLabel();
        tl.setText("Sliding Tiles");
        add(tl, BorderLayout.PAGE_START);

        // main panel
        divideImage();

        // button panel
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.PAGE_END);
        buttonPanel.setBackground(Color.BLACK);
        JButton scrambleButton = new JButton();
        scrambleButton.setText("Scramble");
        scrambleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        buttonPanel.add(scrambleButton);

        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void divideImage() {
        centerPanel.setLayout(new GridLayout(gridSize, gridSize));
        add(centerPanel, BorderLayout.CENTER);
        centerPanel.removeAll();

        int imageId = 0;
        for (int row = 0; row < tile.length; row++) {
            for (int col = 0; col < tile[0].length; col++) {
                int x = col * tileSize;
                int y = row * tileSize;
                BufferedImage subimage = image.getSubimage(x, y, tileSize, tileSize);
                ImageIcon imageIcon = new ImageIcon(subimage);

                tile[row][col] = new TileButton(imageIcon, imageId, row, col);
                if (row + 1 == tile.length && col + 1 == tile[0].length) {
                    this.missingImage = imageIcon;
                    tile[row][col] = new TileButton(null, imageId, row, col);
                }
                tile[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TileButton tb = (TileButton) e.getSource();
                        clickedTile(tb);
                    }
                });
                centerPanel.add(tile[row][col]);
                imageId++;
            }
        }
        centerPanel.revalidate();

        scramble();
    }

    private void clickedTile(TileButton clickedTile) {
        int row = clickedTile.getRow();
        int col = clickedTile.getCol();

        if (row > 0 && tile[row - 1][col].hasNoImage()) {
            clickedTile.swap(tile[row - 1][col]);
        } else if (col > 0 && tile[row][col - 1].hasNoImage()) {
            clickedTile.swap(tile[row][col - 1]);
        } else if (row + 1 < this.gridSize && tile[row + 1][col].hasNoImage()) {
            clickedTile.swap(tile[row + 1][col]);
        } else if (col + 1 < this.gridSize && tile[row][col + 1].hasNoImage()) {
            clickedTile.swap(tile[row][col + 1]);
        }
        if(imagesInOrder()) {
            tile[gridSize - 1][gridSize - 1].setImage(this.missingImage, gridSize * gridSize - 1);
        }
    }

    private void scramble() {
        int openRow = gridSize - 1;
        int openCol = gridSize - 1;
        Random rand = new Random();
        for (int i = 0; i < /*25 * gridSize*/2; i++) {
            int direction = rand.nextInt(4);
            TileButton openButton = tile[openRow][openCol];
            switch(direction) {
                case UP: if (openRow > 0) {
                    tile[openRow - 1][openCol].swap(openButton);
                    openRow--;
                }
                        break;
                case DOWN: if (openRow < gridSize - 1) {
                    tile[openRow + 1][openCol].swap(openButton);
                    openRow++;
                }
                    break;
                case LEFT: if (openCol > 0) {
                    tile[openRow][openCol - 1].swap(openButton);
                    openCol--;
                }
                    break;
                case RIGHT: if (openCol < gridSize - 1) {
                    tile[openRow][openCol + 1].swap(openButton);
                    openCol++;
                }
                    break;
            }
        }
    }

    private boolean imagesInOrder() {
        int id = 0;
        for (TileButton[] arr: tile) {
            for (TileButton button: arr) {
                if (button.getImageId() != id) {
                    return false;
                }
                id++;
            }
        }
        return true;
    }

    private void newGame() {
        divideImage();
        scramble();
    }


}
