package jy95.fhir.r4.dosage.utils.types;

import org.hl7.fhir.r4.model.Type;
import org.hl7.fhir.r4.model.Dosage.DosageDoseAndRateComponent;

/**
 * Represents the available fields in "doseAndRate"
 */
public enum DoseAndRateKey {
    DOSE_RANGE {
        @Override
        public Type extract(DosageDoseAndRateComponent doseAndRate) {
            return doseAndRate.getDoseRange();
        }
    },
    DOSE_QUANTITY {
        @Override
        public Type extract(DosageDoseAndRateComponent doseAndRate) {
            return doseAndRate.getDoseQuantity();
        }
    },
    RATE_RATIO {
        @Override
        public Type extract(DosageDoseAndRateComponent doseAndRate) {
            return doseAndRate.getRateRatio();
        }
    },
    RATE_RANGE {
        @Override
        public Type extract(DosageDoseAndRateComponent doseAndRate) {
            return doseAndRate.getRateRange();
        }
    },
    RATE_QUANTITY {
        @Override
        public Type extract(DosageDoseAndRateComponent doseAndRate) {
            return doseAndRate.getRateQuantity();
        }
    };

    public abstract Type extract(DosageDoseAndRateComponent doseAndRate);
}
