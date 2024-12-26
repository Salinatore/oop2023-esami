package a06.e2;

import java.util.Optional;

public interface Logic {

    boolean isOver();

    void hit(Position position);

    Optional<Integer> getValue(Position value);

    boolean isDisabled(Position position);

}
