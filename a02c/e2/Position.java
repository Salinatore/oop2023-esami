package a02c.e2;

public record Position(int x, int y) {

    public static Position sum(Position p1, Position p2) {
        return new Position(p1.x() + p2.x(), p1.y() + p2.y());
    }

}
