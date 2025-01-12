package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractCountCountMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class CountCountMaxR4 extends AbstractCountCountMax<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code CountCountMaxR4}.
     * @param config The configuration object used for translation.
     */
    public CountCountMaxR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat()
                && (dosage.getTiming().getRepeat().hasCount()
                || dosage.getTiming().getRepeat().hasCountMax());
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    @Override
    protected int getCountMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().getCountMax();
    }

    @Override
    protected int getCount(Dosage dosage) {
        return dosage.getTiming().getRepeat().getCount();
    }

    @Override
    protected boolean hasCountMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasCountMax();
    }
}
