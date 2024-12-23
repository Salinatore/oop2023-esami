package a03a.e2;

public interface Logic {

    enum CellType {
        FINISH, DRAW, EMPTY;
    }

    boolean isOver();

    CellType getMark(Pair<Integer, Integer> pair);

    void hit(Pair<Integer, Integer> pair);    

}
