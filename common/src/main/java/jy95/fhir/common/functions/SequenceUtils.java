package jy95.fhir.common.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SequenceUtils {

    public static <T> boolean containsOnlySequentialInstructions(List<T> dosages, Function<T, Integer> getSequence) {
        var encounteredSequenceNumbers = dosages
                .stream()
                .map(getSequence)
                .filter(i -> i > 0)
                .collect(Collectors.toSet());

        return encounteredSequenceNumbers.isEmpty() || encounteredSequenceNumbers.size() == dosages.size();
    }

    public static <T> List<List<T>> groupBySequence(List<T> dosages, Function<T, Integer> getSequence) {
        Map<Integer, List<T>> sequencesMap = new HashMap<>();

        for (T dosage : dosages) {
            var sequenceNr = getSequence.apply(dosage);
            sequencesMap.computeIfAbsent(sequenceNr, k -> new ArrayList<>()).add(dosage);
        }

        return new ArrayList<>(sequencesMap.values());
    }
}
