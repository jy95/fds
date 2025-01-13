package io.github.jy95.fds.r4;

import io.github.jy95.fds.common.functions.SequenceUtils;
import io.github.jy95.fds.common.types.AbstractTranslator;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.utils.TranslatorsMapR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;

/**
 * R4 class for translating and formatting dosage data
 */
public class DosageAPIR4 extends DosageAPI<FDSConfigR4, Dosage> {

    /**
     * Map to retrieve R4 translators for various fields
     */
    private final TranslatorsMapR4 translatorsMapR4;

    /**
     * Default constructor for {@code DosageAPIR4} using default configuration
     */
    public DosageAPIR4() {
        this(FDSConfigR4.builder().build());
    }

    /**
     * Constructor for {@code DosageAPIR4}.
     * @param config The configuration object used for translation.
     */
    public DosageAPIR4(FDSConfigR4 config) {
        super(config);
        translatorsMapR4 = new TranslatorsMapR4(config);
    }

    @Override
    public AbstractTranslator<FDSConfigR4, Dosage> getTranslator(DisplayOrder displayOrder) {
        return translatorsMapR4.getTranslator(displayOrder);
    }

    @Override
    public boolean containsOnlySequentialInstructions(List<Dosage> dosages) {
        return SequenceUtils.containsOnlySequentialInstructions(dosages, Dosage::getSequence);
    }

    @Override
    protected List<List<Dosage>> groupBySequence(List<Dosage> dosages) {
        return SequenceUtils.groupBySequence(dosages, Dosage::getSequence);
    }

}
