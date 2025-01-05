package a03b.e2;

public interface Logic {

    enum CellType { ORIGIN, ACTIVE, BLANK }

    void hit(Position position);

    CellType getType(Position position);

    boolean isOver();

}
