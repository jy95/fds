package io.github.jy95.fds.r4.internal;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.utils.DosageMarkdownR4;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.common.config.FDSConfig;

import java.util.List;
import java.util.Locale;

public class DosageMarkdownExecutor {

    /**
     * A custom implementation for the specification examples
     */
    static class SpecsR4 extends DosageMarkdownR4 {

        @Override
        public List<Locale> getLocales() {
            // Only English at the moment
            return List.of(Locale.ENGLISH);
        }

        @Override
        public DosageAPIR4 createDosageAPI(Locale locale) {

            // Add the text in the rendering order as by default, it isn't
            var renderOrder = FDSConfig
                    .builder()
                    .build()
                    .getDisplayOrder();
            renderOrder.add(DisplayOrder.TEXT);

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
