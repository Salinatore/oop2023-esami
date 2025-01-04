package a01c.e1;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    private record TimesheetImpl(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities, Map<String, Integer> boundsOnDays) implements TimeSheet {

        @Override
        public Set<String> activities() {
            return data.stream().map(p -> p.get1()).collect(Collectors.toSet());
        }

        @Override
        public Set<String> days() {
            return data.stream().map(p -> p.get2()).collect(Collectors.toSet());
        }

        @Override
        public int getSingleData(String activity, String day) {
            return (int) data.stream().filter(p -> p.get1() == activity && p.get2() == day).count();
        }

        @Override
        public boolean isValid() {
            if (!boundsOnActivities.isEmpty()) {
                for (var act : boundsOnActivities.keySet()) {
                    int timeAct = 0;
                    for (var day : days()) {
                        timeAct = timeAct + getSingleData(act, day);
                    }
                    if (timeAct >= boundsOnActivities.get(act)) {
                        return false;
                    }
                }
            }
            if (!boundsOnDays.isEmpty()) {
                for (var day : boundsOnDays.keySet()) {
                    int timeDay = 0;
                    for (var act : activities()) {
                        timeDay = timeDay + getSingleData(act, day);
                    }
                    if (timeDay >= boundsOnDays.get(day)) {
                        return false;
                    }
                }
            }
            return true;
        }

    }

    @Override
    public TimeSheet ofRawData(List<Pair<String, String>> data) {
        return new TimesheetImpl(data, Collections.emptyMap(), Collections.emptyMap());
    }

    @Override
    public TimeSheet withBoundsPerActivity(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities) {
        return new TimesheetImpl(data, boundsOnActivities, Collections.emptyMap());
    }

    @Override
    public TimeSheet withBoundsPerDay(List<Pair<String, String>> data, Map<String, Integer> boundsOnDays) {
        return new TimesheetImpl(data, Collections.emptyMap(), boundsOnDays);

    }

    @Override
    public TimeSheet withBounds(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities,
            Map<String, Integer> boundsOnDays) {
        return new TimesheetImpl(data, boundsOnActivities, boundsOnDays);
    }

}
