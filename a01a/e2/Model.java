package a01a.e2;

import java.util.Map;

public interface Model {

    boolean select(Pair<Integer, Integer> pair);

    Map<Pair<Integer, Integer>, Integer> getValues();

}
