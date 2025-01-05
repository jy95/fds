package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.ListToString;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Enumeration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OffsetWhen extends AbstractTranslator {

    public OffsetWhen(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var offsetPart = turnOffsetToText(dosage);
        var whenPart = turnWhenToText(dosage);

        return offsetPart.thenCombineAsync(whenPart,(offsetText, whenText) -> Stream
                .of(offsetText, whenText)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" ")));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        // Rule: If there's an offset, there must be a when (and not C, CM, CD, CV)
        return dosage.hasTiming()
                && (dosage.getTiming().getRepeat().hasOffset() ||
                dosage.getTiming().getRepeat().hasWhen());
    }

    // To turn offset time (expressed as minutes) into days, hours and minutes
    private Map<String, Integer> extractTime(int minutes) {
        int d = minutes / 1440;
        int h = (minutes % 1440) / 60;
        int min = minutes % 60;

        return Map.of(
                "d", d,
                "h", h,
                "min", min
        );
    }

    private CompletableFuture<String> turnOffsetToText(Dosage dosage) {
        var bundle = getResources();
        var repeat = dosage.getTiming().getRepeat();

        if (!repeat.hasOffset()) {
            return CompletableFuture.completedFuture("");
        }

        return CompletableFuture.supplyAsync(() -> {
            var extractedTime = extractTime(repeat.getOffset());
            List<String> order = List.of("d", "h", "min");

            var times = order
                    .stream()
                    .filter(unit -> extractedTime.getOrDefault(unit, 0) > 0)
                    .map(unit -> {
                        var unitMsg = bundle.getString("withCount." + unit);
                        var amount = extractedTime.get(unit);
                        return MessageFormat.format(unitMsg, amount);
                    })
                    .toList();

            return ListToString.convert(bundle, times);
        });
    }

    private CompletableFuture<String> turnWhenToText(Dosage dosage) {
        var bundle = getResources();
        var repeat = dosage.getTiming().getRepeat();

        if (!repeat.hasWhen()) {
            return CompletableFuture.completedFuture("");
        }

        return CompletableFuture.supplyAsync(() -> {
            var events = repeat
                    .getWhen()
                    .stream()
                    .map(Enumeration::getCode)
                    .map(bundle::getString)
                    .toList();

            return ListToString.convert(bundle, events);
        });

    }
}
