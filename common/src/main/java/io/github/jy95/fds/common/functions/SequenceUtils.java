package io.github.jy95.fds.common.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A utility class for handling sequences of dosages.
 * Provides methods for checking sequential instructions and grouping by
 * sequence.
 *
 * @author jy95
 * @since 1.0.0
 */
public final class SequenceUtils {

    /**
     * No constructor for this class
     */
    private SequenceUtils() {
    }

    /**
     * Checks if the given list of dosages contains only sequential instructions.
     *
     * <p>
     * This method checks if the dosages contain unique sequence numbers,
     * and determines if the instructions are sequential. A list of dosages is
     * considered sequential
     * if it either has no sequence numbers or has a sequence number for every
     * dosage in the list.
     * </p>
     *
     * @param dosages     The list of dosages to check.
     * @param getSequence A function that extracts the sequence number from each
     *                    dosage.
     * @param <T>         The type of the dosage object.
     * @return {@code true} if the dosages contain only sequential instructions,
     *         {@code false} otherwise.
     */
    public static <T> boolean containsOnlySequentialInstructions(List<T> dosages, Function<T, Integer> getSequence) {
        var encounteredSequenceNumbers = dosages
                .stream()
                .map(getSequence)
                .filter(i -> i > 0)
                .collect(Collectors.toSet());

        return encounteredSequenceNumbers.isEmpty() || encounteredSequenceNumbers.size() == dosages.size();
    }

    /**
     * Groups the given list of dosages by their sequence number.
     *
     * <p>
     * This method creates a map of dosages grouped by sequence number,
     * returning a list of lists where each inner list contains dosages with the
     * same sequence number.
     * </p>
     *
     * @param dosages     The list of dosages to group.
     * @param getSequence A function that extracts the sequence number from each
     *                    dosage.
     * @param <T>         The type of the dosage object.
     * @return A list of lists, where each inner list contains dosages grouped by
     *         their sequence number.
     */
    public static <T> List<List<T>> groupBySequence(List<T> dosages, Function<T, Integer> getSequence) {
        Map<Integer, List<T>> sequencesMap = new HashMap<>();

        for (T dosage : dosages) {
            var sequenceNr = getSequence.apply(dosage);
            sequencesMap.computeIfAbsent(sequenceNr, k -> new ArrayList<>()).add(dosage);
        }

        return new ArrayList<>(sequencesMap.values());
    }
}
