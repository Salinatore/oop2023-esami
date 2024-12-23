package a03a.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//x colonne -- y righe
public class LogicImpl implements Logic {

    private final int width;
    private final int height;
    private final Pair<Integer, Integer> finish;
    private List<Pair<Integer, Integer>> values = new ArrayList<>();

    public LogicImpl(int width, int height) {
        this.width = width;
        this.height = height;
        Random random = new Random();
        this.finish = new Pair<Integer,Integer>(width - 1, random.nextInt(height - 1));
    }

    @Override
    public void hit(Pair<Integer, Integer> pair) {
        this.values.clear();
        this.values.add(pair);
        boolean movingUp = true;
        while (this.values.getLast().getX() < width) {
            var lastPos = this.values.getLast();
            if (lastPos.getY() == 0) {
                movingUp = false;
            }
            if (lastPos.getY() == height - 1) {
                movingUp = true;
            }
            if (movingUp == true) {
                this.values.add(new Pair<>(lastPos.getX() + 1, lastPos.getY() - 1));      
            } else {
                this.values.add(new Pair<>(lastPos.getX() + 1, lastPos.getY() + 1));
            }
        }
    }

    @Override
    public boolean isOver() {
        return this.values.contains(finish);
    }

    @Override
    public CellType getMark(Pair<Integer, Integer> pair) {
        if (pair.equals(finish)) {
            return CellType.FINISH;
        } 
        if (this.values.contains(pair)) {
            return CellType.DRAW;
        } else {
            return CellType.EMPTY;
        }
    }

}
