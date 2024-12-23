package a01b.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LogicImpl implements Logic {

    private static final int NUMBER_OF_VALUES = 5;
    private List<Pair<Integer, Integer>> values = new ArrayList<>();
    private Move moving = Move.RIGHT;
    private final int size;

    private enum Move {
        RIGHT, LEFT;
    }

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(Pair<Integer, Integer> pair) {
        if (this.values.size() < NUMBER_OF_VALUES) {
            if (!this.values.contains(pair)) {
                this.values.add(pair);
            }
            return true;
        } else {
            if (this.moving == Move.RIGHT) {
                this.values = this.values.stream()
                .map(p -> new Pair<>(p.getX() + 1, p.getY()))
                .toList();
                if (isNotOnBorder()) {
                    this.moving = Move.LEFT;
                }
                return true;
            } else {
                this.values = this.values.stream()
                .map(p -> new Pair<>(p.getX() - 1, p.getY()))
                .toList();
                return isNotOnBorder();
            }
        }
    }

    @Override
    public Optional<Integer> getValue(Pair<Integer, Integer> currentPos) {
        return Optional.of(values.indexOf(currentPos)).filter(i -> i > -1).map(i -> i + 1);
    }

    private boolean isNotOnBorder() {
        return !this.values.stream()
                .anyMatch(p -> p.getX() >= this.size || p.getY() < 0);
    }

}
