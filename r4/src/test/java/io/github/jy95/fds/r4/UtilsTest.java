package io.github.jy95.fds.r4;

import org.hl7.fhir.r4.model.Dosage;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.jy95.fds.common.functions.SequenceUtils;

public class UtilsTest {

    @Test
    void testContainsOnlySequentialInstructionsEmptyList() {
        List<Dosage> dosages = new ArrayList<>();
        assertTrue(SequenceUtils.containsOnlySequentialInstructions(dosages, Dosage::getSequence));
    }

    @Test
    void testContainsOnlySequentialInstructionsAllSequential() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2),
                createDosage(3)
        );
        assertTrue(SequenceUtils.containsOnlySequentialInstructions(dosages, Dosage::getSequence));
    }

    @Test
    void testContainsOnlySequentialInstructionsNonSequential() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2), // Missing sequence 2
                createDosage(2)
        );
        assertFalse(SequenceUtils.containsOnlySequentialInstructions(dosages, Dosage::getSequence));
    }

    @Test
    void testContainsOnlySequentialInstructionsWithZeros() {
        List<Dosage> dosages = List.of(
                new Dosage()
        );
        assertTrue(SequenceUtils.containsOnlySequentialInstructions(dosages, Dosage::getSequence));
    }

    @Test
    void testGroupBySequenceEmptyList() {
        List<Dosage> dosages = new ArrayList<>();
        List<List<Dosage>> groups = SequenceUtils.groupBySequence(dosages, Dosage::getSequence);
        assertTrue(groups.isEmpty());
    }

    @Test
    void testGroupBySequenceAllSequential() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2),
                createDosage(3)
        );
        List<List<Dosage>> groups = SequenceUtils.groupBySequence(dosages, Dosage::getSequence);
        assertEquals(3, groups.size());
        assertEquals(dosages.get(0), groups.get(0).get(0));
    }

    @Test
    void testGroupBySequenceMultipleSequences() {
        List<Dosage> dosages = List.of(
                createDosage(1),
                createDosage(2),
                createDosage(3)
        );
        List<List<Dosage>> groups = SequenceUtils.groupBySequence(dosages, Dosage::getSequence);
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
