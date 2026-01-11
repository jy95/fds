package io.github.jy95.fds.r5;

import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.common.types.DisplayOrder;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public abstract class AbstractFhirTest {

    public static DosageAPIR5 getDosageAPI(Locale locale, DisplayOrder displayOrder) {
        return new DosageAPIR5(FDSConfigR5.builder()
                .displayOrder(List.of(displayOrder))
                .locale(locale)
                .build());
    }

    public static DosageAPIR5 getDosageAPI(FDSConfigR5 config) {
        return new DosageAPIR5(config);
    }

    public static Stream<Locale> localeProvider() {
        return Stream
                .of(
                        Locale.ENGLISH,
                        Locale.FRENCH,
                        Locale.forLanguageTag("nl-BE"),
                        Locale.GERMAN,
                        Locale.forLanguageTag("es"),
                        Locale.ITALIAN,
                        Locale.forLanguageTag("pt")
                );
    }
}
