package a04.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListExtractorFactoryImpl implements ListExtractorFactory {

    private abstract class AbstractListExtractor<A, B> implements ListExtractor<A, B> {

        @Override
        public B extract(List<A> list) {
            return mapper(transformer(list));
        }

        protected abstract List<A> transformer(List<A> list);

        protected abstract B mapper(List<A> list);

    }

    @Override
    public <X> ListExtractor<X, Optional<X>> head() {
        return new AbstractListExtractor<X,Optional<X>>() {

            @Override
            protected List<X> transformer(List<X> list) {
                return List.copyOf(list);
            }

            @Override
            protected Optional<X> mapper(List<X> list) {
                return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
            }
             
        };
    }

    @Override
    public <X, Y> ListExtractor<X, List<Y>> collectUntil(Function<X, Y> mapper, Predicate<X> stopCondition) {
        return new AbstractListExtractor<X,List<Y>>() {

            @Override
            protected List<X> transformer(List<X> list) {
                List<X> transformedList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (stopCondition.test(list.get(i))) {
                        return transformedList;
                    } else {
                        transformedList.add(list.get(i));
                    }
                }
                return transformedList;
            }

            @Override
            protected List<Y> mapper(List<X> list) {
                return list.stream()
                        .map(mapper)
                        .toList();
            }
            
        };
    }

    @Override
    public <X> ListExtractor<X, List<List<X>>> scanFrom(Predicate<X> startCondition) {
        return new AbstractListExtractor<X,List<List<X>>>() {

            @Override
            protected List<X> transformer(List<X> list) {
                return List.copyOf(
                    list.subList(
                        list.stream().filter(startCondition).findAny().isPresent() ? 
                            list.indexOf( list.stream().filter(startCondition).findAny().get()) : list.size(),
                        list.size())
                );
            }

            @Override
            protected List<List<X>> mapper(List<X> list) {
                if (list.isEmpty()) {
                    return List.of();
                }
                List<List<X>> mappedList = new ArrayList<>();
                List<X> supplier = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    supplier.add(list.get(i));
                    mappedList.add(List.copyOf(supplier));
                }
                return mappedList;
            }
            
        };
    }

    @Override
    public <X> ListExtractor<X, Integer> countConsecutive(X x) {
        return new AbstractListExtractor<X,Integer>() {

            @Override
            protected List<X> transformer(List<X> list) {
                if (list.isEmpty() || !list.contains(x)) {
                    return Collections.emptyList();
                } 
                List<X> consecutive = new ArrayList<>();
                for (int i = list.indexOf(x); i < list.size() && list.get(i).equals(x); i++) {
                    consecutive.add(list.get(i));
                }
                return consecutive;
            }

            @Override
            protected Integer mapper(List<X> list) {
                return list.size();
            }
            
        };
    }

}
