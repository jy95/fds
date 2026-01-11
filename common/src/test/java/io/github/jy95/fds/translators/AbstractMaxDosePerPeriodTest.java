package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractMaxDosePerPeriodTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoMaxDosePerPeriod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.MAX_DOSE_PER_PERIOD);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMaxDosePerPeriod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithMaxDosePerPeriod();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.MAX_DOSE_PER_PERIOD);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText(locale), result);
    }

    protected abstract D generateWithMaxDosePerPeriod();

    private String getExpectedText(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "jusqu’à un maximum de 10 mg par jour";
            case "de"    -> "bis zu einer maximalen Menge von 10 mg pro Tag";
            case "es"    -> "hasta un máximo de 10 mg por día";
            case "it"    -> "fino a un massimo di 10 mg per giorno";
            case "nl-BE" -> "tot een maximum van 10 mg per dag";
            case "pt" -> "até um máximo de 10 mg por dia";
            default      -> "up to a maximum of 10 mg per day";
        };
    }
}