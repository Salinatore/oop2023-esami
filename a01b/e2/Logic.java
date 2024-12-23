package a01b.e2;

import java.util.Optional;

public interface Logic {

    boolean hit(Pair<Integer, Integer> pair);

    Optional<Integer> getValue(Pair<Integer, Integer> currentPos);

}
