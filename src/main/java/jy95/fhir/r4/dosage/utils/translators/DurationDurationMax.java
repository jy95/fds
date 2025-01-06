package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DurationDurationMax extends AbstractTranslator {

    public DurationDurationMax(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var repeat = dosage.getTiming().getRepeat();

            // Rule: duration SHALL be a non-negative value
            // Rule: if there's a duration, there needs to be duration units
            // Rule: If there's a durationMax, there must be a duration
            var hasDuration = repeat.hasDuration();
            var hasDurationMax = repeat.hasDurationMax();
            var hasBoth = hasDuration && hasDurationMax;

            List<String> texts = new ArrayList<>();

            if (hasDuration) {
                texts.add(turnDurationToString(dosage));
            }

            if (hasBoth) {
                texts.add("(");
            }

            if (hasDurationMax) {
                texts.add(turnDurationMaxToString(dosage));
            }

            if (hasBoth) {
                texts.add(")");
            }

            return String.join(" ", texts);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat()
                && dosage.getTiming().getRepeat().hasDurationUnit()
                && (dosage.getTiming().getRepeat().hasDuration() ||
                dosage.getTiming().getRepeat().hasDurationMax());
    }

    private String turnDurationToString(Dosage dosage) {
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();

        var repeat = dosage.getTiming().getRepeat();
        var durationUnit = repeat.getDurationUnit().toCode();
        var durationQuantity = repeat.getDuration();

        var durationMsg = bundle.getString("fields.duration");
        var durationText = quantityToString(durationUnit, durationQuantity);
        return new MessageFormat(durationMsg, locale).format(new Object[]{durationText});
    }

    private String turnDurationMaxToString(Dosage dosage) {
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();

        var repeat = dosage.getTiming().getRepeat();
        var durationUnit = repeat.getDurationUnit().toCode();
        var durationQuantity = repeat.getDurationMax();

        var durationMsg = bundle.getString("fields.durationMax");
        var durationText = quantityToString(durationUnit, durationQuantity);
        return new MessageFormat(durationMsg, locale).format(new Object[]{durationText});
    }

    private String quantityToString(String durationUnit, BigDecimal quantity){
        var bundle = this.getResources();
        var commonDurationMsg = bundle.getString("withCount." + durationUnit);
        return MessageFormat.format(commonDurationMsg, quantity);
    }

}
