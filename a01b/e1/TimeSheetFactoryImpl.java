package a01b.e1;

import static org.junit.jupiter.api.DynamicTest.stream;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    private record TimeSheetImpl(List<String> activities, List<String> days, BiFunction<String, String, Integer> function) implements TimeSheet {

        @Override
        public int getSingleData(String activity, String day) {
            if (this.activities.contains(activity) && this.days.contains(day)) {
                return function.apply(activity, day);
            } else {
                return 0;
            }
        }

        @Override
        public Map<String, Integer> sumsPerActivity() {
            return this.activities.stream()
                    .collect(Collectors.toMap(
                        act -> act, 
                        act -> days.stream()
                            .map(d -> this.getSingleData(act, d))
                            .reduce(0, (x, y) -> x+y)
                    ));
        }

        @Override
        public Map<String, Integer> sumsPerDay() {
            return this.days.stream()
                    .collect(Collectors.toMap(
                        day -> day, 
                        day -> activities.stream()
                            .map(a -> this.getSingleData(a, day))
                            .reduce(0, (x, y) -> x+y)
                    ));
        }

    }

    private static List<String> createActivity(int numActivities) {
        return IntStream.rangeClosed(1, numActivities)
            .mapToObj(i -> "act"+i)
            .toList();
    }

    private static List<String> createDays(int numActivities) {
        return IntStream.rangeClosed(1, numActivities)
            .mapToObj(i -> "day"+i)
            .toList();
    }

    @Override
    public TimeSheet flat(int numActivities, int numNames, int hours) {
        return new TimeSheetImpl(
            createActivity(numActivities),
            createDays(numNames),
            (act, day) -> hours
        );
    }

    @Override
    public TimeSheet ofListsOfLists(List<String> activities, List<String> days, List<List<Integer>> data) {
        return new TimeSheetImpl(
            activities, 
            days, 
            (a, d) -> data.get(activities.indexOf(a)).get(days.indexOf(d))
        );
    }

    @Override
    public TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer, Integer>> data) {
        var activities = createActivity(numActivities);
        var days = createDays(numDays);
        return new TimeSheetImpl(
            activities,
            days,
            (a, d) -> (int) data.stream().filter(p -> p.get1().equals(activities.indexOf(a)) && p.get2().equals(days.indexOf(d))).count()
        );
    }

    @Override
    public TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String, String>, Integer> data) {
        return new TimeSheetImpl(
            activities,
            days,
            (a, d) -> data.getOrDefault(new Pair<>(a, d), 0)
        );
    }

}
