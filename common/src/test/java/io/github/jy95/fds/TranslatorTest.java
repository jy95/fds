package io.github.jy95.fds;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

public class TranslatorTest {

    // Concrete implementation of Translator
    private static class TestTranslator implements Translator<FDSConfig, String> {

        @Override
        public CompletableFuture<String> convert(String dosage) {
            return CompletableFuture.completedFuture("Converted: " + dosage);
        }
    }

    private Translator<FDSConfig, String> translator;

    @BeforeEach
    void setUp() {
        translator = new TestTranslator();
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
