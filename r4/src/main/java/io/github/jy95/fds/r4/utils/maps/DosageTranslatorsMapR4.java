package io.github.jy95.fds.r4.utils.maps;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import org.hl7.fhir.r4.model.Dosage;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.common.types.SimpleStringTranslator;
import io.github.jy95.fds.common.types.CodeableConceptTranslator;
import io.github.jy95.fds.common.types.ExtensionTranslator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.translators.*;

public class DosageTranslatorsMapR4 extends AbstractTranslatorsMap<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code DosageTranslatorsMapR4}.
     *
     * @param translationService The translation service used for translation.
     */
    public DosageTranslatorsMapR4(TranslationService<FDSConfigR4> translationService) {
        super(translationService);
    }

    /** {@inheritDoc} */
    @Override
    protected Map<DisplayOrder, Supplier<Translator<Dosage>>> createTranslatorsSuppliers() {
        EnumMap<DisplayOrder, Supplier<Translator<Dosage>>> suppliers = new EnumMap<>(DisplayOrder.class);

        suppliers.put(DisplayOrder.EXTENSION, () -> new ExtensionTranslator<>(
                translationService,
                Dosage::getExtension,
                Dosage::hasExtension
        ));
        suppliers.put(DisplayOrder.MODIFIER_EXTENSION, () -> new ExtensionTranslator<>(
                translationService,
                Dosage::getModifierExtension,
                Dosage::hasModifierExtension
        ));
        suppliers.put(DisplayOrder.TEXT, () -> new SimpleStringTranslator<Dosage>(Dosage::getText, Dosage::hasText));
        suppliers.put(DisplayOrder.ADDITIONAL_INSTRUCTION, () -> new AdditionalInstructionR4(translationService));
        suppliers.put(DisplayOrder.PATIENT_INSTRUCTION, () -> new SimpleStringTranslator<Dosage>(Dosage::getPatientInstruction, Dosage::hasPatientInstruction));
        suppliers.put(DisplayOrder.AS_NEEDED, () -> new AsNeededR4(translationService));
        suppliers.put(DisplayOrder.SITE, () -> new CodeableConceptTranslator<>(
                translationService,
                Dosage::getSite,
                Dosage::hasSite
        ));
        suppliers.put(DisplayOrder.ROUTE, () -> new CodeableConceptTranslator<>(
                translationService,
                Dosage::getRoute,
                Dosage::hasRoute
        ));
        suppliers.put(DisplayOrder.METHOD, () -> new CodeableConceptTranslator<>(
                translationService,
                Dosage::getMethod,
                Dosage::hasMethod
        ));
        suppliers.put(DisplayOrder.DOSE_QUANTITY, () -> new DoseQuantityR4(translationService));
        suppliers.put(DisplayOrder.DOSE_RANGE, () -> new DoseRangeR4(translationService));
        suppliers.put(DisplayOrder.RATE_QUANTITY, () -> new RateQuantityR4(translationService));
        suppliers.put(DisplayOrder.RATE_RANGE, () -> new RateRangeR4(translationService));
        suppliers.put(DisplayOrder.RATE_RATIO, () -> new RateRatioR4(translationService));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_LIFETIME, () -> new MaxDosePerLifetimeR4(translationService));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_ADMINISTRATION, () -> new MaxDosePerAdministrationR4(translationService));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_PERIOD, () -> new MaxDosePerPeriodR4(translationService));

        return suppliers;
    }

}
