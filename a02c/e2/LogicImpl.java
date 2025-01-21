package a02c.e2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LogicImpl implements Logic {

    private final Set<Position> active = new HashSet<>();
    private final int size;
    private Position left;
    private Position right;
    private boolean isOver;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public void hit(Position position) {
        if (this.active.isEmpty()) {
            this.left = new Position(position.x() - 1, position.y() - 1);
            this.right = new Position(position.x() + 1, position.y() + 1);
        } else {
            if (this.left.equals(position)) {
                this.left = new Position(left.x() - 1, left.y() - 1);
            }
            if (this.right.equals(position)) {
                this.right = new Position(right.x() + 1, right.y() + 1);
            }
            if (position.equals(new Position(this.left.x(), this.right.y()))) {
                this.left = new Position(left.x() - 1, left.y());
                this.right = new Position(right.x(), right.y() + 1);
            }
            if (position.equals(new Position(this.right.x(), this.left.y()))) {
                this.left = new Position(left.x(), left.y() - 1);
                this.right = new Position(right.x() + 1, right.y());
            }
        }
        this.active.clear();
        this.active.addAll(drawRectangle(this.left, this.right)); 
        isOver = checkOver();
    }

    private boolean checkOver() {
        return this.active.stream()
                .anyMatch(p -> p.x() == 0 || p.x() == this.size -1 ||  p.y() == 0 || p.y() == this.size -1);
    }

    private Collection<Position> all() {
        Set<Position> allPositions = new HashSet<>();
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	allPositions.add(new Position(j,i));
            }
        }
        return allPositions;
    }

    private Collection<Position> drawRectangle(Position p1, Position p2) {
        return all().stream()
                .filter(p -> p.x() >= p1.x() && p.y() >= p1.y() && p.x() <= p2.x() && p.y() <= p2.y())
                .filter(p -> p.x() == p1.x() || p.y() == p1.y() || p.x() == p2.x() || p.y() == p2.y())
                .toList();
    }

    @Override
    public boolean isActive(Position position) {
        return this.active.contains(position);
    }

    @Override
    public boolean isOver() {
        return this.isOver;
    }

}
