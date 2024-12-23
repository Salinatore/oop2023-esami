package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells;
    private final Map<Pair<Integer, Integer>, JButton> invertedCells;
    private final Model model;
    
    public GUI(int size) {
        cells = new HashMap<>();
        invertedCells = new HashMap<>();
        model = new ModelImpl(size);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            if (model.select(cells.get(e.getSource()))) {
                this.updateView();
            } else {
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                this.invertedCells.put(pos, jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        this.setVisible(true);
    }

    private void updateView() {
        Map<Pair<Integer, Integer>, Integer> currentValues = this.model.getValues();
        for (var entry : this.invertedCells.entrySet()) {
            if (currentValues.containsKey(entry.getKey())) {
                entry.getValue().setText(Integer.toString(currentValues.get(entry.getKey())));
            } else {
                entry.getValue().setText("");
            }
        }
    }
    
}
