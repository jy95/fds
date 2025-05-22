package io.github.jy95.fds;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import io.github.jy95.fds.common.types.DosageMarkdown;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.common.config.FDSConfig;

import static org.junit.jupiter.api.Assertions.*;

public class DosageMarkdownTest {
    
    // Dummy implementation of DosageAPI with string as dosage type
    private static class DummyDosageAPI extends DosageAPI<FDSConfig, String> {

        public DummyDosageAPI() {
            super(FDSConfig.builder().build());
        }

        @Override
        public Translator<FDSConfig, String> getTranslator(DisplayOrder displayOrder) {
            return (dosage) -> CompletableFuture.completedFuture(dosage);
        }

        @Override
        public boolean containsOnlySequentialInstructions(List<String> dosages) {
            return true;
        }

        @Override
        protected List<List<String>> groupBySequence(List<String> dosages) {
            return Collections.singletonList(dosages);
        }
    }

    private static class DummyMarkdown implements DosageMarkdown<DummyDosageAPI, String> {

        @Override
        public DummyDosageAPI createDosageAPI(Locale locale) {
            return new DummyDosageAPI();
        }

        @Override
        public List<String> getDosageFromJson(Path jsonFile) throws IOException {
            // Return dummy dosage entries based on file name
            return List.of("dosage_from_" + jsonFile.getFileName());
        }

    }

    private DosageMarkdown<DummyDosageAPI, String> dosageMarkdown = new DummyMarkdown();

    @Test
    void testDefaultGetResourcesDir() {
        Path expected = Path.of("src", "site", "resources", "examples");
        assertEquals(expected, dosageMarkdown.getResourcesDir(), "Default getResourcesDir should match expected path");
    }

    @Test
    void testDefaultLocales() {
        Locale expected = Locale.ENGLISH;
        assertTrue(dosageMarkdown.getLocales().contains(expected), "Locales should include English");
    }

    @Test
    void testGetOutputDirRoot() {
        Path folder = Path.of("src", "site", "resources", "examples");
        Path expected = Path.of("src", "site", "en", "markdown", "examples", "general");

        assertEquals(expected, dosageMarkdown.getOutputDir(Locale.ENGLISH, folder));
    }

    @Test
    void testGetOutputDirSimple() {
        Path folder = Path.of("src", "site", "resources", "examples", "text");
        Path expected = Path.of("src", "site", "en", "markdown", "examples", "text");

        assertEquals(expected, dosageMarkdown.getOutputDir(Locale.ENGLISH, folder));
    }

    @Test
    void testGetOutputDirNested() {
        Path folder = Path.of("src", "site", "resources", "examples", "text", "complex");
        Path expected = Path.of("src", "site", "en", "markdown", "examples", "text", "complex");

        assertEquals(expected, dosageMarkdown.getOutputDir(Locale.ENGLISH, folder));
    }
}
