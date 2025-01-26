package io.github.jy95.fds.r5.functions;

import io.github.jy95.fds.common.types.DoseAndRateExtractor;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.common.types.DoseAndRateRegistry;
import org.hl7.fhir.r5.model.Dosage.DosageDoseAndRateComponent;
import org.hl7.fhir.r5.model.DataType;

import java.util.EnumMap;
import java.util.Map;

/**
 * A registry specific to FHIR R5 for managing dose and rate components of a Dosage resource.
 * This class provides a mapping between {@link io.github.jy95.fds.common.types.DoseAndRateKey} and the corresponding getter methods
 * from {@link org.hl7.fhir.r5.model.Dosage.DosageDoseAndRateComponent}. It is implemented as a singleton.
 *
 * @author jy95
 */
public class DoseAndRateRegistryR5 implements DoseAndRateRegistry<DosageDoseAndRateComponent, DataType> {

    // Static map holding the extractors
    private static final Map<DoseAndRateKey, DoseAndRateExtractor<DosageDoseAndRateComponent, DataType>> extractors = new EnumMap<>(
            Map.ofEntries(
                    Map.entry(DoseAndRateKey.DOSE_QUANTITY, DosageDoseAndRateComponent::getDoseQuantity),
                    Map.entry(DoseAndRateKey.DOSE_RANGE, DosageDoseAndRateComponent::getDoseRange),
                    Map.entry(DoseAndRateKey.RATE_QUANTITY, DosageDoseAndRateComponent::getRateQuantity),
                    Map.entry(DoseAndRateKey.RATE_RANGE, DosageDoseAndRateComponent::getRateRange),
                    Map.entry(DoseAndRateKey.RATE_RATIO, DosageDoseAndRateComponent::getRateRatio)
            )
    );

    /**
     * No constructor for this class
     */
    private DoseAndRateRegistryR5(){}

    /** {@inheritDoc} */
    @Override
    public DoseAndRateExtractor<DosageDoseAndRateComponent, DataType> getExtractor(DoseAndRateKey key) {
        return extractors.get(key);
    }

    /**
     * Static inner class responsible for holding the singleton instance.
     * This ensures the singleton is instantiated lazily and thread-safe.
     */
    private static class Holder {
        private static final DoseAndRateRegistryR5 INSTANCE = new DoseAndRateRegistryR5();
    }

    /**
     * Provides access to the singleton instance of {@link io.github.jy95.fds.r5.functions.DoseAndRateRegistryR5}.
     *
     * @return The singleton instance of {@link io.github.jy95.fds.r5.functions.DoseAndRateRegistryR5}.
     */
    public static DoseAndRateRegistryR5 getInstance() {
        return Holder.INSTANCE;
    }
}
