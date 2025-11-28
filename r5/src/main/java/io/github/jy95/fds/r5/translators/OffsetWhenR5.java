package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.OffsetWhen;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r5.model.Timing.TimingRepeatComponent;
import org.hl7.fhir.r5.model.Enumeration;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * R5 class for translating "timing.repeat.offset" / "timing.repeat.when"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class OffsetWhenR5 implements OffsetWhen<TimingRepeatComponent> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(TimingRepeatComponent data) {
        var offsetPart = turnOffsetToText(data);
        var whenPart = turnWhenToText(data);

        return offsetPart.thenCombineAsync(whenPart,(offsetText, whenText) -> Stream
                .of(offsetText, whenText)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" ")));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(TimingRepeatComponent data) {
        // Rule: If there's an offset, there must be a when (and not C, CM, CD, CV)
        return data.hasOffset() || data.hasWhen();
    }

    private CompletableFuture<String> turnWhenToText(TimingRepeatComponent data) {

        if (!data.hasWhen()) {
            return CompletableFuture.completedFuture("");
        }

        return CompletableFuture.supplyAsync(() -> {
            var events = data
                    .getWhen()
                    .stream()
                    .map(Enumeration::getCode)
                    .map(translationService::getText)
                    .toList();

            return ListToString.convert(translationService, events);
        });
    }

    private CompletableFuture<String> turnOffsetToText(TimingRepeatComponent data) {

        if (!data.hasOffset()) {
            return CompletableFuture.completedFuture("");
        }

        return turnOffsetValueToText(translationService, data.getOffset());
    }

}
