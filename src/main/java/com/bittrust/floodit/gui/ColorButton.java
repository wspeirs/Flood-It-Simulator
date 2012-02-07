package com.bittrust.floodit.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ColorButton extends JButton implements ActionListener {

    private static final long serialVersionUID = -7981882862582714754L;
    
    private Board curBoard;
    private Board lastBoard;
    private Color color;
    static private int stepNum = 0;

    public ColorButton(Board curBoard, Board lastBoard, Color color) {
        super(" ");
        
        this.curBoard = curBoard;
        this.lastBoard = lastBoard;
        this.color = color;
        
        this.setBackground(color);
        this.addActionListener(this);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        Color[][] curColorBoard = curBoard.getColorBoard();
        
        int numChanged = curBoard.flood(color);
        
        stepNum++;
        
        if(stepNum > 1) {
            lastBoard.setBoard(curColorBoard);
        }
        
        curBoard.printColorCount();
        System.out.println();
    }
}
