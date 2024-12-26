package a05.e2;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicImpl implements Logic {

    private Position playerPosition;
    private Position enemyPosition;
    private final int width;

    public LogicImpl(int width) {
        this.width = width;
        this.playerPosition = createEntity();
        this.enemyPosition = createEntity();
        while (neighbor(this.playerPosition, this.enemyPosition)) {
            this.enemyPosition = createEntity();
        }
    }

    private Position createEntity() {
        Random random = new Random();
        return new Position(random.nextInt(this.width), random.nextInt(this.width));
    }

    private static boolean neighbor(Position p1, Position p2) {
        return Math.abs(p1.x() - p2.x()) <= 1 && Math.abs(p1.y() - p2.y()) <= 1;
    }

    @Override
    public void hit(Position position) {
        if (neighbor(this.playerPosition, position) && !position.equals(this.playerPosition)) {
            this.playerPosition = position;
            this.enemyPosition = this.computeEnemyPosition();
        } 
    }

    private Position computeEnemyPosition() {
        Random random = new Random();
        List<Position> availableNeighbor = Stream.iterate(-1, i -> i + 1)
                .limit(3)
                .flatMap(i -> Stream.iterate(-1, n -> n + 1)
                    .limit(3)
                    .map(j -> new Position(this.enemyPosition.x() + i, this.enemyPosition.y() + j)))
                .filter(p -> this.isInMap(p))
                .filter(p -> !neighbor(p, this.playerPosition))
                .toList();

        
        if (!availableNeighbor.isEmpty()) {
            return availableNeighbor.get(random.nextInt(availableNeighbor.size()));
        } else {
            return this.enemyPosition;
        }
    }

    private boolean isInMap(Position pos) {
        return pos.x() >= 0 && pos.x() < this.width && pos.y() >= 0 && pos.y() < this.width;
    }

    @Override
    public boolean isOver() {
        return this.enemyPosition.equals(this.playerPosition);
    }

    @Override
    public CellType getValue(Position value) {
        if (value.equals(this.enemyPosition)) {
            return CellType.ENEMY;
        } 
        if (value.equals(this.playerPosition)) {
            return CellType.PLAYER;
        }
        return CellType.EMPTY;
    }

}
