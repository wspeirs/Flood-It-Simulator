package com.bittrust.floodit.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.math.random.MersenneTwister;

public class Board extends JPanel {

    public static final Color[] colorMap = { Color.MAGENTA, Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.RED };
    
    private static final long serialVersionUID = -8271212380727339087L;
    private static final int HEIGHT = 10;
    private static final int WIDTH = 10;
    
    
    private MersenneTwister rnd;
    private JLabel[][] board = new JLabel[HEIGHT][WIDTH];
    private Color currentColor;

    public Board(boolean blankFill) {
        rnd = new MersenneTwister();
        this.setLayout(new GridLayout(10,10));
        this.setBounds(0, 0, 150, 150);
        this.setVisible(true);

        if(blankFill) {
            for(int h=0; h < HEIGHT; ++h) {
                for(int w=0; w < WIDTH; ++w) {
                    JLabel label = new JLabel();
                    
                    label.setOpaque(true);

                    this.add(label);
                    board[h][w] = label;
                }
            }
        }
}
    
    public void randomlyFillBoard() {
        for(int h=0; h < HEIGHT; ++h) {
            for(int w=0; w < WIDTH; ++w) {
                JLabel label = generateLabel(colorMap[rnd.nextInt(colorMap.length)]);
                
                this.add(label);
                board[h][w] = label;
            }
        }
        
        currentColor = board[0][0].getBackground();
    }
    
    public Color getCurrentColor() {
        return currentColor;
    }
    
    /**
     * Floods the board with a given color, returning the number of new squares converted.
     * @param color The color to flood.
     * @return The number of new squares converted.
     */
    public int flood(Color color) {
        Color[][] newBoard = new Color[HEIGHT][WIDTH];
        
        int total = flood( color, 0, 0, newBoard, currentColor );       
        
        currentColor = color;
        setBoard(newBoard);
        
        return total;
    }

    private int flood(Color color, int row, int column, Color[][] newBoard, Color parentColor ) {
        if( row >= 0 && row < HEIGHT && column >= 0 && column < WIDTH && newBoard[row][column] == null ) {
            Color currentColor = board[row][column].getBackground();
            int count =  0;
            
            if( currentColor.equals( parentColor ) ) {          
                newBoard[row][column] = color;
                count++;
            
                count +=    flood( color, row + 1, column, newBoard, currentColor ) +           
                            flood( color, row - 1, column, newBoard, currentColor ) +
                            flood( color, row, column + 1, newBoard, currentColor ) +
                            flood( color, row, column - 1, newBoard, currentColor );
            }
                        
            return count;
        }
        else
            return 0;   
    }

    public void setBoard(Color[][] colors) {
        for(int h=0; h < HEIGHT; ++h) {
            for(int w=0; w < WIDTH; ++w) {
                if(colors[h][w] != null)
                    board[h][w].setBackground(colors[h][w]);
            }
        }
        
        this.updateUI();
    }
    
    public Color[][] getColorBoard() {
        Color[][] ret = new Color[HEIGHT][WIDTH];
        
        for(int h=0; h < HEIGHT; ++h) {
            for(int w=0; w < WIDTH; ++w) {
                ret[h][w] = new Color(board[h][w].getBackground().getRGB());
            }
        }
        
        return ret;
    }
    
    private JLabel generateLabel(Color color) {
        JLabel ret = new JLabel();
        
        ret.setOpaque(true);
        ret.setBackground(color);
        
        return ret;
    }
}
