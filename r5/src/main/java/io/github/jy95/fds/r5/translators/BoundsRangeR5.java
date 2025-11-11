package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.BoundsRange;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.RangeToStringR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.boundsRange"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class BoundsRangeR5 implements BoundsRange<FDSConfigR5, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var boundsRange = dosage.getTiming().getRepeat().getBoundsRange();
        var boundsRangeMsg = translationService.getMessage(KEY_BOUNDS_RANGE);

        return RangeToStringR5
                .INSTANCE
                .convert(translationService, boundsRange)
                .thenApplyAsync(v -> boundsRangeMsg.format(new Object[]{v}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsRange();
    }
}
