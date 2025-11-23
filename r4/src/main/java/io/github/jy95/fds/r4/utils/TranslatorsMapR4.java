package io.github.jy95.fds.r4.utils;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.utils.adapters.*;
import io.github.jy95.fds.r4.utils.maps.DosageTranslatorsMapR4;
import io.github.jy95.fds.r4.utils.maps.TimingRepeatTranslatorsMapR4;
import io.github.jy95.fds.r4.utils.maps.TimingTranslatorsMapR4;

import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;
import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Map for R4 translators
 *
 * @author jy95
 */
public class TranslatorsMapR4 extends AbstractTranslatorsMap<FDSConfigR4, Dosage> {

    /**
     * Translators directly related to Dosage fields
     */
    private final DosageTranslatorsMapR4 dosageTranslatorsMapR4;
    /**
     * Translators directly related to Timing fields
     */
    private final TimingTranslatorsMapR4 timingTranslatorsMapR4;
    /**
     * Translators directly related to Timing.repeat fields
     */
    private final TimingRepeatTranslatorsMapR4 timingRepeatTranslatorsMapR4;

    /**
     * Constructor for {@code TranslatorsMapR4}.
     *
     * @param translationService The translation service used for translation.
     */
    public TranslatorsMapR4(TranslationService<FDSConfigR4> translationService) {
        super(translationService);
        dosageTranslatorsMapR4 = new DosageTranslatorsMapR4(translationService);
        timingTranslatorsMapR4 = new TimingTranslatorsMapR4(translationService);
        timingRepeatTranslatorsMapR4 = new TimingRepeatTranslatorsMapR4(translationService);
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
        return new RepeatComponentTranslatorAdapterR4(reTranslator);
    }

    /**
     * Adapt a Timing Translator into a Dosage Translator
     * @param timeTranslator a Timing translator
     * @return a Dosage Translator
     */
    private Translator<Dosage> adaptTimingTranslator(Translator<Timing> timeTranslator) {
        return new TimingTranslatorAdapterR4(timeTranslator);
    }

    @Override
    public Translator<Dosage> getTranslator(DisplayOrder displayOrder) {
        return translatorCache.computeIfAbsent(displayOrder, key -> {
            return switch(displayOrder.getComponent()) {
                case TIMING -> adaptTimingTranslator(timingTranslatorsMapR4.getTranslator(displayOrder));
                case TIMING_REPEAT -> adaptRepeatTranslator(timingRepeatTranslatorsMapR4.getTranslator(displayOrder));
                default -> dosageTranslatorsMapR4.getTranslator(displayOrder);
            };
        });
    }

}
