package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.BoundsDuration;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.QuantityToStringR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.boundsDuration"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class BoundsDurationR5 implements BoundsDuration<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

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
        return QuantityToStringR5
                .INSTANCE
                .convert(translationService, boundsDuration)
                .thenApplyAsync((durationText) -> boundsDurationMsg.format(new Object[]{durationText}));
    }
}
