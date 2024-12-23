package a03a.e2;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int width, int height) {
        this.logic = new LogicImpl(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*height);
        
        JPanel panel = new JPanel(new GridLayout(height, width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            JButton jb = (JButton)e.getSource();
            this.logic.hit(this.cells.get(jb));
            if (this.logic.isOver()) {
                System.exit(0);
            }
            this.reDraw();
        };
                
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.reDraw();
        this.setVisible(true);
    }

    private void reDraw() {
        for (var entry : this.cells.entrySet()) {
            entry.getKey().setText(
                switch(this.logic.getMark(entry.getValue())) {
                    case FINISH -> "o";
                    case DRAW -> "*";
                    case EMPTY -> "";
                }
            );
        }
    }
    
}
