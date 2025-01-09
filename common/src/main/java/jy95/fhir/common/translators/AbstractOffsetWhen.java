package jy95.fhir.common.translators;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.functions.ListToString;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractOffsetWhen<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    public AbstractOffsetWhen(C config) {
        super(config);
    }

    // To turn offset time (expressed as minutes) into days, hours and minutes
    protected Map<String, Integer> extractTime(int minutes) {
        int d = minutes / 1440;
        int h = (minutes % 1440) / 60;
        int min = minutes % 60;

        return Map.of(
                "d", d,
                "h", h,
                "min", min
        );
    }

    protected CompletableFuture<String> turnOffsetValueToText(int offset) { 
        return CompletableFuture.supplyAsync(() -> {
            var bundle = getResources();
            var extractedTime = extractTime(offset);
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
}
