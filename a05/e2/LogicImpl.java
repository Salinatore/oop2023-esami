package a05.e2;

import java.util.Random;

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
        Position nextPosition;
        do  {
            nextPosition = tryPosition(this.enemyPosition);
        } while (
                nextPosition.equals(this.enemyPosition) ||
                neighbor(this.playerPosition, nextPosition) ||
                !this.isInMap(nextPosition));
        return nextPosition;
    }

    private boolean isInMap(Position pos) {
        return pos.x() >= 0 && pos.x() < this.width && pos.y() >= 0 && pos.y() < this.width;
    }

    private static Position tryPosition(Position pos) {
        Random random = new Random();
        return new Position(
            (pos.x() + random.nextInt(3) - 1),
            (pos.y() + random.nextInt(3) - 1));
    }

    @Override
    public boolean isOver() {
        return false;
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
