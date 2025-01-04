package a02b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RulesEngineFactoryImpl implements RulesEngineFactory {

    @Override
    public <T> List<List<T>> applyRule(Pair<T, List<T>> rule, List<T> input) {
        List<List<T>> list = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).equals(rule.get1())) {
                var current = new ArrayList<>(input);
                current.remove(i);
                current.addAll(i, rule.get2());
                list.add(current);
            } 
        }
        return list;
    }

    @Override
    public <T> RulesEngine<T> singleRuleEngine(Pair<T, List<T>> rule) {
        return new RulesEngine<T>() {

            private final List<T> input = new ArrayList<>();
            private boolean done;

            @Override
            public void resetInput(List<T> input) {
                this.input.clear();
                done = false;
                this.input.addAll(input);
            }

            @Override
            public boolean hasOtherSolutions() {
                return !done;
            }

            @Override
            public List<T> nextSolution() {
                this.done = true;
                return input.stream()
                        .flatMap(t -> t.equals(rule.get1()) ? rule.get2().stream() : Stream.of(t))
                        .toList();
            }
            
        };
    }

    @Override
    public <T> RulesEngine<T> cascadingRulesEngine(Pair<T, List<T>> baseRule, Pair<T, List<T>> cascadeRule) {
        return new RulesEngine<T>() {

            private final List<T> input = new ArrayList<>();
            private boolean done;

            @Override
            public void resetInput(List<T> input) {
                this.input.clear();
                done = false;
                this.input.addAll(input);
            }

            @Override
            public boolean hasOtherSolutions() {
                return !done;
            }

            @Override
            public List<T> nextSolution() {
                this.done = true;
                return input.stream()
                        .flatMap(t -> t.equals(baseRule.get1()) ? baseRule.get2().stream() : Stream.of(t))
                        .flatMap(t -> t.equals(cascadeRule.get1()) ? cascadeRule.get2().stream() : Stream.of(t))
                        .toList();
            }
            
        };
    }

    @Override
    public <T> RulesEngine<T> conflictingRulesEngine(Pair<T, List<T>> rule1, Pair<T, List<T>> rule2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'conflictingRulesEngine'");
    }

}
