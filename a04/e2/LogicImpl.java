package a04.e2;

import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class LogicImpl implements Logic {

    private final Set<Position> values = new HashSet<>();
    private final Position start;
    private final int width;

    public LogicImpl(int width) {
        this.width = width;
        Random random = new Random();
        this.start = new Position(random.nextInt(width), 0);
        values.add(start);
    }

    @Override
    public void hit(Position position) {
        if (position.y() != 0) {
            this.values.add(position);
        }
    }

    private boolean hasChildren(Position init) {
        if (init.y() == width) {
            return true;
        }
        if (!this.values.contains(init)) {
            return false;
        } else {
            return 
                this.hasChildren(new Position(init.x() - 1, init.y() + 1)) ||
                this.hasChildren(new Position(init.x() + 1, init.y() + 1));
        }
    }

    @Override
    public boolean isOver() {
        return this.hasChildren(start);
    }

    @Override
    public boolean isPresent(Position value) {
        return this.values.contains(value);
    }

}
