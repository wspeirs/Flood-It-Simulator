package com.bittrust.floodit.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections.map.UnmodifiableMap;
import org.apache.commons.math.random.MersenneTwister;

public class Board extends JPanel {

    public static final Color[] colorMap = { Color.MAGENTA, Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.RED };
    
    private static final long serialVersionUID = -8271212380727339087L;
    private static final int HEIGHT = 10;
    private static final int WIDTH = 10;
    
    
    private MersenneTwister rnd;
    private JLabel[][] board = new JLabel[HEIGHT][WIDTH];
    private Color currentColor;
    private Map<Color, Integer> colorCount;

    public Board(boolean blankFill) {
        this.rnd = new MersenneTwister();
        
        this.setLayout(new GridLayout(10,10));
        this.setBounds(0, 0, 150, 150);
        this.setVisible(true);
        
        this.colorCount = new HashMap<Color, Integer>();
        
        // add them all, and set them all to zero
        for(Color c:colorMap) {
            colorCount.put(c, 0);
        }
        
        // set our flood area counter
        colorCount.put(Color.BLACK, 1);

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
                Color c = colorMap[rnd.nextInt(colorMap.length)];
                JLabel label = generateLabel(c);
                
                colorCount.put(c, colorCount.get(c) + 1);
                this.add(label);
                board[h][w] = label;
            }
        }
        
        currentColor = board[0][0].getBackground();
    }
    
    public Color getCurrentColor() {
        return currentColor;
    }
    
    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }
    
    public Map<Color, Integer> getColorCount() {
        return UnmodifiableMap.decorate(colorCount);
    }
    
    public boolean isGameOver() {
        return colorCount.get(currentColor) == HEIGHT * WIDTH;
    }
    
    public void printColorCount() {
        for(Color c:colorMap) {
            System.out.println(colorToString(c) + ": " + colorCount.get(c));
        }
    }
    
    public static String colorToString(Color color) {
        if(color.equals(Color.MAGENTA)) return "PINK";
        else if(color.equals(Color.BLUE)) return "BLUE";
        else if(color.equals(Color.YELLOW)) return "YELLOW";
        else if(color.equals(Color.GREEN)) return "GREEN";
        else if(color.equals(Color.CYAN)) return "LT BLUE";
        else if(color.equals(Color.RED)) return "RED";
        else return "";
    }
    
    /**
     * Floods the board with a given color, returning the number of new squares converted.
     * @param color The color to flood.
     * @return The number of new squares converted.
     */
    public int flood(Color color) {
        if(color.equals(currentColor) || isGameOver()) {
            return 0;
        }
        
        Color[][] newBoard = new Color[HEIGHT][WIDTH];
        
        flood(true, color, 0, 0, newBoard, currentColor);

        currentColor = color;
        setBoard(newBoard);
        
        int beforeCount = colorCount.get(Color.BLACK);
        
        colorCount.put(Color.BLACK, 0);

        flood(false, Color.BLACK, 0, 0, new Color[HEIGHT][WIDTH], currentColor );

        int afterCount = colorCount.get(Color.BLACK);
        
        return afterCount - beforeCount;
    }

    private void flood(boolean updateCount, Color color, int row, int column, Color[][] newBoard, Color parentColor ) {
        if( row >= 0 && row < HEIGHT && column >= 0 && column < WIDTH && newBoard[row][column] == null ) {
            Color currentColor = board[row][column].getBackground();
            
            if( currentColor.equals( parentColor ) ) {
                newBoard[row][column] = color;
                
                // update the color count
                if(updateCount)
                    colorCount.put(currentColor, colorCount.get(currentColor) - 1);
                
                // always update this, as it is BLACK second time through
                colorCount.put(color, colorCount.get(color) + 1);
            
                // recurse in all 4 directions
                flood(updateCount, color, row + 1, column, newBoard, currentColor);
                flood(updateCount, color, row - 1, column, newBoard, currentColor);
                flood(updateCount, color, row, column + 1, newBoard, currentColor);
                flood(updateCount, color, row, column - 1, newBoard, currentColor);
            }
        }
    }
    
    public void resetBoard(Color[][] colors, Map<Color, Integer> colorCount, Color currentColor) {
        setBoard(colors);
        
        for(Map.Entry<Color, Integer> count:colorCount.entrySet()) {
            this.colorCount.put(count.getKey(), count.getValue());
        }
        
        this.colorCount.put(Color.BLACK, colorCount.get(Color.BLACK));
        
        this.currentColor = currentColor;
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
