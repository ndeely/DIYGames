
package com.diy.wizardofyesno;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.EventQueue;

public class WizardOfYesNo extends JFrame {

    private static final long serialVersionUID = 1L;
    public static final String[] ANSWER = {
        "Yes",
        "Sure why not?",
        "No",
        "Bad idea, broski ¬,¬"
    };

    public static void main(String[] args) {
        try {
            String classname = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(classname);
        } catch (Exception e) {
            
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WizardOfYesNo();
            }
        });
    }
    
    public WizardOfYesNo() {
        //double rand = Math.random();
        int numberOfAnswers = ANSWER.length;
        int pick = new Random().nextInt(numberOfAnswers);
        JLabel label = new JLabel();
        String answer = ANSWER[pick];

        label.setText(answer);
        Font font = new Font(Font.SERIF, Font.BOLD, 32);
        label.setFont(font);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        //setSize(200,100);
        setTitle("Wizard of Yes/No");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
