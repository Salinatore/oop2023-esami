package a01a.e2;

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ModelImpl implements Model {

    private Map<Pair<Integer, Integer>, Integer> values;
    private final int size;
    private boolean selectMode;

    public ModelImpl(int size) {
        this.values = new HashMap<>(size*size);
        this.selectMode = true;
        this.size = size;
    }

    @Override
    public boolean select(Pair<Integer, Integer> pair) {
        System.out.println(pair);
        if (this.selectMode) {
            this.selectMode = this.computeMode(pair);
        }
        if (this.selectMode) {
            if (this.values.containsKey(pair)) {
                this.values.put(pair, this.values.get(pair) + 1);
            } else {
                this.values.put(pair, 1);
            }
            return true;
        } else {
            return this.fire();
        }                    
    } 

    @Override
    public Map<Pair<Integer, Integer>, Integer> getValues() {
        return Map.copyOf(this.values);
    }
    
    private boolean computeMode(Pair<Integer, Integer> pair) {
        return !this.values.keySet().stream()
                .anyMatch(p -> Math.abs(p.getX() - pair.getX()) == 1 || Math.abs(p.getY() - pair.getY()) == 1);
    }
            
    private boolean fire() {
        this.values = this.values.entrySet().stream()
                .collect(Collectors.toMap(e -> 
                    new Pair<>(e.getKey().getX() + 1, e.getKey().getY() - 1),
                    Map.Entry::getValue
                ));

        boolean notOver = !this.values.keySet().stream()
                .filter(p ->  p.getY() < 0 || p.getX() > this.size - 1).findAny().isPresent(); 

        return notOver;
    }

}
