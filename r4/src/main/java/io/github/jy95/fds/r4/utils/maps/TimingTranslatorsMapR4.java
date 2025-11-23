package io.github.jy95.fds.r4.utils.maps;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import org.hl7.fhir.r4.model.Timing;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.translators.*;

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

        suppliers.put(DisplayOrder.TIMING_EXTENSION, () -> new TimingExtensionR4(translationService));
        suppliers.put(DisplayOrder.TIMING_MODIFIER_EXTENSION, () -> new TimingModifierExtensionR4(translationService));
        suppliers.put(DisplayOrder.TIMING_CODE, () -> new TimingCodeR4(translationService));
        suppliers.put(DisplayOrder.TIMING_EVENT, () -> new TimingEventR4(translationService));

        return suppliers;
    }

}
