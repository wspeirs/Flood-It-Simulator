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
        int changeCount = 0;
        
        Color[][] newBoard = new Color[HEIGHT][WIDTH];
        
        for(int h=0; h < HEIGHT; ++h) {
            for(int w=0; w < WIDTH; ++w) {
                Color c = board[h][w].getBackground();
                
                if(c.equals(currentColor) && reachHome(w,h)) {
                    newBoard[h][w] = color;
                } else if(c.equals(color) && 
                        ( reachHome(w-1,h) ||
                          reachHome(w,h-1) ||
                          reachHome(w+1,h) ||
                          reachHome(w,h+1))) {
                    changeCount++;
                    newBoard[h][w] = c;
                } else {
                    newBoard[h][w] = c;
                }
            }
        }
        
        currentColor = color;
        setBoard(newBoard);
        
        return changeCount;
    }
    
    public void setBoard(Color[][] colors) {
        for(int h=0; h < HEIGHT; ++h) {
            for(int w=0; w < WIDTH; ++w) {
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
    
    private boolean reachHome(int x, int y) {
        return reachHome(x, y, getColorBoard());
    }
    
    private boolean reachHome(int x, int y, Color[][] b) {
        if(x == 0 && y == 0)
            return true;
        
        if(x <= -1 || y <= -1 || x >= WIDTH || y >= HEIGHT)
            return false;
        
        Color c = new Color(b[y][x].getRGB());
        
        b[y][x] = Color.BLACK;

        if(y > 0 && b[y-1][x].equals(c))
            return reachHome(x, y-1, b);
        
        if(x > 0 && b[y][x-1].equals(c))
            return reachHome(x-1, y, b);
        
        if(y < HEIGHT-1 && b[y+1][x].equals(c))
            return reachHome(x, y+1, b);
        
        if(x < WIDTH-1 && b[y][x+1].equals(c))
            return reachHome(x+1,y, b);
        
        return false;
    }
   
    private JLabel generateLabel(Color color) {
        JLabel ret = new JLabel();
        
        ret.setOpaque(true);
        ret.setBackground(color);
        
        return ret;
    }
}
