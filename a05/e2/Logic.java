package a05.e2;

public interface Logic {

    enum CellType {
        PLAYER, ENEMY, EMPTY;
    }

    void hit(Position position);

    boolean isOver();

    CellType getValue(Position value);

}
