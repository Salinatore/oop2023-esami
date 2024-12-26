package a06.e1;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ZipCombinerFactoryImpl implements ZipCombinerFactory {

    private record ZipCombinerImpl<X, Y, Z>(BiFunction<X, Iterator<Y>, Stream<Z>> transform) implements ZipCombiner<X, Y, Z> {

        @Override
        public List<Z> zipCombine(List<X> l1, List<Y> l2) {
            var i2 = l2.iterator();
            return l1.stream()
                    .flatMap(x -> transform.apply(x, i2))
                    .toList();
        }
        
    }

    @Override
    public <X, Y> ZipCombiner<X, Y, Pair<X, Y>> classical() {
        return new ZipCombinerImpl<>((x, it) -> Stream.of(new Pair<X, Y>(x, it.next())));
    }

    @Override
    public <X, Y, Z> ZipCombiner<X, Y, Z> mapFilter(Predicate<X> predicate, Function<Y, Z> mapper) {
        return new ZipCombinerImpl<>((x, it) -> Stream.of(mapper.apply(it.next())).filter(e -> predicate.test(x)));
    }

    @Override
    public <Y> ZipCombiner<Integer, Y, List<Y>> taker() {
        return new ZipCombinerImpl<>((x, it) -> 
            Stream.of(Stream.iterate(0, i -> i+1).limit(x).map(i -> it.next()).toList()));
    }

    @Override
    public <X> ZipCombiner<X, Integer, Pair<X, Integer>> countUntilZero() {
        return new ZipCombinerImpl<>((x, it) -> 
            Stream.of(new Pair<>(x, (int) Stream.iterate(it.next(), n -> n != 0, n -> it.next()).count())));
    }

}
