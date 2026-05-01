package io.github.jy95.fds.r4.utils.maps;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import org.hl7.fhir.r4.model.Timing;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.common.types.CodeableConceptTranslator;
import io.github.jy95.fds.common.types.ExtensionTranslator;
import io.github.jy95.fds.common.translators.timing.TimingEvent;
import io.github.jy95.fds.r4.config.FDSConfigR4;

public class TimingTranslatorsMapR4 extends AbstractTranslatorsMap<FDSConfigR4, Timing> {

    /**
     * Constructor for {@code TimingTranslatorsMapR4}.
     *
     * @param translationService The translation service used for translation.
     */
    public TimingTranslatorsMapR4(TranslationService<FDSConfigR4> translationService) {
        super(translationService);
    }

    @Override
    protected Map<DisplayOrder, Supplier<Translator<Timing>>> createTranslatorsSuppliers() {
        EnumMap<DisplayOrder, Supplier<Translator<Timing>>> suppliers = new EnumMap<>(DisplayOrder.class);

        suppliers.put(DisplayOrder.TIMING_EXTENSION, () -> new ExtensionTranslator<>(
                translationService,
                Timing::getExtension,
                Timing::hasExtension
        ));
        suppliers.put(DisplayOrder.TIMING_MODIFIER_EXTENSION, () -> new ExtensionTranslator<>(
                translationService,
                Timing::getModifierExtension,
                Timing::hasModifierExtension
        ));
        suppliers.put(DisplayOrder.TIMING_CODE, () -> new CodeableConceptTranslator<>(translationService, Timing::getCode, Timing::hasCode));
        suppliers.put(DisplayOrder.TIMING_EVENT, () -> new TimingEvent<>(
                translationService,
                Timing::getEvent,
                Timing::hasEvent,
                event -> translationService.dateTimeToHumanDisplay(event.getValue(), event.getTimeZone(), event.getPrecision())
        ));

        return suppliers;
    }

}
