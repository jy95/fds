package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.OffsetWhen;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Enumeration;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * R4 class for translating "timing.repeat.offset" / "timing.repeat.when"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class OffsetWhenR4 implements OffsetWhen<FDSConfigR4, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

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

        var bundle = translationService.getBundle();

        return CompletableFuture.supplyAsync(() -> {
            var events = repeat
                    .getWhen()
                    .stream()
                    .map(Enumeration::getCode)
                    .map(bundle::getString)
                    .toList();

            return ListToString.convert(translationService, events);
        });
    }

    private CompletableFuture<String> turnOffsetToText(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        if (!repeat.hasOffset()) {
            return CompletableFuture.completedFuture("");
        }
        return turnOffsetValueToText(translationService, repeat.getOffset());
    }
}
