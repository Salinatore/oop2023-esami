package a03b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int width, int height) {
        this.logic = new LogicImpl(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*height);
        
        JPanel panel = new JPanel(new GridLayout(height, width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
        	this.logic.hit(this.cells.get(jb));
            if (this.logic.isOver()) {
                System.exit(0);
            }
            this.redraw();
        };
                
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
            	var pos = new Position(j,i);
                final JButton jb = new JButton(pos.toString());
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        //this.redraw();
        this.setVisible(true);
    }

    private void redraw() {
        for (var jb : this.cells.keySet()) {
            jb.setText(
                switch (this.logic.getType(this.cells.get(jb))) {
                    case ORIGIN -> "o";
                    case ACTIVE -> "*";
                    case BLANK -> "";
                }
            );
        }
    }
    
}
