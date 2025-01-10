package io.github.jy95.fds.r4;

import io.github.jy95.fds.common.functions.SequenceUtils;
import io.github.jy95.fds.common.types.AbstractTranslator;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.utils.TranslatorsMapR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DosageAPIR4 extends DosageAPI<FDSConfigR4, Dosage> {

    private final TranslatorsMapR4 translatorsMapR4;

    public DosageAPIR4() {
        this(FDSConfigR4.builder().build());
    }

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