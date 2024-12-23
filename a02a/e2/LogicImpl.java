package a02a.e2;

import java.util.HashSet;
import java.util.Set;

public class LogicImpl implements Logic {

    Set<Pair<Integer, Integer>> mutable = new HashSet<>();

    @Override
    public boolean hit(Pair<Integer, Integer> pair) {
        if (pair.getX() % 2 != 0 || pair.getY() % 2 != 0) {
            return false;
        } else {
            mutable.add(pair);
            return true;
        }
    }

    public boolean isWall(Pair<Integer, Integer> pair) {
        if (pair.getX() % 2 != 0 || pair.getY() % 2 != 0) {
            return true;
        } 
        return false;
    }

    private boolean makesSquare(Pair<Integer, Integer> pair) {
        if (
            this.mutable.contains(new Pair<>(pair.getX(), pair.getY() + 2)) &&
            this.mutable.contains(new Pair<>(pair.getX() + 2, pair.getY())) &&
            this.mutable.contains(new Pair<>(pair.getX() + 2, pair.getY() + 2))
            ) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isOver() {
        return this.mutable.stream().anyMatch(p -> this.makesSquare(p));
    }

}
