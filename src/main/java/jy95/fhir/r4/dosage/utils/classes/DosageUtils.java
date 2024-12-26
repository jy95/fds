package jy95.fhir.r4.dosage.utils.classes;

import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
// import java.util.Objects;

public class DosageUtils {

    public static boolean containsOnlySequentialInstructions(List<Dosage> dosages) {
        var encounteredSequenceNumbers = dosages
                .stream()
                .map(Dosage::getSequence)
                .filter(i -> i > 0)
                .collect(Collectors.toSet());

        return encounteredSequenceNumbers.isEmpty() || encounteredSequenceNumbers.size() == dosages.size();
    }

    public static List<List<Dosage>> groupBySequence(List<Dosage> dosages){
        Set<Integer> sequences = new HashSet<>();
        Map<Integer, List<Dosage>> sequencesMap = new HashMap<>();

        for (Dosage dosage : dosages) {
            var sequenceNr = dosage.getSequence();
            List<Dosage> localGroup = sequencesMap.getOrDefault(sequenceNr, new ArrayList<>());
            localGroup.add(dosage);
            sequencesMap.put(sequenceNr, localGroup);
            sequences.add(sequenceNr);
        }

        return sequences
                .stream()
                .map(sequencesMap::get)
                .collect(Collectors.toList());
    }
}
