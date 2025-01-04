package a01c.e2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicImpl implements Logic {

    private final List<Position> active = new ArrayList<>();
    private final int size;
    private int state = 0;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(Position position) {
        if (active.size() < 2) {
            active.add(position); 
        } else if (active.size() == 2) {
            active.addAll(this.fill(active.get(0), active.get(1)));
        } else {
            state++;
            active.addAll(this.fill(
                new Position(active.get(0).x() - state, active.get(0).y() - state), 
                new Position(active.get(1).x() + state, active.get(1).y() + state)));
        }
        return allMap().stream().allMatch(p -> this.active.contains(position));
    }

    private Set<Position> allMap() {
        Set<Position> all = new HashSet<>(); 
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                all.add(new Position(j, i));
            }
        }
        return all;
    }

    private Collection<Position> fill(Position p1, Position p2) {
        return allMap().stream()
                .filter(p -> p.x() >= p1.x() && p.y() >= p1.y() && p.x() <= p2.x() && p.y() <= p2.y())
                .toList();
    }

    @Override
    public boolean isPresent(Position position) {
        return this.active.contains(position);
    }

    @Override
    public int getValue(Position position) {
        return this.active.get(0).equals(position) ? 1 : this.active.get(1).equals(position) ? 2 : 0;
    }

}
