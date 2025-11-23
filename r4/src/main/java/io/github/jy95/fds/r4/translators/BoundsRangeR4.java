package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.BoundsRange;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RangeToStringR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.boundsRange"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class BoundsRangeR4 implements BoundsRange<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

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

        return RangeToStringR4
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
