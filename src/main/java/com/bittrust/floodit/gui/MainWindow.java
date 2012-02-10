package com.bittrust.floodit.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements WindowListener {

    private static final long serialVersionUID = -9185488860057526538L;
    private Board gameBoard;
    private Board lastBoard;
    
    public MainWindow() {
        super("Flood-It Simulation");
        
        gameBoard = new Board(false);
        gameBoard.randomlyFillBoard();
        gameBoard.validate();
        
        lastBoard = new Board(true);
        lastBoard.setBoard(gameBoard.getColorBoard());
        lastBoard.validate();

        this.setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        
        buttonPanel.add(new ColorButton(gameBoard, lastBoard, Color.MAGENTA), BorderLayout.SOUTH);
        buttonPanel.add(new ColorButton(gameBoard, lastBoard, Color.BLUE), BorderLayout.SOUTH);
        buttonPanel.add(new ColorButton(gameBoard, lastBoard, Color.YELLOW), BorderLayout.SOUTH);
        buttonPanel.add(new ColorButton(gameBoard, lastBoard, Color.GREEN), BorderLayout.SOUTH);
        buttonPanel.add(new ColorButton(gameBoard, lastBoard, Color.CYAN), BorderLayout.SOUTH);
        buttonPanel.add(new ColorButton(gameBoard, lastBoard, Color.RED), BorderLayout.SOUTH);
        buttonPanel.add(new SimulationButton(gameBoard), BorderLayout.SOUTH);
        
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.X_AXIS));
        
        boardPanel.add(gameBoard);
        boardPanel.add(lastBoard);

        this.add(boardPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        this.setBounds(getCenteredWindow(600, 350));
        this.addWindowListener(this);
        this.setVisible(true);
    }
    
    public static Rectangle getCenteredWindow(int width, int height) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle rec = ge.getScreenDevices()[0].getDefaultConfiguration().getBounds();
        Rectangle ret = new Rectangle();
        
        ret.x = (rec.width/2) - (width/2);
        ret.y = (rec.height/2) - (height/2);
        ret.width = width;
        ret.height = height;
    
        return ret;
    }

    public void windowActivated(WindowEvent arg0) {
    }

    public void windowClosed(WindowEvent arg0) {
    }

    public void windowClosing(WindowEvent arg0) {
        this.dispose();
    }

    public void windowDeactivated(WindowEvent arg0) {
    }

    public void windowDeiconified(WindowEvent arg0) {
    }

    public void windowIconified(WindowEvent arg0) {
    }

    public void windowOpened(WindowEvent arg0) {
    }

}
