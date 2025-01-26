package io.github.jy95.fds.r5;

import io.github.jy95.fds.common.functions.SequenceUtils;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.utils.TranslatorsMapR5;
import org.hl7.fhir.r5.model.Dosage;

import java.util.List;

/**
 * R5 class for translating and formatting dosage data
 *
 * @author jy95
 */
public class DosageAPIR5 extends DosageAPI<FDSConfigR5, Dosage> {

    /**
     * Map to retrieve R5 translators for various fields
     */
    private final TranslatorsMapR5 translatorsMapR5;

    /**
     * Default constructor for {@code DosageAPIR5} using default configuration
     */
    public DosageAPIR5() {
        this(FDSConfigR5.builder().build());
    }

    /**
     * Constructor for {@code DosageAPIR5}.
     *
     * @param config The configuration object used for translation.
     */
    public DosageAPIR5(FDSConfigR5 config) {
        super(config);
        var bundle = config.getSelectResourceBundle().apply(config.getLocale());
        translatorsMapR5 = new TranslatorsMapR5(config, bundle);
    }

    /** {@inheritDoc} */
    @Override
    public Translator<FDSConfigR5, Dosage> getTranslator(DisplayOrder displayOrder) {
        return translatorsMapR5.getTranslator(displayOrder);
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsOnlySequentialInstructions(List<Dosage> dosages) {
        return SequenceUtils.containsOnlySequentialInstructions(dosages, Dosage::getSequence);
    }

    /** {@inheritDoc} */
    @Override
    protected List<List<Dosage>> groupBySequence(List<Dosage> dosages) {
        return SequenceUtils.groupBySequence(dosages, Dosage::getSequence);
    }

}
