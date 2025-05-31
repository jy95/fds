package io.github.jy95.fds.r4.internal;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.utils.DosageMarkdownR4;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.common.config.FDSConfig;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DosageMarkdownExecutor {

    /**
     * A custom implementation for the specification examples
     */
    static class SpecsR4 extends DosageMarkdownR4 {

        private final String BASE_PATH = new File("").getAbsolutePath();
        private final String ROOT_PATH = "r4";

        @Override
        public Path getResourcesDir() {
            return Paths.get(BASE_PATH,ROOT_PATH, "src", "site", "resources", "specs");
        }

        @Override
        public Path getBaseOutputDir(Locale locale) {
            return Paths.get(BASE_PATH,ROOT_PATH, "src", "site", "markdown", "specs");
        }

        @Override
        public List<Locale> getLocales() {
            // Only English at the moment
            return List.of(Locale.ENGLISH);
        }

        @Override
        public DosageAPIR4 createDosageAPI(Locale locale) {

            // Add the text in the rendering order as by default, it isn't
            var renderOrder = Stream
                    .concat(
                            FDSConfig
                                    .builder()
                                    .build()
                                    .getDisplayOrder()
                                    .stream(),
                            Stream.of(DisplayOrder.TEXT)
                    )
                    .collect(Collectors.toList());

            // Return custom instance for docs
            return new DosageAPIR4(
                    FDSConfigR4
                            .builder()
                            .displayOrder(renderOrder)
                            .locale(locale)
                            .build()
            );
        }
    }

    public static void main(String[] args) throws Exception {
        var specsExamples = new SpecsR4();
        specsExamples.generateMarkdown();
    }
}
