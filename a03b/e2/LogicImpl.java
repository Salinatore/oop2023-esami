package a03b.e2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LogicImpl implements Logic {

    private final Set<Position> active = new HashSet<>();
    private final Position origin;
    private final int width;
    private final int height;

    public LogicImpl(int width, int height) {
        this.width = width;
        this.height = height;
        Random random = new Random();
        this.origin = new Position(random.nextInt(width), random.nextInt(height));
    }

    @Override
    public void hit(Position position) {
        active.addAll(createTriangle(position));
    }

    private Collection<Position> createTriangle(Position position) {
        var full = fullTriangle(position);
        int finish = full.stream()
                .filter(p -> p.y() < 0 || p.y() >= height)
                .mapToInt(p -> p.x())
                .min().getAsInt();
        return full.stream().filter(p -> p.x() < finish).toList();
    }

    private Collection<Position> fullTriangle(Position position) {
        Set<Position> triangle = new HashSet<>();
        triangle.add(position);
        if (isInMap(position)) {
            triangle.addAll(fullTriangle(new Position(position.x() + 1, position.y() - 1)));
            triangle.addAll(fullTriangle(new Position(position.x() + 1, position.y() + 1)));
            triangle.addAll(fullTriangle(new Position(position.x() + 1, position.y())));
        }
        return triangle;
    }

    private boolean isInMap(Position position) {
        return position.x() < this.width && position.x() >= 0 &&
            position.y() < this.height && position.y() >= 0;
    }

    @Override
    public CellType getType(Position position) {
        if (this.origin.equals(position)) {
            return CellType.ORIGIN;
        }
        if (this.active.contains(position)) {
            return CellType.ACTIVE;
        }
        return CellType.BLANK;
    }

    @Override
    public boolean isOver() {
        return this.active.contains(this.origin);
    }

}
