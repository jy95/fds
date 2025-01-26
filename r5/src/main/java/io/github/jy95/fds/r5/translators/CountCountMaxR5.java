package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.CountCountMax;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Dosage;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.count" / "timing.repeat.countMax"
 *
 * @author jy95
 */
public class CountCountMaxR5 implements CountCountMax<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "count" &amp; "countMax" translation */
    protected final MessageFormat countMaxMsg;
    /** MessageFormat instance used for "count" translation. */
    protected final MessageFormat countMsg;

    /**
     * Constructor for {@code CountCountMaxR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public CountCountMaxR5(FDSConfigR5 config, ResourceBundle bundle) {
        this.countMaxMsg = getCountMaxMsg(bundle, config.getLocale());
        this.countMsg = getCountMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat()
                && (dosage.getTiming().getRepeat().hasCount()
                || hasCountMax(dosage));
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasCountMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasCountMax();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            // Rule: If there's a countMax, there must be a count
            if (hasCountMax(dosage)) {
                Map<String, Object> arguments = Map.of(
                        "minCount", dosage.getTiming().getRepeat().getCount(),
                        "maxCount", dosage.getTiming().getRepeat().getCountMax()
                );
                return countMaxMsg.format(arguments);
            }

            return countMsg.format(new Object[]{
                    dosage.getTiming().getRepeat().getCount()
            });

        });
    }
}
