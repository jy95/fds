package io.github.jy95.fds.r5.utils;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.SpecComponent;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.utils.adapters.*;
import io.github.jy95.fds.r5.utils.maps.DosageTranslatorsMapR5;
import io.github.jy95.fds.r5.utils.maps.TimingRepeatTranslatorsMapR5;
import io.github.jy95.fds.r5.utils.maps.TimingTranslatorsMapR5;

import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Timing;
import org.hl7.fhir.r5.model.Timing.TimingRepeatComponent;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Map for R5 translators
 *
 * @author jy95
 */
public class TranslatorsMapR5 extends AbstractTranslatorsMap<FDSConfigR5, Dosage> {

    /**
     * Map for delegating components.
     * The key is the SpecComponent type, and the value is a function
     * that takes the DisplayOrder and returns the adapted Translator<Dosage>.
     */
    private final Map<SpecComponent, Function<DisplayOrder, Translator<Dosage>>> delegationMap;

    /**
     * Constructor for {@code TranslatorsMapR5}.
     *
     * @param translationService The translation service used for translation.
     */
    public TranslatorsMapR5(TranslationService<FDSConfigR5> translationService) {
        super(translationService);
        var dosageTranslatorsMapR5 = new DosageTranslatorsMapR5(translationService);
        var timingTranslatorsMapR5 = new TimingTranslatorsMapR5(translationService);
        var timingRepeatTranslatorsMapR5 = new TimingRepeatTranslatorsMapR5(translationService);

        delegationMap = Map.of(
            // Translators directly related to Dosage fields
            SpecComponent.DOSAGE,
            displayOrder -> dosageTranslatorsMapR5.getTranslator(displayOrder),
            // Translators directly related to Timing fields
            SpecComponent.TIMING,
            displayOrder -> adaptTimingTranslator(timingTranslatorsMapR5.getTranslator(displayOrder)),
            // Translators directly related to Timing.repeat fields
            SpecComponent.TIMING_REPEAT,
            displayOrder -> adaptRepeatTranslator(timingRepeatTranslatorsMapR5.getTranslator(displayOrder))
        );
    }

    /** {@inheritDoc} */
    @Override
    protected Map<DisplayOrder, Supplier<Translator<Dosage>>> createTranslatorsSuppliers() {
        // An empty map as delegation is in place here
        return Map.of();
    }

    /**
     * Adapt a TimingRepeatComponent Translator into a Dosage Translator
     * @param reTranslator a TimingRepeatComponent translator
     * @return a Dosage Translator
     */
    private Translator<Dosage> adaptRepeatTranslator(Translator<TimingRepeatComponent> reTranslator) {
        return new RepeatComponentTranslatorAdapterR5(reTranslator);
    }

    /**
     * Adapt a Timing Translator into a Dosage Translator
     * @param timeTranslator a Timing translator
     * @return a Dosage Translator
     */
    private Translator<Dosage> adaptTimingTranslator(Translator<Timing> timeTranslator) {
        return new TimingTranslatorAdapterR5(timeTranslator);
    }

    @Override
    public Translator<Dosage> getTranslator(DisplayOrder displayOrder) {
        var componentHandler = delegationMap.get(displayOrder.getComponent());
        return translatorCache.computeIfAbsent(displayOrder, key -> componentHandler.apply(key));
    }
}
