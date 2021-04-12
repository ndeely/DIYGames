package com.diy.mycomponents;

import javax.swing.*;
import java.awt.*;

public class TitleLabel extends JLabel {
    public TitleLabel() {
        Font font = new Font("Serif", Font.BOLD, 32);
        setFont(font);
        setBackground(Color.WHITE);
        setForeground(Color.BLUE);
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
        setText("title");
    }


}
