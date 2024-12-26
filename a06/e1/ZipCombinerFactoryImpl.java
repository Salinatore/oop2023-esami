package a06.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ZipCombinerFactoryImpl implements ZipCombinerFactory {

    private abstract class AbstractZipCombiner<X, Y, Z> implements ZipCombiner<X, Y, Z> {

        @Override
        public List<Z> zipCombine(List<X> l1, List<Y> l2) {
            List<Z> results = new ArrayList<>();
            for (int i = 0; i < l1.size(); i++) {
                if (predicate(l1.get(i))) {
                    results.add(transformer(l1, l2, i));
                }
            }
            return results;
        }
        
        protected abstract Z transformer(List<X> l1, List<Y> l2, int i);

        protected abstract boolean predicate(X x);

    }

    @Override
    public <X, Y> ZipCombiner<X, Y, Pair<X, Y>> classical() {
        return new AbstractZipCombiner<X,Y,Pair<X,Y>>() {

            @Override
            protected Pair<X, Y> transformer(List<X> l1, List<Y> l2, int i) {
                return new Pair<X,Y>(l1.get(i), l2.get(i));
            }

            @Override
            protected boolean predicate(X x) {
                return true;
            }
            
        };
    }

    @Override
    public <X, Y, Z> ZipCombiner<X, Y, Z> mapFilter(Predicate<X> predicate, Function<Y, Z> mapper) {
        return new AbstractZipCombiner<X,Y,Z>() {

            @Override
            protected Z transformer(List<X> l1, List<Y> l2, int i) {
                return mapper.apply(l2.get(i));
            }

            @Override
            protected boolean predicate(X x) {
                return predicate.test(x);
            }
            
        }; 
    }

    @Override
    public <Y> ZipCombiner<Integer, Y, List<Y>> taker() {
        return new AbstractZipCombiner<Integer,Y,List<Y>>() {

            @Override
            protected List<Y> transformer(List<Integer> l1, List<Y> l2, int i) {
                return l2.stream()
                        .skip(l1.stream().limit(i - 1 > 0 ? i - 1 : 0).reduce(0, (x, y) -> x+y))
                        .limit(l1.get(i))
                        .toList();
            }

            @Override
            protected boolean predicate(Integer x) {
                return true;
            }
            
        };
    }

    @Override
    public <X> ZipCombiner<X, Integer, Pair<X, Integer>> countUntilZero() {
        return new AbstractZipCombiner<X,Integer,Pair<X,Integer>>() {

            @Override
            protected Pair<X, Integer> transformer(List<X> l1, List<Integer> l2, int i) {
                int count = 0;
                int zeroFound = 0;
                boolean counting = false;
                for (int j = 0; j < l2.size(); j++) {
                    counting = zeroFound == i ? true : false;
                    if (l2.get(j) == 0) {
                        if (zeroFound < i) {
                            zeroFound++;
                        } else {
                            break;
                        }
                    } else {
                        if (counting) {
                            count++;
                        }
                    } 
                } 
                return new Pair<X,Integer>(l1.get(i), count);
            }

            @Override
            protected boolean predicate(X x) {
                return true;
            }
            
        };
    }

}
