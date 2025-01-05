package a03b.e1;

import java.util.List;

public class InfiniteIteratorHelpersImpl implements InfiniteIteratorsHelpers {

    @Override
    public <X> InfiniteIterator<X> of(X x) {
        return new InfiniteIterator<X>() {

            @Override
            public X nextElement() {
                return x;
            }
            
        };
    }

    @Override
    public <X> InfiniteIterator<X> cyclic(List<X> l) {
        return new InfiniteIterator<X>() {

            private int count = 0;

            @Override
            public X nextElement() {
                var x = l.get(this.count);
                this.count = (this.count + 1) % l.size();
                return x;
            }
            
        };
    }

    @Override
    public InfiniteIterator<Integer> incrementing(int start, int increment) {
        return new InfiniteIterator<Integer>() {

            private int current = start;

            @Override
            public Integer nextElement() {
                var value = current;
                this.current = this.current + increment;
                return value;
            }
            
        };
    }

    @Override
    public <X> InfiniteIterator<X> alternating(InfiniteIterator<X> i, InfiniteIterator<X> j) {
        return new InfiniteIterator<X>() {

            private int count = -1;

            @Override
            public X nextElement() {
                count++;
                return count % 2 == 0 ? i.nextElement() : j.nextElement();
            }
            
        };
    }

    @Override
    public <X> InfiniteIterator<List<X>> window(InfiniteIterator<X> i, int n) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'window'");
    }

}
