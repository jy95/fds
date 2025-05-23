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
            public Path getOutputDir(Locale locale, Path folder) {
                String outputFolderName = getOutputName(folder);
                return fs.getPath("src", "site", locale.getLanguage(), "markdown", "examples", outputFolderName);
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

        assertTrue(Files.exists(outputFile1), "Output file 1 should exist");
        assertTrue(Files.exists(outputFile2), "Output file 2 should exist");
        assertTrue(Files.exists(outputFile3), "Output file 3 should exist");

    }
}
