package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslatorTiming;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.List;

public abstract class AbstractDurationDurationMax<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {

    // Translations
    protected final MessageFormat durationMsg;
    protected final MessageFormat durationMaxMsg;

    public AbstractDurationDurationMax(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg1 = bundle.getString("fields.duration");
        var msg2 = bundle.getString("fields.durationMax");
        durationMsg = new MessageFormat(msg1, locale);
        durationMaxMsg = new MessageFormat(msg2, locale);
    }

    @Override
    public CompletableFuture<String> convert(D dosage) {
        return CompletableFuture.supplyAsync(() -> {

            // Rule: duration SHALL be a non-negative value
            // Rule: if there's a duration, there needs to be duration units
            // Rule: If there's a durationMax, there must be a duration
            var hasDurationFlag = hasDuration(dosage);
            var hasDurationMaxFlag = hasDurationMax(dosage);
            var hasBoth = hasDurationFlag && hasDurationMaxFlag;

            List<String> texts = new ArrayList<>();

            if (hasDurationFlag) {
                texts.add(turnDurationToString(dosage));
            }

            if (hasBoth) {
                texts.add("(");
            }

            if (hasDurationMaxFlag) {
                texts.add(turnDurationMaxToString(dosage));
            }

            if (hasBoth) {
                texts.add(")");
            }

            return String.join(" ", texts);
        });
    }

    protected String quantityToString(String durationUnit, BigDecimal quantity){
        var bundle = this.getResources();
        var commonDurationMsg = bundle.getString("withCount." + durationUnit);
        return MessageFormat.format(commonDurationMsg, quantity);
    }

    protected abstract boolean hasDuration(D dosage);

    protected abstract boolean hasDurationMax(D dosage);

    protected abstract String turnDurationToString(D dosage);

    protected abstract String turnDurationMaxToString(D dosage);
}
