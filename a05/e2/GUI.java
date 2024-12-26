package a05.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;

    public GUI(int width) {
        this.logic = new LogicImpl(width);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*width);
        
        JPanel panel = new JPanel(new GridLayout(width,width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            this.logic.hit(this.cells.get(jb));
            if (this.logic.isOver()) {
                this.cells.keySet().stream().forEach(b -> b.setEnabled(false));
            }
        	this.reDraw();
        };
                
        for (int i=0; i<width; i++){
            for (int j=0; j<width; j++){
            	var pos = new Position(j,i);
                final JButton jb = new JButton(pos.toString());
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.reDraw();
        this.setVisible(true);
    }

    private void reDraw() {
        for (var entry: this.cells.entrySet()) {
            entry.getKey().setText(
                switch (this.logic.getValue(entry.getValue())) {
                    case EMPTY -> "";
                    case ENEMY -> "E";
                    case PLAYER -> "P";
                }
            );
        }
    }
    
}
