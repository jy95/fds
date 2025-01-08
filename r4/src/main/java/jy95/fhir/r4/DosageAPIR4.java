package jy95.fhir.r4;

import jy95.fhir.common.functions.SequenceUtils;
import jy95.fhir.common.types.AbstractTranslator;
import jy95.fhir.common.types.DisplayOrder;
import jy95.fhir.common.types.DosageAPI;
import jy95.fhir.r4.config.FDSConfigR4;
import jy95.fhir.r4.utils.TranslatorsMapR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DosageAPIR4 extends DosageAPI<FDSConfigR4, Dosage> {

    private final TranslatorsMapR4 translatorsMapR4;

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
    protected CompletableFuture<String> convertConcurrentDosagesToText(List<Dosage> dosages) {
        var groupedDosages = SequenceUtils.groupBySequence(dosages, Dosage::getSequence);
        return convertGroupedDosagesToText(groupedDosages);
    }

}
