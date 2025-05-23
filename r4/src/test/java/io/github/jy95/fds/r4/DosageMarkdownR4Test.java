package io.github.jy95.fds.r4;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

import io.github.jy95.fds.r4.utils.DosageMarkdownR4;
import io.roastedroot.zerofs.Configuration;
import io.roastedroot.zerofs.ZeroFs;

public class DosageMarkdownR4Test {

    private void assertMarkdownFileContent(Path markdownFile, String headerContains, String... contentContains) throws IOException {
        assertTrue(Files.exists(markdownFile), "Markdown file should exist: " + markdownFile);
        String content = Files.readString(markdownFile);
        assertTrue(content.contains("# " + headerContains), "Header should contain: " + headerContains + " in " + markdownFile);
        for (String expectedContent : contentContains) {
            assertTrue(content.contains(expectedContent), "Content should contain: " + expectedContent + " in " + markdownFile);
        }
    }

    @Test
    void testGenerateMarkdown_realFlowInVirtualFS() throws Exception {
        // Step 1: Virtual filesystem setup
        var fs = ZeroFs.newFileSystem(Configuration.unix());
        var srcFolder = fs.getPath("src", "site", "resources", "examples");
        Files.createDirectories(srcFolder);

        // Step 2: Create dummy JSON files
        var jsonFile1 = srcFolder.resolve("simple.json");
        String jsonContent = "{\"additionalInstruction\": [\"Instruction 1\"]}";
        Files.writeString(jsonFile1, jsonContent, StandardCharsets.UTF_8);
        var jsonFile2 = srcFolder.resolve("complex.json");
        String jsonContent2 = "[{\"additionalInstruction\": [\"Instruction 1\", \"Instruction 2\"]}]";
        Files.writeString(jsonFile2, jsonContent2, StandardCharsets.UTF_8);

        // Step 3: Custom subclass only for directory override
        class InMemoryMarkdown extends DosageMarkdownR4 {
            @Override
            public Path getResourcesDir() {
                return srcFolder;
            }

            @Override
            public Path getBaseOutputDir(Locale locale) {
                return fs.getPath("src", "site", locale.getLanguage(), "markdown", "examples");
            }
        }
        var inMemoryMarkdown = new InMemoryMarkdown();

        // Step 4: Run real logic
        inMemoryMarkdown.generateMarkdown();

        // Step 5: Assert the output
        // 5.1 : Check that the locale folder was created
        var localeFolder = fs.getPath("src", "site", "en", "markdown", "examples");
        assertTrue(Files.exists(localeFolder), "Locale folder should exist");

        // 5.2 : Check that the output files were created
        var outputFile = localeFolder.resolve("examples.md");

        // Step 6: Assert the content of the files using the reusable method
        assertMarkdownFileContent(outputFile, "examples", "Instruction 1");
        assertMarkdownFileContent(outputFile, "examples",  "Instruction 2");
        fs.close();
    }
}
