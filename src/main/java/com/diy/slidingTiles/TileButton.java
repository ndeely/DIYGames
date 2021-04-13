package com.diy.slidingTiles;

import javax.swing.*;
import java.awt.*;

public class TileButton extends JButton {
    private static final long serialVersionUID = 1L;
    private static int tileSize = 0;
    private static int maxTiles = 0;
    private ImageIcon imageIcon;
    private int imageId = 0;
    private int row = 0;
    private int col = 0;

    public TileButton(ImageIcon imageIcon,
                      int imageId,
                      int row,
                      int col) {
        this.row = row;
        this.col = col;
        setImage(imageIcon, imageId);
        setBackground(Color.WHITE);
        setBorder(null);
        Dimension size = new Dimension(tileSize, tileSize);
        setPreferredSize(size);
        setFocusPainted(true);
    }

    public void setImage(ImageIcon imageIcon, int imageId) {
        this.imageIcon = imageIcon;
        this.imageId = imageId;
        if (imageId == maxTiles) {
            setIcon(null);
        } else {
            setIcon(imageIcon);
        }
    }

    private ImageIcon getImage() {
        return this.imageIcon;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getImageId() {
        return this.imageId;
    }

    public static void setTileSizeAndMaxTiles(int size, int max) {
        TileButton.tileSize = size;
        TileButton.maxTiles = max;
    }

    public boolean hasNoImage() {
        return getImage() == null;
    }

    public void swap(TileButton otherTile) {
        ImageIcon otherImageIcon = otherTile.getImage();
        int otherImageId = otherTile.getImageId();
        otherTile.setImage(this.imageIcon, this.imageId);
        setImage(otherImageIcon, otherImageId);
    }

    public void showImage() {
        setIcon(this.imageIcon);
    }
}
