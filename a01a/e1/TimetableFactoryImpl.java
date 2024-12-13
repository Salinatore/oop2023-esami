package a01a.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimetableFactoryImpl implements TimetableFactory {

    private class TimetableImpl implements Timetable {

        private final Map<String, List<String>> table = new HashMap<>();

        @Override
        public Timetable addHour(String activity, String day) {
            if(!this.table.containsKey(day)) {
                this.table.put(day, new ArrayList<>());
            }
            this.table.get(day).add(activity);    
            return this;
        }

        @Override
        public Set<String> activities() {
            return this.table.entrySet().stream()
                    .map(e -> e.getValue())
                    .flatMap(l -> l.stream().distinct())
                    .collect(Collectors.toSet());
        }

        @Override
        public Set<String> days() {
            return this.table.keySet();
        }

        @Override
        public int getSingleData(String activity, String day) {
            return (int) this.table.entrySet().stream()
                    .filter(e -> e.getKey().equals(day))
                    .flatMap(e -> e.getValue().stream())
                    .filter(a -> a.equals(activity))
                    .count();
        }

        @Override
        public int sums(Set<String> activities, Set<String> days) {
            return (int) this.table.entrySet().stream()
                    .flatMap(e -> days.contains(e.getKey()) ? e.getValue().stream() : Collections.emptyList().stream())
                    .filter(a -> activities.contains(a))
                    .count();
        }
    }
    
    @Override
    public Timetable empty() {
        return new TimetableImpl();
    }

    @Override
    public Timetable single(String activity, String day) {
        return this.empty().addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'join'");
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        Timetable toReturn = new TimetableImpl();
        for (var day : table.days()) {
            for (var act : table.activities()) {
                if (table.sums(Set.of(act), Set.of(day)) <= bounds.apply(act, day)) {
                    for (int i = 0; i <table.sums(Set.of(act), Set.of(day)); i++) {
                        toReturn.addHour(act, day);
                    }
                }
            }
        }
        return toReturn;
    }

}
