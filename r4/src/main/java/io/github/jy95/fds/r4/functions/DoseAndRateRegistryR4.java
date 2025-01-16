package io.github.jy95.fds.r4.functions;

import io.github.jy95.fds.common.types.DoseAndRateExtractor;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.common.types.DoseAndRateRegistry;
import org.hl7.fhir.r4.model.Dosage.DosageDoseAndRateComponent;
import org.hl7.fhir.r4.model.Type;

import java.util.EnumMap;
import java.util.Map;

/**
 * A registry specific to FHIR R4 for managing dose and rate components of a Dosage resource.
 * This class provides a mapping between {@link io.github.jy95.fds.common.types.DoseAndRateKey} and the corresponding getter methods
 * from {@link org.hl7.fhir.r4.model.Dosage.DosageDoseAndRateComponent}. It is implemented as a singleton.
 *
 * @author jy95
 */
public class DoseAndRateRegistryR4 implements DoseAndRateRegistry<DosageDoseAndRateComponent, Type> {

    /**
     * No constructor for this class
     */
    private DoseAndRateRegistryR4(){}

    // Static map holding the extractors
    private static final Map<DoseAndRateKey, DoseAndRateExtractor<DosageDoseAndRateComponent, Type>> extractors = new EnumMap<>(
            Map.ofEntries(
                    Map.entry(DoseAndRateKey.DOSE_QUANTITY, DosageDoseAndRateComponent::getDoseQuantity),
                    Map.entry(DoseAndRateKey.DOSE_RANGE, DosageDoseAndRateComponent::getDoseRange),
                    Map.entry(DoseAndRateKey.RATE_QUANTITY, DosageDoseAndRateComponent::getRateQuantity),
                    Map.entry(DoseAndRateKey.RATE_RANGE, DosageDoseAndRateComponent::getRateRange),
                    Map.entry(DoseAndRateKey.RATE_RATIO, DosageDoseAndRateComponent::getRateRatio)
            )
    );

    @Override
    public DoseAndRateExtractor<DosageDoseAndRateComponent, Type> getExtractor(DoseAndRateKey key) {
        return extractors.get(key);
    }

    /**
     * Static inner class responsible for holding the singleton instance.
     * This ensures the singleton is instantiated lazily and thread-safe.
     */
    private static class Holder {
        private static final DoseAndRateRegistryR4 INSTANCE = new DoseAndRateRegistryR4();
    }

    /**
     * Provides access to the singleton instance of {@link io.github.jy95.fds.r4.functions.DoseAndRateRegistryR4}.
     *
     * @return The singleton instance of {@link io.github.jy95.fds.r4.functions.DoseAndRateRegistryR4}.
     */
    public static DoseAndRateRegistryR4 getInstance() {
        return Holder.INSTANCE;
    }
}
