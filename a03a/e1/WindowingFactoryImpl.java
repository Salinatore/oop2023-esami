package a03a.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class WindowingFactoryImpl implements WindowingFactory {

    private class WindowingImpl<X, Y> implements Windowing<X, Y> {

        List<X> list = new ArrayList<>();
        Function<List<X>, Optional<Y>> function;

        public WindowingImpl(Function<List<X>, Optional<Y>> function) {
            this.function = function;
        }

        @Override
        public Optional<Y> process(X x) {
            this.list.add(x);
            return function.apply(list);
        }

    }

    @Override
    public <X> Windowing<X, X> trivial() {
        return new WindowingImpl<>(l -> Optional.of(l.getLast()));
    }

    @Override
    public <X> Windowing<X, Pair<X, X>> pairing() {
        return new WindowingImpl<>(
            l -> l.size() > 1 ? Optional.of(new Pair<X, X>(l.get(l.size() - 2), l.get(l.size() - 1))) : Optional.empty() 
        );
    }

    @Override
    public Windowing<Integer, Integer> sumLastFour() {
        return new WindowingImpl<>(
            l -> l.size() > 3 ? l.subList(l.size() - 4, l.size()).stream().reduce((x,y) -> x+y) : Optional.empty()
        );
    }

    @Override
    public <X> Windowing<X, List<X>> lastN(int n) {
        return new WindowingImpl<>(
            l -> l.size() >= n ? Optional.of(l.subList(l.size() - n, l.size())) : Optional.empty()
        );
    }

    @Override
    public Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n) {
        return new WindowingImpl<>(
            l -> {
                List<Integer> shortestList = new ArrayList<>();
                for (int i = l.size() - 1; i >= 0; i--) {
                    shortestList.add(l.get(i));
                    if (shortestList.stream().reduce((x, y) -> x+y).get() >= n) {
                        Collections.reverse(shortestList);
                        return Optional.of(List.copyOf(shortestList));
                    }
                }
                return Optional.empty();
            }
        );
    }

}
