package io.github.jy95.r4;

import io.github.jy95.r4.config.FDSConfigR4;
import io.github.jy95.common.types.DisplayOrder;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public abstract class AbstractFhirTest {

    public static DosageAPIR4 getDosageAPI(Locale locale, DisplayOrder displayOrder) {
        return new DosageAPIR4(FDSConfigR4.builder()
                .displayOrder(List.of(displayOrder))
                .locale(locale)
                .build());
    }

    public static DosageAPIR4 getDosageAPI(FDSConfigR4 config) {
        return new DosageAPIR4(config);
    }

    public static Stream<Locale> localeProvider() {
        return Stream
                .of(
                        Locale.ENGLISH,
                        Locale.FRENCH,
                        Locale.forLanguageTag("nl-BE"),
                        Locale.GERMAN
                );
    }
}
