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

import io.roastedroot.zerofs.Configuration;
import io.roastedroot.zerofs.ZeroFs;

public class DosageMarkdownTest {
    
    // Dummy implementation of DosageAPI with string as dosage type
    private static class DummyDosageAPI extends DosageAPI<FDSConfig, String> {

        public DummyDosageAPI() {
            super(FDSConfig
                .builder()
                .displayOrder(List.of(DisplayOrder.TEXT))
                .build()
            );
        }

        @Override
        public Translator<FDSConfig, String> getTranslator(DisplayOrder displayOrder) {
            return new Translator<FDSConfig, String>() {
                @Override
                public CompletableFuture<String> convert(String dosage) {
                    return CompletableFuture.completedFuture(dosage);
                }

                @Override
                public boolean isPresent(String dosage) {
                    return true;
                }
            };
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

    private void assertMarkdownFileContent(Path markdownFile, String headerContains, String... contentContains) throws IOException {
        assertTrue(Files.exists(markdownFile), "Markdown file should exist: " + markdownFile);
        String content = Files.readString(markdownFile);
        assertTrue(content.contains("# " + headerContains), "Header should contain: " + headerContains + " in " + markdownFile);
        for (String expectedContent : contentContains) {
            assertTrue(content.contains(expectedContent), "Content should contain: " + expectedContent + " in " + markdownFile);
        }
    }

    @Test
    void testDeFaultLocales() {
        // Step 1: Create a dummy instance of the class
        var dosageMarkdown = new DummyMarkdown();

        // Step 2: Get the default locales
        var defaultLocales = dosageMarkdown.getLocales();

        // Step 3: Assert that the default locales are not empty
        assertFalse(defaultLocales.isEmpty(), "Default locales should not be empty");
    }

    @Test
    void testDefaultBaseOutputDir() {
        // Step 1: Create a dummy instance of the class
        var dosageMarkdown = new DummyMarkdown();
        // Step 2: Get the default base output directory
        var defaultBaseOutputDir = dosageMarkdown.getBaseOutputDir(Locale.ENGLISH);
        // Step 3: Assert that the default base output directory is not null        
        assertNotNull(defaultBaseOutputDir, "Default base output directory should not be null");
    }

    @Test
    void testDefaultResourcesDir() {
        // Step 1: Create a dummy instance of the class
        var dosageMarkdown = new DummyMarkdown();
        // Step 2: Get the default resources directory
        var defaultResourcesDir = dosageMarkdown.getResourcesDir();
        // Step 3: Assert that the default resources directory is not null
        assertNotNull(defaultResourcesDir, "Default resources directory should not be null");
    }

    @Test
    void testGenerateMarkdown_realFlowInVirtualFS() throws Exception {

        // Step 1: Virtual filesystem setup
        var fs = ZeroFs.newFileSystem(Configuration.unix());
        var srcFolder = fs.getPath("src", "site", "resources", "examples");
        Files.createDirectories(srcFolder);
        Files.createDirectories(srcFolder.resolve("text"));
        Files.createDirectories(srcFolder.resolve("text").resolve("nested"));

        // Step 2: Create fake JSON input files
        var jsonFile1 = fs.getPath("src", "site", "resources", "examples", "input1.json");
        var jsonFile2 = fs.getPath("src", "site", "resources", "examples", "text", "input2.json");
        var jsonFile3 = fs.getPath("src", "site", "resources", "examples", "text", "input3.json");
        var jsonFile4 = fs.getPath("src", "site", "resources", "examples", "text", "nested", "input4.json");
        var jsonFile5 = fs.getPath("src", "site", "resources", "examples", "text", "nested", "input5.json");
        Files.writeString(jsonFile1, "{}", StandardCharsets.UTF_8);
        Files.writeString(jsonFile2, "{}", StandardCharsets.UTF_8);
        Files.writeString(jsonFile3, "{}", StandardCharsets.UTF_8);
        Files.writeString(jsonFile4, "{}", StandardCharsets.UTF_8);
        Files.writeString(jsonFile5, "{}", StandardCharsets.UTF_8);

        // Step 3: Custom subclass only for directory override
        class InMemoryMarkdown extends DummyMarkdown {
            @Override
            public Path getResourcesDir() {
                return srcFolder;
            }

            @Override
            public Path getBaseOutputDir(Locale locale) {
                return fs.getPath("src", "site", locale.getLanguage(), "markdown", "examples");
            }

            @Override
            public List<Locale> getLocales() {
                return List.of(Locale.ENGLISH);
            }
        }
        var inMemoryMarkdown = new InMemoryMarkdown();

        // Step 4: Run real logic
        inMemoryMarkdown.generateMarkdown();

        // Step 5: Assert that output files were created

        // 5.1: Check the output directory
        var localeFolder = fs.getPath("src", "site", "en", "markdown", "examples");
        assertTrue(Files.exists(localeFolder), "Locale folder should exist");

        // 5.2 : Check that the output files were created
        var outputFile1 = localeFolder.resolve("examples.md");
        var outputFile2 = localeFolder.resolve("text.md");
        var outputFile3 = localeFolder.resolve("text").resolve("nested.md");

        // Step 6: Assert the content of the files using the reusable method
        assertMarkdownFileContent(outputFile1, "examples", "dosage_from_input1.json");
        assertMarkdownFileContent(outputFile2, "text", "dosage_from_input2.json", "dosage_from_input3.json");
        assertMarkdownFileContent(outputFile3, "nested", "dosage_from_input4.json", "dosage_from_input5.json");

    }
}
