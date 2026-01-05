package io.github.jy95.fds.utilities;

import java.util.Locale;
import java.util.stream.Stream;

public class LocaleProviderBase {

    // The locales to support
    public static Stream<Locale> localeProvider() {
        return Stream
                .of(
                        Locale.ENGLISH,
                        Locale.FRENCH,
                        Locale.forLanguageTag("nl-BE"),
                        Locale.GERMAN,
                        Locale.forLanguageTag("es"),
                        Locale.ITALIAN
                );
    }
}
