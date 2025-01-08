package jy95.fhir.r4;

import org.hl7.fhir.r4.model.Dosage;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import jy95.fhir.r4.dosage.utils.classes.Utils;

public class UtilsTest {

    @Test
    void testContainsOnlySequentialInstructionsEmptyList() {
        List<Dosage> dosages = new ArrayList<>();
        assertTrue(Utils.containsOnlySequentialInstructions(dosages));
    }

    @Test
    void testContainsOnlySequentialInstructionsAllSequential() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2),
                createDosage(3)
        );
        assertTrue(Utils.containsOnlySequentialInstructions(dosages));
    }

    @Test
    void testContainsOnlySequentialInstructionsNonSequential() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2), // Missing sequence 2
                createDosage(2)
        );
        assertFalse(Utils.containsOnlySequentialInstructions(dosages));
    }

    @Test
    void testContainsOnlySequentialInstructionsWithZeros() {
        List<Dosage> dosages = List.of(
                new Dosage()
        );
        assertTrue(Utils.containsOnlySequentialInstructions(dosages));
    }

    @Test
    void testGroupBySequenceEmptyList() {
        List<Dosage> dosages = new ArrayList<>();
        List<List<Dosage>> groups = Utils.groupBySequence(dosages);
        assertTrue(groups.isEmpty());
    }

    @Test
    void testGroupBySequenceAllSequential() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2),
                createDosage(3)
        );
        List<List<Dosage>> groups = Utils.groupBySequence(dosages);
        assertEquals(3, groups.size());
        assertEquals(dosages.getFirst(), groups.getFirst().getFirst());
    }

    @Test
    void testGroupBySequenceMultipleSequences() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2),
                createDosage(3)
        );
        List<List<Dosage>> groups = Utils.groupBySequence(dosages);
        assertEquals(3, groups.size());

        for (int i = 0; i < dosages.size(); i++) {
            List<Dosage> groupTest = groups.get(i);
            assertTrue(groupTest.contains(dosages.get(i)));
        }
    }

    private Dosage createDosage(int sequence) {
        Dosage dosage = new Dosage();
        dosage.setSequence(sequence);
        return dosage;
    }
}
