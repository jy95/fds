package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.common.translators.OffsetWhen;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Enumeration;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * R5 class for translating "timing.repeat.offset" / "timing.repeat.when"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class OffsetWhenR5 implements OffsetWhen<FDSConfigR5, Dosage> {

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * The locale for translation.
     */
    private final Locale locale;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var offsetPart = turnOffsetToText(dosage);
        var whenPart = turnWhenToText(dosage);

        return offsetPart.thenCombineAsync(whenPart,(offsetText, whenText) -> Stream
                .of(offsetText, whenText)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" ")));
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        var timing = dosage.getTiming();
        // Rule: If there's an offset, there must be a when (and not C, CM, CD, CV)
        return timing.hasRepeat() && (timing.getRepeat().hasOffset() || timing.getRepeat().hasWhen());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    private CompletableFuture<String> turnWhenToText(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();

        if (!repeat.hasWhen()) {
            return CompletableFuture.completedFuture("");
        }

        return CompletableFuture.supplyAsync(() -> {
            var events = repeat
                    .getWhen()
                    .stream()
                    .map(Enumeration::getCode)
                    .map(bundle::getString)
                    .toList();

            return ListToString.convert(bundle, events);
        });
    }

    private CompletableFuture<String> turnOffsetToText(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();

        if (!repeat.hasOffset()) {
            return CompletableFuture.completedFuture("");
        }
        return turnOffsetValueToText(repeat.getOffset());
    }

    /**
     * Converts an offset value (in minutes) into a human-readable time string.
     * The result combines the extracted time components (days, hours, minutes) into a formatted string.
     *
     * @param offset The offset in minutes to be converted.
     * @return A {@link java.util.concurrent.CompletableFuture} containing the formatted string representing the offset.
     */
    protected CompletableFuture<String> turnOffsetValueToText(int offset) {
        return CompletableFuture.supplyAsync(() -> {
            var extractedTime = extractTime(offset);

            var times = OffsetWhen
                    .order
                    .stream()
                    .filter(unit -> extractedTime.getOrDefault(unit, 0) > 0)
                    .map(unit -> {
                        var amount = extractedTime.get(unit);
                        return UnitsOfTimeFormatter.formatWithCount(locale, unit, amount);
                    })
                    .toList();

            return ListToString.convert(bundle, times);
        });
    }
}
