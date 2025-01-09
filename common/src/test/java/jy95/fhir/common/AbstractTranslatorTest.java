package jy95.fhir.common;

import jy95.fhir.common.types.AbstractTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import jy95.fhir.common.config.FDSConfig;
import static org.junit.jupiter.api.Assertions.*;

public class AbstractTranslatorTest {

    // Concrete implementation of AbstractTranslator
    private static class TestTranslator extends AbstractTranslator<FDSConfig, String> {

        public TestTranslator(FDSConfig config) {
            super(config);
        }

        @Override
        public CompletableFuture<String> convert(String dosage) {
            return CompletableFuture.completedFuture("Converted: " + dosage);
        }
    }

    private AbstractTranslator<FDSConfig, String> translator;

    @BeforeEach
    void setUp() {
        translator = new TestTranslator(FDSConfig.builder().build());
    }

    @Test
    void testConvert() {
        String input = "dosage123";
        CompletableFuture<String> result = translator.convert(input);

        assertNotNull(result);
        assertEquals("Converted: dosage123", result.join());
    }

    @Test
    void testIsPresent() {
        assertFalse(translator.isPresent("dosage123"));
    }
}
