package io.github.jy95.fds;

import org.junit.jupiter.api.Test;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.function.Supplier;

public class TranslatorsMapTest {

    private static class TranslatorsMap extends AbstractTranslatorsMap<FDSConfig, String> {

        public TranslatorsMap(TranslationService<FDSConfig> translationService) {
            super(translationService);
        }

        @Override
        protected Map<DisplayOrder, Supplier<Translator<String>>> createTranslatorsSuppliers() {
            return Map.of();
        }

    }

    @Test
    void unsupportedOperation() {
        var emptyMap = new TranslatorsMap(null);
        assertEquals(null, emptyMap.getTranslator(DisplayOrder.TEXT));
    }
}
