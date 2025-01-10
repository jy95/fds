package io.github.jy95.r4.functions;

import io.github.jy95.common.types.AbstractDoseAndRateRegistry;

import io.github.jy95.common.types.DoseAndRateKey;
import org.hl7.fhir.r4.model.Dosage.DosageDoseAndRateComponent;
import org.hl7.fhir.r4.model.Type;

import java.util.Map;
import java.util.EnumMap;

public class DoseAndRateRegistryR4 extends AbstractDoseAndRateRegistry<DosageDoseAndRateComponent, Type> {

    public DoseAndRateRegistryR4() {
        super(
                new EnumMap<>(
                        Map.ofEntries(
                                Map.entry(DoseAndRateKey.DOSE_QUANTITY, DosageDoseAndRateComponent::getDoseQuantity),
                                Map.entry(DoseAndRateKey.DOSE_RANGE, DosageDoseAndRateComponent::getDoseRange),
                                Map.entry(DoseAndRateKey.RATE_QUANTITY, DosageDoseAndRateComponent::getRateQuantity),
                                Map.entry(DoseAndRateKey.RATE_RANGE, DosageDoseAndRateComponent::getRateRange),
                                Map.entry(DoseAndRateKey.RATE_RATIO, DosageDoseAndRateComponent::getRateRatio)
                        )
                )
        );
    }

    // Static inner class responsible for holding the singleton instance
    private static class Holder {
        private static final DoseAndRateRegistryR4 INSTANCE = new DoseAndRateRegistryR4();
    }

    // Public method to provide access to the singleton instance
    public static DoseAndRateRegistryR4 getInstance() {
        return Holder.INSTANCE;
    }
}
