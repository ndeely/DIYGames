package com.diy.guessmycolor;

import com.diy.mycomponents.TitleLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class GuessMyColor extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel samplePanel = new JPanel();
    private JPanel targetPanel = new JPanel();

    private int targetRed;
    private int targetGreen;
    private int targetBlue;

    private int sampleRed = 0;
    private int sampleGreen = 0;
    private int sampleBlue = 0;

    public static final void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {

        }

        EventQueue.invokeLater(new Runnable(){
            public void run() {
                new GuessMyColor();
            }
        });
    }

    public GuessMyColor() {
        setTitle("Guess My Color");
        setResizable(false);

        initGUI();

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        generateTargetColor();
    }

    private void initGUI() {

        TitleLabel titleLabel = new TitleLabel();
        titleLabel.setText("Guess My Color");
        add(titleLabel, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);

        Dimension size = new Dimension(50, 50);

        samplePanel.setBackground(Color.BLACK);
        samplePanel.setPreferredSize(size);
        centerPanel.add(samplePanel);

        targetPanel.setPreferredSize(size);
        centerPanel.add(targetPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel, BorderLayout.PAGE_END);

        //create + and - colour buttons
        ArrayList<JButton> colorButtons = new ArrayList<JButton>();

        //red buttons
        JButton moreRedButton = new JButton("+");
        moreRedButton.setBackground(Color.RED);
        moreRedButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("R", "+");
                }
            }
        );
        colorButtons.add(moreRedButton);
        JButton lessRedButton = new JButton("-");
        lessRedButton.setBackground(Color.RED);
        lessRedButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("R", "-");
                }
            }
        );
        colorButtons.add(lessRedButton);

        //green buttons
        JButton moreGreenButton = new JButton("+");
        moreGreenButton.setBackground(Color.GREEN);
        moreGreenButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("G", "+");
                }
            }
        );
        colorButtons.add(moreGreenButton);
        JButton lessGreenButton = new JButton("-");
        lessGreenButton.setBackground(Color.GREEN);
        lessGreenButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("G", "-");
                }
            }
        );
        colorButtons.add(lessGreenButton);

        //blue buttons
        JButton moreBlueButton = new JButton("+");
        moreBlueButton.setBackground(Color.BLUE);
        moreBlueButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("B", "+");
                }
            }
        );
        colorButtons.add(moreBlueButton);
        JButton lessBlueButton = new JButton("-");
        lessBlueButton.setBackground(Color.BLUE);
        lessBlueButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("B", "-");
                }
            }
        );
        colorButtons.add(lessBlueButton);

        Font font = new Font("Dialog", Font.BOLD, 18);
        for (JButton jb: colorButtons) {
            jb.setFont(font);
            jb.setFocusable(false);
            buttonPanel.add(jb);
        }
    }

    private void generateTargetColor() {
        Random rand = new Random();
        targetRed = rand.nextInt(18) * 15;
        targetGreen = rand.nextInt(18) * 15;
        targetBlue = rand.nextInt(18) * 15;
        
        Color targetColor = new Color(targetRed, targetGreen, targetBlue);
        targetPanel.setBackground(targetColor);
    }

    //color is "R", "G", or "B". mod is "+" or "-"
    private void modifyColor(String color, String mod) {
        switch (mod) {
            case "+":
                switch (color) {
                    case "R":
                        if (this.sampleRed + 16 < 256) {
                            this.sampleRed += 16;
                        } else {
                            showMessageDialog(this, "Maximum red already!");
                        }
                        break;
                    case "G":
                        if (this.sampleGreen + 16 < 256) {
                            this.sampleGreen += 16;
                        } else {
                            showMessageDialog(this, "Maximum green already!");
                        }
                        break;
                    case "B":
                        if (this.sampleBlue + 16 < 256) {
                            this.sampleBlue += 16;
                        } else {
                            showMessageDialog(this, "Maximum blue already!");
                        }
                        break;
                }
                break;
            case "-":
                switch (color) {
                    case "R":
                        if (this.sampleRed - 16 >= 0) {
                            this.sampleRed -= 16;
                        } else {
                            showMessageDialog(this, "Minimum red already!");
                        }
                        break;
                    case "G":
                        if (this.sampleGreen - 16 >= 0) {
                            this.sampleGreen -= 16;
                        } else {
                            showMessageDialog(this, "Minimum green already!");
                        }
                        break;
                    case "B":
                        if (this.sampleBlue - 16 >= 0) {
                            this.sampleBlue -= 16;
                        } else {
                            showMessageDialog(this, "Minimum blue already!");
                        }
                        break;
                }
                break;
        }
        Color sampleColor = new Color(sampleRed, sampleGreen, sampleBlue);
        samplePanel.setBackground(sampleColor);
        checkIfWin();
    }

    private void checkIfWin() {
        if (
            Math.abs(targetRed - sampleRed) < 16 &&
            Math.abs(targetGreen - sampleGreen) < 16 &&
            Math.abs(targetBlue - sampleBlue) < 16
        ) {
            int option = showConfirmDialog(this, "You win! Play again?");
            if (option == 0) {
                newGame();
            } else {
                System.exit(0);
            }
        }
    }

    private void newGame() {
        samplePanel.setBackground(Color.BLACK);
        generateTargetColor();
    }
    
}
