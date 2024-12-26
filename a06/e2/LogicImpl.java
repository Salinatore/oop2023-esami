package a06.e2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicImpl implements Logic {

    private final Map<Position, Integer> values;
    private List<Position> current;
    private final Set<Position> disable;

    public LogicImpl(int width) {
        Random random = new Random();
        this.values = Stream.iterate(0, i -> i+1).limit(width)
                .flatMap(i -> Stream.iterate(0, j -> j+1).limit(width).map(j -> new Position(i, j)))
                .collect(Collectors.toMap(Function.identity(), pos -> random.nextInt(6) + 1));
        this.current = new ArrayList<>();
        this.disable = new HashSet<>();
    }

    @Override
    public void hit(Position position) {
        if (this.current.size() == 2) {
            this.current.clear();
        }
        this.current.add(position);
        if (this.current.size() == 2) {
            if (this.values.get(this.current.get(0)).equals(this.values.get(this.current.get(1)))) {
                this.disable.addAll(this.current);
            } 
        }
    }

    @Override
    public Optional<Integer> getValue(Position value) {
        return this.current.contains(value) || this.disable.contains(value) ? 
            Optional.of(this.values.get(value)) : Optional.empty(); 
    }

    @Override
    public boolean isOver() {
        List<Integer> remaining = values.keySet().stream().filter(p -> !this.disable.contains(p)).map(values::get).toList();
        return remaining.stream().distinct().count() == remaining.size();
    }

    @Override
    public boolean isDisabled(Position position) {
        return this.disable.contains(position);
    }

}
