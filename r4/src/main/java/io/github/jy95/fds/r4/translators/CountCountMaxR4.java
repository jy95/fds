package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractCountCountMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class CountCountMaxR4 extends AbstractCountCountMax<FDSConfigR4, Dosage> {

    public CountCountMaxR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var repeat = dosage.getTiming().getRepeat();

            // Rule: If there's a countMax, there must be a count
            if (repeat.hasCountMax()) {
                return turnCountAndCountMaxToText(repeat.getCount(), repeat.getCountMax());
            }
            return turnCountToText(repeat.getCount());
        });
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
}
