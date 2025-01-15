package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractCountCountMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

/**
 * R4 class for translating "timing.repeat.count" / "timing.repeat.countMax"
 *
 * @author jy95
 */
public class CountCountMaxR4 extends AbstractCountCountMax<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code CountCountMaxR4}.
     *
     * @param config The configuration object used for translation.
     */
    public CountCountMaxR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat()
                && (dosage.getTiming().getRepeat().hasCount()
                || dosage.getTiming().getRepeat().hasCountMax());
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    protected int getCountMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().getCountMax();
    }

    /** {@inheritDoc} */
    @Override
    protected int getCount(Dosage dosage) {
        return dosage.getTiming().getRepeat().getCount();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasCountMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasCountMax();
    }
}
