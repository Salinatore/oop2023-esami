package a02a.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ListBuilderFactoryImpl implements ListBuilderFactory {

    private class ListBuilderImpl<T> implements ListBuilder<T> {

        private final List<T> list;

        private ListBuilderImpl(List<T> list) {
            this.list = list;
        }

        @Override
        public ListBuilder<T> add(List<T> list) {
            return new ListBuilderImpl<>(Stream.concat(this.list.stream(), list.stream()).toList());
        }

        @Override
        public ListBuilder<T> concat(ListBuilder<T> lb) {
            return this.add(lb.build());
        }

        @Override
        public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
            return new ListBuilderImpl<>(
                this.list.stream()
                        .flatMap(i -> i.equals(t) ? lb.build().stream() : List.of(i).stream())
                        .toList()
            );
        }

        @Override
        public ListBuilder<T> reverse() {
            var reversedList = new ArrayList<>(list);
            Collections.reverse(reversedList);
            return new ListBuilderImpl<>(reversedList);
        }

        @Override
        public List<T> build() {
            return List.copyOf(list);
        }

    }

    @Override
    public <T> ListBuilder<T> empty() {
        return new ListBuilderImpl<>(Collections.<T>emptyList());
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
        return new ListBuilderImpl<>(List.of(t));
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        return new ListBuilderImpl<>(list);
    }

    @Override
    public <T> ListBuilder<T> join(T start, T stop, List<ListBuilder<T>> builderList) {
        var temp = new ArrayList<>(builderList);
        temp.addFirst(fromElement(start));
        temp.addLast(fromElement(stop));
        return temp.stream().reduce(empty(), (bl1, bl2) -> bl1.concat(bl2));
    }

}
