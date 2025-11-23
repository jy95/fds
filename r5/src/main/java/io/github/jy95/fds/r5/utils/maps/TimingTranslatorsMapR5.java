package io.github.jy95.fds.r5.utils.maps;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import org.hl7.fhir.r5.model.Timing;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.translators.*;

public class TimingTranslatorsMapR5 extends AbstractTranslatorsMap<FDSConfigR5, Timing> {

    /**
     * Constructor for {@code TimingTranslatorsMapR5}.
     *
     * @param translationService The translation service used for translation.
     */
    public TimingTranslatorsMapR5(TranslationService<FDSConfigR5> translationService) {
        super(translationService);
    }

    @Override
    protected Map<DisplayOrder, Supplier<Translator<Timing>>> createTranslatorsSuppliers() {
        EnumMap<DisplayOrder, Supplier<Translator<Timing>>> suppliers = new EnumMap<>(DisplayOrder.class);

        suppliers.put(DisplayOrder.TIMING_EXTENSION, () -> new TimingExtensionR5(translationService));
        suppliers.put(DisplayOrder.TIMING_MODIFIER_EXTENSION, () -> new TimingModifierExtensionR5(translationService));
        suppliers.put(DisplayOrder.TIMING_CODE, () -> new TimingCodeR5(translationService));
        suppliers.put(DisplayOrder.TIMING_EVENT, () -> new TimingEventR5(translationService));

        return suppliers;
    }

}
