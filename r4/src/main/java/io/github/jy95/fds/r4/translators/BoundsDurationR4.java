package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.BoundsDuration;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.boundsDuration"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class BoundsDurationR4 implements BoundsDuration<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsDuration();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var boundsDuration = dosage.getTiming().getRepeat().getBoundsDuration();
        var boundsDurationMsg = translationService.getMessage(KEY_BOUNDS_DURATION);

        return QuantityToStringR4
                .INSTANCE
                .convert(translationService, boundsDuration)
                .thenApplyAsync((durationText) -> boundsDurationMsg.format(new Object[]{durationText}));
    }
}
