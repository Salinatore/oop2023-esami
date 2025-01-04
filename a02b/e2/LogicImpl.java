package a02b.e2;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

    private final int size;
    private Optional<Position> current = Optional.empty();
    private boolean isOver;
    
    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public void hit(Position position) {
        if (current.isEmpty()) {
            current = Optional.of(position);
        } else if (diagonals(current.get()).contains(position)) {
            var center = current.get();
            var diagonals = diagonals(center);
            if (diagonals.get(0).equals(position)) {
                current = Optional.of(new Position(this.size - 3, this.size - 3));
            } 
            if (diagonals.get(1).equals(position)) {
                current = Optional.of(new Position(this.size - 3, 2));
            } 
            if (diagonals.get(2).equals(position)) {
                current = Optional.of(new Position(2, this.size - 3));
            } 
            if (diagonals.get(3).equals(position)) {
                current = Optional.of(new Position(2, 2));
            } 
        } else if (current.get().equals(position)) {
            isOver = true;
        }
    }

    private Set<Position> allPosition() {
        Set<Position> all = new HashSet<>();
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                all.add(new Position(j,i));
            }
        }
        return all;
    }

    @Override
    public boolean isOver() {
        return this.isOver;
    }

    private static List<Position> diagonals(Position center) {
        return List.of(
            new Position(center.x() + 1, center.y() + 1),
            new Position(center.x() + 1, center.y() - 1),
            new Position(center.x() - 1, center.y() + 1),
            new Position(center.x() - 1, center.y() - 1));
    }

    @Override
    public boolean isActive(Position position) {
        var center = current.get();
        var left = new Position(center.x() - 2, center.y() - 2);
        var right = new Position(center.x() + 2, center.y() + 2);
        var square = allPosition().stream()
                .filter(p -> p.x() >= left.x() && p.y() >= left.y() && p.x() <= right.x() && p.y() <= right.y())
                .collect(Collectors.toCollection(HashSet::new));
        square.removeAll(diagonals(center));
        return square.contains(position);
    }

}
