package jy95.fhir.r4.dosage.utils;

import org.hl7.fhir.r4.model.Dosage;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import jy95.fhir.r4.dosage.utils.classes.DosageUtils;

public class DosageUtilsTest {

    @Test
    void testContainsOnlySequentialInstructions_emptyList() {
        List<Dosage> dosages = new ArrayList<>();
        assertTrue(DosageUtils.containsOnlySequentialInstructions(dosages));
    }

    @Test
    void testContainsOnlySequentialInstructions_allSequential() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2),
                createDosage(3)
        );
        assertTrue(DosageUtils.containsOnlySequentialInstructions(dosages));
    }

    @Test
    void testContainsOnlySequentialInstructions_nonSequential() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2), // Missing sequence 2
                createDosage(2)
        );
        assertFalse(DosageUtils.containsOnlySequentialInstructions(dosages));
    }

    @Test
    void testContainsOnlySequentialInstructions_withZeros() {
        List<Dosage> dosages = List.of(
                new Dosage()
        );
        assertTrue(DosageUtils.containsOnlySequentialInstructions(dosages));
    }

    @Test
    void testGroupBySequence_emptyList() {
        List<Dosage> dosages = new ArrayList<>();
        List<List<Dosage>> groups = DosageUtils.groupBySequence(dosages);
        assertTrue(groups.isEmpty());
    }

    @Test
    void testGroupBySequence_allSequential() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2),
                createDosage(3)
        );
        List<List<Dosage>> groups = DosageUtils.groupBySequence(dosages);
        assertEquals(3, groups.size());
        assertEquals(dosages.getFirst(), groups.getFirst().getFirst());
    }

    @Test
    void testGroupBySequence_multipleSequences() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2),
                createDosage(3)
        );
        List<List<Dosage>> groups = DosageUtils.groupBySequence(dosages);
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
