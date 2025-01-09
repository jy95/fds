package jy95.fhir.r4.translators;

import jy95.fhir.common.functions.ListToString;
import jy95.fhir.common.translators.AbstractOffsetWhen;
import jy95.fhir.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Enumeration;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OffsetWhenR4 extends AbstractOffsetWhen<FDSConfigR4, Dosage> {

    public OffsetWhenR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var offsetPart = turnOffsetToText(dosage);
        var whenPart = turnWhenToText(dosage);

        return offsetPart.thenCombineAsync(whenPart,(offsetText, whenText) -> Stream
                .of(offsetText, whenText)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" ")));
    }

    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        var timing = dosage.getTiming();
        // Rule: If there's an offset, there must be a when (and not C, CM, CD, CV)
        return timing.hasRepeat() && (timing.getRepeat().hasOffset() || timing.getRepeat().hasWhen());
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    private CompletableFuture<String> turnWhenToText(Dosage dosage) {
        var bundle = getResources();
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
}
