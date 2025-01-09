package jy95.fhir.common;

import jy95.fhir.common.types.DisplayOrder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DisplayOrderTest {

    @Test
    public void testDisplayOrderInitialization() {
        // Ensure that all DisplayOrder values have non-null fields.
        for (DisplayOrder order : DisplayOrder.values()) {
            assertNotNull(order.getFields(), "Fields should not be null for " + order);
            assertFalse(order.getFields().isEmpty(), "Fields should contain at least one entry for " + order);
        }
    }

    @Test
    public void testSpecificDisplayOrder() {
        // Test a specific DisplayOrder for expected functionality.
        List<String> fields = DisplayOrder.METHOD.getFields();
        assertNotNull(fields, "Fields for METHOD should not be null");
        assertTrue(fields.contains("method"), "Fields for METHOD should contain 'method'");
    }
}
