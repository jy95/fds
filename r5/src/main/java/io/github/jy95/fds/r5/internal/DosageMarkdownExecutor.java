package io.github.jy95.fds.r5.internal;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.utils.DosageMarkdownR5;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.common.config.FDSConfig;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>DosageMarkdownExecutor class.</p>
 *
 * @author runner
 * @since 1.0.6
 */
public class DosageMarkdownExecutor {

    /**
     * A custom implementation for the specification examples
     */
    static class SpecsR5 extends DosageMarkdownR5 {

        private final String BASE_PATH = new File("").getAbsolutePath();
        private final String ROOT_PATH = "r5";

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
        public DosageAPIR5 createDosageAPI(Locale locale) {

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
            return new DosageAPIR5(
                    FDSConfigR5
                            .builder()
                            .displayOrder(renderOrder)
                            .locale(locale)
                            .build()
            );
        }
    }

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {
        var specsExamples = new SpecsR5();
        specsExamples.generateMarkdown();
    }
}
