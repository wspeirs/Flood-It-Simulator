package com.bittrust.floodit.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

public class SimulationButton extends JButton implements ActionListener {

    private static final long serialVersionUID = -1975065605076965639L;
    
    private Board board;
    
    public SimulationButton(Board board) {
        super("Sim");

        this.board = board;
        
        this.addActionListener(this);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        List<Color> path = new ArrayList<Color>();
        
        int ret = simulateStep(0, 1, path);
        
        System.out.println("BEST FLOOD: " + ret);
        
        for(Color color:path) {
            System.out.println("\t" + Board.colorToString(color));
            board.flood(color);
        }
        
    }
    
    public int simulateStep(int depth, int floodArea, List<Color> path) {
        if(depth == 4)
            return floodArea;
        
        List<Color> bestPath = null;
        int bestFloodArea = 0;
        
        for(Color color:Board.colorMap) {
            
            Color currentColor = board.getCurrentColor();
            Color[][] colors = board.getColorBoard();
            Map<Color, Integer> colorCount = board.getColorCount();
            
            // flood the board with this color
            int flooded = board.flood(color);
            
            if(flooded == 0) {
                board.resetBoard(colors, colorCount, currentColor);
                continue;
            }
            
            // add the color to the path
            path.add(color);
            
            int totalFloodArea = simulateStep(depth + 1, floodArea + flooded, path);

            if(totalFloodArea > bestFloodArea) {
                bestPath = new ArrayList<Color>(path);
                bestFloodArea = totalFloodArea;
            }
            
            // remove the last one in the path
            path.remove(path.size()-1);
            
            // reset the board
            board.resetBoard(colors, colorCount, currentColor);
        }
        
        if(bestPath != null) {
            path.clear();
            path.addAll(bestPath);
        }
        
        return bestFloodArea;
    }

}
