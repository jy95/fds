package io.github.jy95.r4.translators;

import io.github.jy95.common.types.AbstractTranslator;
import io.github.jy95.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrequencyFrequencyMaxPeriodPeriodMaxR4 extends AbstractTranslator<FDSConfigR4, Dosage> {

    private final FrequencyFrequencyMaxR4 frequencyFrequencyMaxR4;
    private final PeriodPeriodMaxR4 periodPeriodMaxR4;

    public FrequencyFrequencyMaxPeriodPeriodMaxR4(FDSConfigR4 config) {
        super(config);
        frequencyFrequencyMaxR4 = new FrequencyFrequencyMaxR4(config);
        periodPeriodMaxR4 = new PeriodPeriodMaxR4(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var frequencyPart = frequencyFrequencyMaxR4.isPresent(dosage)
                ? frequencyFrequencyMaxR4.convert(dosage)
                : CompletableFuture.completedFuture("");

        var periodPart = periodPeriodMaxR4.isPresent(dosage)
                ? periodPeriodMaxR4.convert(dosage)
                : CompletableFuture.completedFuture("");

        return frequencyPart.thenCombineAsync(periodPart, (freq, period) -> Stream.of(freq, period)
                .filter(part -> !part.isEmpty())
                .collect(Collectors.joining(" ")));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return frequencyFrequencyMaxR4.isPresent(dosage) || periodPeriodMaxR4.isPresent(dosage);
    }
}
