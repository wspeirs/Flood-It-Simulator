package com.bittrust.floodit.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ColorButton extends JButton implements ActionListener {

    private static final long serialVersionUID = -7981882862582714754L;
    
    private Board board;
    private Color color;

    public ColorButton(Board board, Color color) {
        super(" ");
        
        this.board = board;
        this.color = color;
        
        this.setBackground(color);
        this.addActionListener(this);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        int numChanged = board.flood(color);
        
        System.out.println("NUM CHANGED: " + numChanged);
    }
}
