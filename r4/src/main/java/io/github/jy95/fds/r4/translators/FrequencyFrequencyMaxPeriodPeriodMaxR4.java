package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractFrequencyFrequencyMaxPeriodPeriodMax;
import io.github.jy95.fds.common.types.AbstractTranslator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * R4 class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax" / "timing.repeat.period" / "timing.repeat.periodMax"
 *
 * @author jy95
 */
public class FrequencyFrequencyMaxPeriodPeriodMaxR4 extends AbstractFrequencyFrequencyMaxPeriodPeriodMax<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code FrequencyFrequencyMaxPeriodPeriodMaxR4}.
     *
     * @param config The configuration object used for translation.
     * @param frequencyTranslator The translator for "timing.repeat.frequency" / "timing.repeat.frequencyMax" fields
     * @param periodTranslator The translator for "timing.repeat.period" / "timing.repeat.periodMax" fields
     */
    public FrequencyFrequencyMaxPeriodPeriodMaxR4(FDSConfigR4 config,
                                                  AbstractTranslator<FDSConfigR4, Dosage> frequencyTranslator,
                                                  AbstractTranslator<FDSConfigR4, Dosage> periodTranslator) {
        super(config, frequencyTranslator, periodTranslator);
    }
}
