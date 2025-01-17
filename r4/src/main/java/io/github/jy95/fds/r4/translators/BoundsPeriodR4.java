package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.BoundsPeriod;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.FormatDateTimesR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.boundsPeriod"
 *
 * @author jy95
 */
public class BoundsPeriodR4 implements BoundsPeriod<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "boundsPeriod" translation. */
    protected final MessageFormat boundsPeriodMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * Constructor for {@code BoundsPeriodR4}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public BoundsPeriodR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.config = config;
        this.boundsPeriodMsg = getBoundsPeriodMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsPeriod();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasStartPeriod(Dosage dosage) {
        return dosage.getTiming().getRepeat().getBoundsPeriod().hasStart();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasEndPeriod(Dosage dosage) {
        return dosage.getTiming().getRepeat().getBoundsPeriod().hasEnd();
    }

    /** {@inheritDoc} */
    @Override
    public String formatStartPeriod(Dosage dosage) {
        var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
        var locale = config.getLocale();
        return FormatDateTimesR4.getInstance().convert(locale, boundPeriods.getStartElement());
    }

    /** {@inheritDoc} */
    @Override
    public String formatEndPeriod(Dosage dosage) {
        var locale = config.getLocale();
        var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
        return FormatDateTimesR4.getInstance().convert(locale, boundPeriods.getEndElement());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var arguments = extractInformation(dosage);
            // Format the message with the named arguments
            return boundsPeriodMsg.format(arguments);
        });
    }
}
