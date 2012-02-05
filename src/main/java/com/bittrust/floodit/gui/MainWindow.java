package com.bittrust.floodit.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements WindowListener {

    private static final long serialVersionUID = -9185488860057526538L;
    private Board gameBoard;
    
    public MainWindow() {
        super("Flood-It Simulation");
        
        gameBoard = new Board();
        
        gameBoard.randomlyFillBoard();
        
        this.setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        
        buttonPanel.add(new ColorButton(gameBoard, Color.MAGENTA), BorderLayout.SOUTH);
        buttonPanel.add(new ColorButton(gameBoard, Color.BLUE), BorderLayout.SOUTH);
        buttonPanel.add(new ColorButton(gameBoard, Color.YELLOW), BorderLayout.SOUTH);
        buttonPanel.add(new ColorButton(gameBoard, Color.GREEN), BorderLayout.SOUTH);
        buttonPanel.add(new ColorButton(gameBoard, Color.CYAN), BorderLayout.SOUTH);
        buttonPanel.add(new ColorButton(gameBoard, Color.RED), BorderLayout.SOUTH);
        
        this.add(gameBoard, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        this.setBounds(getCenteredWindow(250, 300));
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
        // TODO Auto-generated method stub

    }

    public void windowClosed(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void windowClosing(WindowEvent arg0) {
        this.dispose();
    }

    public void windowDeactivated(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void windowDeiconified(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void windowIconified(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void windowOpened(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

}
