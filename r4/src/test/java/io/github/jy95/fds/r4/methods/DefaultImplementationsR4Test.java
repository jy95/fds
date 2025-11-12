package io.github.jy95.fds.r4.methods;

import io.github.jy95.fds.r4.config.DefaultImplementationsR4;
import org.junit.jupiter.api.Test;

import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.CodeableConcept;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultImplementationsR4Test {

    @Test
    void fromFHIRQuantityUnitToStringNull() {
        assertNull(DefaultImplementationsR4.fromFHIRQuantityUnitToString(null).join());
    }

    @Test
    void fromFHIRQuantityUnitToStringWithCode() {
        Quantity quantity = new Quantity().setCode("mg");
        assertEquals("mg", DefaultImplementationsR4.fromFHIRQuantityUnitToString(quantity).join());
    }

    @Test
    void fromFHIRQuantityUnitToStringUnknown() {
        Quantity quantity = new Quantity();
        assertEquals("", DefaultImplementationsR4.fromFHIRQuantityUnitToString(quantity).join());
    }

    @Test
    void fromCodeableConceptToStringNull() {
        assertNull(DefaultImplementationsR4.fromCodeableConceptToString(null).join());
    }

    @Test
    void fromCodeableConceptToStringEmptyCoding() {
        CodeableConcept codeableConcept = new CodeableConcept();
        assertNull(DefaultImplementationsR4.fromCodeableConceptToString(codeableConcept).join());
    }
}
