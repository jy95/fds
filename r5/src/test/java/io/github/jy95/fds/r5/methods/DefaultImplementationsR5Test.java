package io.github.jy95.fds.r5.methods;

import io.github.jy95.fds.r5.config.DefaultImplementationsR5;
import org.junit.jupiter.api.Test;

import org.hl7.fhir.r5.model.Quantity;
import org.hl7.fhir.r5.model.CodeableConcept;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultImplementationsR5Test {

    @Test
    void fromFHIRQuantityUnitToStringNull() {
        assertNull(DefaultImplementationsR5.fromFHIRQuantityUnitToString(null).join());
    }

    @Test
    void fromFHIRQuantityUnitToStringWithCode() {
        Quantity quantity = new Quantity().setCode("mg");
        assertEquals("mg", DefaultImplementationsR5.fromFHIRQuantityUnitToString(quantity).join());
    }

    @Test
    void fromFHIRQuantityUnitToStringUnknown() {
        Quantity quantity = new Quantity();
        assertEquals("", DefaultImplementationsR5.fromFHIRQuantityUnitToString(quantity).join());
    }

    @Test
    void fromCodeableConceptToStringNull() {
        assertNull(DefaultImplementationsR5.fromCodeableConceptToString(null).join());
    }

    @Test
    void fromCodeableConceptToStringEmptyCoding() {
        CodeableConcept codeableConcept = new CodeableConcept();
        assertNull(DefaultImplementationsR5.fromCodeableConceptToString(codeableConcept).join());
    }

}
