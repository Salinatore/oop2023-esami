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

        private final Map<String, Map<String, Integer>> table = new HashMap<>();

        @Override
        public Timetable addHour(String activity, String day) {
            if(!this.table.containsKey(day)) {
                this.table.put(day, new HashMap<>());
                this.table.get(day).put(activity, 1);  
            } else if (!this.table.get(day).containsKey(activity)) {
                this.table.get(day).put(activity, 1);  
            } else {
                this.table.get(day).put(activity, this.table.get(day).get(activity) + 1);  
            }
            return this;
        }

        @Override
        public Set<String> activities() {
            return this.table.entrySet().stream()
                    .flatMap(e -> e.getValue().keySet().stream())
                    .collect(Collectors.toSet());
        }

        @Override
        public Set<String> days() {
            return this.table.keySet();
        }

        @Override
        public int getSingleData(String activity, String day) {
            if (this.table.containsKey(day) && this.table.get(day).containsKey(activity)) {
                return this.table.get(day).get(activity);
            } else {
                return 0;
            }
        }

        @Override
        public int sums(Set<String> activities, Set<String> days) {
            int toReturn = 0;
            for (String day : days) {
                for (String activity : activities) {
                    toReturn = toReturn + getSingleData(activity, day);
                }
            }
            return toReturn;
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
            for (var activity : table.activities()) {
         
            }
        }
        return toReturn;
    }

}
