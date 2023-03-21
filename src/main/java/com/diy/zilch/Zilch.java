package com.diy.zilch;

import com.diy.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Zilch extends JFrame {
    public static final long SerialVersionUID = 1L;

    private int points = 0;
    private int newPoints = 0;
    private int score = 0;
    private int round = 1;
    private int lastRound = 5;
    private int highScore = 0;
    private static final String HIGHSCORETEXT = "The previous high score was ";
    private static final String FILENAME = "zilch.txt";

    private JLabel pointsLabel = new JLabel("" + this.points);
    private JLabel scoreLabel = new JLabel("" + this.score);
    private JLabel roundLabel = new JLabel("" + this.round);
    private JLabel highScoreLabel = new JLabel();

    private Font smallFont = new Font(Font.DIALOG, Font.PLAIN, 12);
    private Font bigFont = new Font(Font.DIALOG, Font.BOLD, 36);

    private JButton rollButton = new JButton("Roll");
    private JButton endRoundButton = new JButton("End Round");

    private Die[] dice = new Die[6];
    private ArrayList<PotentialMove> potentialMoves = new ArrayList<PotentialMove>();
    private JPanel potentialMovesPanel = new JPanel();

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
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        //score panel
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.CYAN);
        scorePanel.setLayout(new FlowLayout()); // arrange items in a row

        JLabel roundTitleLabel = new JLabel("Round: ");
        roundTitleLabel.setFont(smallFont);
        scorePanel.add(roundTitleLabel);

        roundLabel.setFont(bigFont);
        scorePanel.add(roundLabel);

        JLabel scoreTitleLabel = new JLabel("Score: ");
        scoreTitleLabel.setFont(smallFont);
        scorePanel.add(scoreTitleLabel);

        scoreLabel.setFont(bigFont);
        scorePanel.add(scoreLabel);

        mainPanel.add(scorePanel);

        //dice row panel
        JPanel diceRowPanel = new JPanel();
        diceRowPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(diceRowPanel);

        //points panel
        JPanel pointsPanel = new JPanel();
        pointsPanel.setBackground(Color.LIGHT_GRAY);
        pointsPanel.setLayout(new BoxLayout(pointsPanel, BoxLayout.Y_AXIS));
        diceRowPanel.add(pointsPanel);

        JLabel pointsTitleLabel = new JLabel("Points:");
        pointsTitleLabel.setFont(smallFont);
        pointsPanel.add(pointsTitleLabel);

        pointsLabel.setFont(bigFont);
        pointsPanel.add(pointsLabel);

        Dimension size = new Dimension(70,70);
        pointsPanel.setPreferredSize(size);
        pointsTitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        pointsLabel.setAlignmentX(CENTER_ALIGNMENT);

        //dice panel
        JPanel dicePanel = new JPanel();
        dicePanel.setBackground(Color.LIGHT_GRAY);
        diceRowPanel.add(dicePanel);

        for(int i = 0; i < 6; i++) {
            dice[i] = new Die();
            dice[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    clickedDie();
                }
            });
            dicePanel.add(dice[i]);
        }

        // potential moves panel
        potentialMovesPanel.setLayout(new BoxLayout(potentialMovesPanel, BoxLayout.Y_AXIS));
        potentialMovesPanel.setAlignmentX(CENTER_ALIGNMENT);
        add(potentialMovesPanel, BorderLayout.EAST);

        setPotentialMoves();

        //high score panel
        getPreviousHighScore();
        this.highScoreLabel.setText(HIGHSCORETEXT + this.highScore);
        this.highScoreLabel.setFont(smallFont);
        JPanel highScorePanel = new JPanel();
        highScorePanel.add(highScoreLabel);
        mainPanel.add(highScorePanel);

        //button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel, BorderLayout.PAGE_END);

        rollButton.setEnabled(false);
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePoints();
                rollRemainingDice();
            }
        });
        buttonPanel.add(rollButton);

        endRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endRound();
            }
        });
        buttonPanel.add(endRoundButton);
    }

    private boolean isValidSelection() {
        int[] count = {0,0,0,0,0,0};
        boolean valid = true;
        this.newPoints = 0;

        for (Die die: this.dice) {
            if (die.isSelected()) {
                count[die.getValue() - 1]++;
            }
        }
        calcNewPoints(count);
        if (this.newPoints == 0) { valid = false; }
        int countOnes = 0;
        for (int i = 0; i < count.length; i++) {
            if ((i != 0 && i != 4) && count[i] > 0 && count[i] < 3) {
                valid = false;
            }
            if (count[i] == 1) { countOnes++; }
        }
        if (countOnes == 6) { valid = true; }

        return valid;
    }

    private void calcNewPoints(int[] counts) {
        int countOnes = 0;
        for (int i = 0; i < 6; i++) {
            switch(counts[i]) {
                case 1:
                    countOnes++;
                case 2:
                    switch(i) {
                        case 0:
                            this.newPoints += 10 * counts[i]; // 1 or 2 ones
                            break;
                        case 4:
                            this.newPoints += 5 * counts[i]; // 1 or 2 fives
                            break;
                    }
                case 3:
                    switch(i) {
                        case 0:
                            this.newPoints += 100; // 3 ones
                            break;
                        case 4:
                            this.newPoints += 50; // 3 fives
                            break;
                        default:
                            this.newPoints += 10 * i; // 3 of other value
                            break;
                    }
                case 4:
                    this.newPoints += 200;
                    break;
                case 5:
                    this.newPoints += 300;
                    break;
                case 6:
                    this.newPoints += 500;
                    break;
            }
        }
        if (countOnes == 6) { this.newPoints = 250; }
    }

    private void clickedDie() {
        if (isValidSelection()) {
            rollButton.setEnabled(true);
            endRoundButton.setEnabled(false);
        } else {
            rollButton.setEnabled(false);
            endRoundButton.setEnabled(true);
        }

        pointsLabel.setText("" + (this.points + this.newPoints));
    }

    private void updatePoints() {
        this.points += this.newPoints;
        this.newPoints = 0;
    }

    private void rollRemainingDice() {
        int count = 0;
        for (Die die: dice) {
            if (die.isSelected()) {
                die.hold();
            }
            if (die.isAvailable()) {
                die.roll();
                count++;
            }
        }
        rollButton.setEnabled(false);
        endRoundButton.setEnabled(true);
        if (count == 0) {
            rollAllDice();
        }
        setPotentialMoves();
    }

    private void rollAllDice() {
        for (Die die: dice) {
            die.roll();
            die.makeAvailable();
            rollButton.setEnabled(false);
        }
    }

    private void endRound() {
        if (isValidSelection()) {
            this.points += this.newPoints;
        }
        this.score += this.points;
        this.points = 0;
        pointsLabel.setText("" + this.points);
        scoreLabel.setText("" + this.score);
        this.newPoints = 0;
        roundLabel.setText("" + ++this.round);
        rollAllDice();
        rollButton.setEnabled(false);
        endRoundButton.setEnabled(true);
        revalidate();

        if (this.round == this.lastRound) {
            String message = "Score: " + this.score + ". Play again?";
            // update high score
            if (this.score > this.highScore) {
                message = "New high score: " + this.score + "! " + message;
                saveScore();
            }

            // ask to play again
            int option = JOptionPane.showConfirmDialog(this, message);
            if (option == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
        setPotentialMoves();
    }

    private void resetGame() {
        getPreviousHighScore();
        this.highScoreLabel.setText(HIGHSCORETEXT + this.highScore);
        this.score = 0;
        scoreLabel.setText("" + this.score);
        this.round = 1;
        roundLabel.setText("" + this.round);
        rollAllDice();
        setPotentialMoves();
    }

    private void getPreviousHighScore() {
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(new File(FILENAME))
            );
            String s = in.readLine();
            this.highScore = Integer.parseInt(s);
            in.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "There was a problem retrieving the high score.");
        }
    }

    private void saveScore() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(new File(FILENAME)));
            out.write("" + this.score);
            out.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"There was an error saving your high score.");
        }
    }

    // set list of potential moves with remaining dice
    private void setPotentialMoves() {
        potentialMovesPanel.removeAll();
        JLabel pmpTitleLabel = new JLabel("Potential Moves");
        potentialMovesPanel.add(pmpTitleLabel);
        this.potentialMoves = new ArrayList<PotentialMove>();
        int[] diceCounts = getDiceCounts();
        calcPotentialMoves(diceCounts);
        showPotentialMoves();
    }

    private int[] getDiceCounts() {
        int[] count = {0,0,0,0,0,0};

        for (Die die: this.dice) {
            if (!die.isHeld()) {
                int value = die.getValue();
                count[value - 1]++;
            }
        }
        return count;
    }

    private void calcPotentialMoves(int[] counts) {
        int countOnes = 0;
        for (int i = 0; i < 6; i++) {
            switch(counts[i]) {
                case 1:
                    countOnes++;
                    if (i == 0) {
                        this.potentialMoves.add(
                            new PotentialMove(
                                getIndexes(counts[i], i + 1),
                                10 * counts[i]
                            )
                        );
                    } // 1 one
                    if (i == 4) {
                        this.potentialMoves.add(
                            new PotentialMove(
                                getIndexes(counts[i], i + 1),
                                5 * counts[i]
                            )
                        );
                    } // 1 five
                    break;
                case 2:
                    if (i == 0) {
                        this.potentialMoves.add(
                            new PotentialMove(
                                getIndexes(counts[i], i + 1),
                                10 * counts[i]
                            )
                        );
                    } // 1 or 2 ones
                    if (i == 4) {
                        this.potentialMoves.add(
                            new PotentialMove(
                                getIndexes(counts[i], i + 1),
                                5 * counts[i]
                            )
                        );
                    } // 1 or 2 fives
                    break;
                case 3:
                    if (i == 0) {
                        this.potentialMoves.add(
                            new PotentialMove(
                                getIndexes(counts[i], i + 1),
                                100
                            )
                        );
                    } // 3 ones
                    else {
                        this.potentialMoves.add(
                            new PotentialMove(
                                getIndexes(counts[i], i + 1),
                                10 * i
                            )
                        );
                    } // 3 of other value
                    break;
                case 4:
                    this.potentialMoves.add(
                        new PotentialMove(
                            getIndexes(counts[i], i + 1),
                            200
                        )
                    );
                    break;
                case 5:
                    this.potentialMoves.add(
                        new PotentialMove(
                            getIndexes(counts[i], i + 1),
                                300
                        )
                    );
                    break;
                case 6:
                    this.potentialMoves.add(
                        new PotentialMove(
                            getIndexes(counts[i], i + 1),
                            500
                        )
                    );
                    break;
            }
        }
        if (countOnes == 6) {
            this.potentialMoves.add(
                new PotentialMove(
                    new int[]{0,1,2,3,4,5},
                    250
                )
            );
        }
        this.potentialMoves.sort(new Comparator<PotentialMove>() {
            @Override
            public int compare(PotentialMove o1, PotentialMove o2) {
                return o2.getPoints() - o1.getPoints();
            }
        });
    }

    // get count indexes of dice with value n
    private int[] getIndexes(int count, int n) {
        int[] indexes = new int[count];
        int counter = 0;
        for(int i = 0; i < this.dice.length; i++) {
            if (!dice[i].isHeld() && dice[i].getValue() == n) {
                indexes[counter] = i;
                counter++;
            }
        }
        return indexes;
    }

    private void showPotentialMoves() {
        if (this.potentialMoves.size() == 0) {
            JLabel noneLabel = new JLabel("None");
            noneLabel.setFont(bigFont);
            potentialMovesPanel.add(noneLabel);
        } else {
            for (int i = 0; i < this.potentialMoves.size(); i++) {
                this.potentialMoves.get(i).drawMove(this.dice);
                this.potentialMoves.get(i).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        potentialMoveClicked();
                    }
                });
                potentialMovesPanel.add(this.potentialMoves.get(i));
            }
        }
        revalidate();
        pack();
    }

    private void potentialMoveClicked() {
        for (Die d: dice) {
            if (d.isSelected()) {
                d.makeAvailable();
            }
        }
        for (PotentialMove pm: this.potentialMoves) {
            if (pm.isSelected()) {
                for (int index: pm.getDiceIndexes()) {
                    if (dice[index].isAvailable()) {
                        dice[index].select();
                    }
                }
            }
        }
        clickedDie();
    }
}
