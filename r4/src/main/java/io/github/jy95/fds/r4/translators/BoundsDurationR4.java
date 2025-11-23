package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.BoundsDuration;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.boundsDuration"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class BoundsDurationR4 implements Translator<TimingRepeatComponent> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(TimingRepeatComponent data) {
        return data.hasBoundsDuration();
    }


    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(TimingRepeatComponent repeat) {
        var boundsDuration = repeat.getBoundsDuration();
        var boundsDurationMsg = translationService.getMessage(BoundsDuration.KEY_BOUNDS_DURATION);

        return QuantityToStringR4
                .INSTANCE
                .convert(translationService, boundsDuration)
                .thenApplyAsync((durationText) -> boundsDurationMsg.format(new Object[]{durationText}));
    }
}
