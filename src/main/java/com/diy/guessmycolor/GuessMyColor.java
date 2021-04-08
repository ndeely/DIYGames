package com.diy.guessmycolor;

import com.diy.mycomponents.TitleLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import static javax.swing.JOptionPane.showMessageDialog;

public class GuessMyColor extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel samplePanel = new JPanel();
    private JPanel targetPanel = new JPanel();

    private int targetRed;
    private int targetGreen;
    private int targetBlue;

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

        //targetPanel.setBackground(Color.cyan);
        targetPanel.setPreferredSize(size);
        centerPanel.add(targetPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel, BorderLayout.PAGE_END);

        //create + and - colour buttons
        Font font = new Font("Dialog", Font.BOLD, 18);
        //red buttons
        JButton moreRedButton = new JButton("+");
        moreRedButton.setBackground(Color.RED);
        moreRedButton.setFont(font);
        moreRedButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("R", "+");
                }
            }
        );
        buttonPanel.add(moreRedButton);
        JButton lessRedButton = new JButton("-");
        lessRedButton.setBackground(Color.RED);
        lessRedButton.setFont(font);
        lessRedButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("R", "-");
                }
            }
        );
        buttonPanel.add(lessRedButton);
        //green buttons
        JButton moreGreenButton = new JButton("+");
        moreGreenButton.setBackground(Color.GREEN);
        moreGreenButton.setFont(font);
        moreGreenButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("G", "+");
                }
            }
        );
        buttonPanel.add(moreGreenButton);
        JButton lessGreenButton = new JButton("-");
        lessGreenButton.setBackground(Color.GREEN);
        lessGreenButton.setFont(font);
        lessGreenButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("G", "-");
                }
            }
        );
        buttonPanel.add(lessGreenButton);
        //blue buttons
        JButton moreBlueButton = new JButton("+");
        moreBlueButton.setBackground(Color.BLUE);
        moreBlueButton.setFont(font);
        moreBlueButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("B", "+");
                }
            }
        );
        buttonPanel.add(moreBlueButton);
        JButton lessBlueButton = new JButton("-");
        lessBlueButton.setBackground(Color.BLUE);
        lessBlueButton.setFont(font);
        lessBlueButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modifyColor("B", "-");
                }
            }
        );
        buttonPanel.add(lessBlueButton);
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
        if (mod.equals("+")) {
            if (color.equals("R")) {
                if (this.targetRed + 16 < 255) {
                    this.targetRed += 16;
                } else {
                    showMessageDialog(this, "Maximum red already!");
                }
            } else if (color.equals("G")) {
                if (this.targetGreen + 16 < 255) {
                    this.targetGreen += 16;
                } else {
                    showMessageDialog(this, "Maximum green already!");
                }
            } else {
                if (this.targetBlue + 16 < 255) {
                    this.targetBlue += 16;
                } else {
                    showMessageDialog(this, "Maximum blue already!");
                }
            }
        } else {
            if (color.equals("R")) {
                if (this.targetRed - 16 > 0) {
                    this.targetRed -= 16;
                } else {
                    showMessageDialog(this, "Minimum red already!");
                }
            } else if (color.equals("G")) {
                if (this.targetGreen - 16 > 0) {
                    this.targetGreen -= 16;
                } else {
                    showMessageDialog(this, "Minimum green already!");
                }
            } else {
                if (this.targetBlue - 16 > 0) {
                    this.targetBlue -= 16;
                } else {
                    showMessageDialog(this, "Minimum blue already!");
                }
            }
        }
        Color targetColor = new Color(targetRed, targetGreen, targetBlue);
        targetPanel.setBackground(targetColor);
    }
    
}
