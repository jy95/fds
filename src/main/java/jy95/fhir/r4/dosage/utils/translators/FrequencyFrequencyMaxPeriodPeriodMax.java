package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrequencyFrequencyMaxPeriodPeriodMax extends AbstractTranslator {

    private final FrequencyFrequencyMax frequencyFrequencyMax;
    private final PeriodPeriodMax periodPeriodMax;

    public FrequencyFrequencyMaxPeriodPeriodMax(FDUConfig config) {
        super(config);
        this.frequencyFrequencyMax = new FrequencyFrequencyMax(config);
        this.periodPeriodMax = new PeriodPeriodMax(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var frequencyPart = frequencyFrequencyMax.isPresent(dosage)
                ? frequencyFrequencyMax.convert(dosage)
                : CompletableFuture.completedFuture("");

        var periodPart = periodPeriodMax.isPresent(dosage)
                ? periodPeriodMax.convert(dosage)
                : CompletableFuture.completedFuture("");

        return frequencyPart.thenCombineAsync(periodPart, (freq, period) -> Stream.of(freq, period)
                .filter(part -> !part.isEmpty())
                .collect(Collectors.joining(" ")));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return frequencyFrequencyMax.isPresent(dosage) || periodPeriodMax.isPresent(dosage);
    }
}
