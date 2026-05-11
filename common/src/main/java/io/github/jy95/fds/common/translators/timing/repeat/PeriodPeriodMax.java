package io.github.jy95.fds.common.translators.timing.repeat;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.GenericOperations;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.common.types.Translator;
import lombok.Builder;

/**
 * Generic class for translating "timing.repeat.period" / "timing.repeat.periodMax" across different FHIR versions.
 *
 * @param <D> The type of the data object containing the period and periodMax fields.
 * @param <C> The type of the FDSConfig used for translation.
 * @author jy95
 * @since 2.1.9
 */
@Builder
public class PeriodPeriodMax<D, C extends FDSConfig> implements Translator<D> {
    
    /** Translation service */
    private final TranslationService<C> translationService;
    /** Function to check the presence of a period value in the data object */
    private final Predicate<D> hasPeriod;
    /** Function to check the presence of a periodMax value in the data object */
    private final Predicate<D> hasPeriodMax;
    /** Function to check the presence of period units in the data object */
    private final Predicate<D> hasPeriodUnit;
    /** Function to extract the period value from the data object */
    private final Function<D, BigDecimal> getPeriod;
    /** Function to extract the periodMax value from the data object */
    private final Function<D, BigDecimal> getPeriodMax;
    /** Function to extract the period unit from the data object */
    private final Function<D, String> getPeriodUnit;


    @Override
    public boolean isPresent(D data) {
        var hasValue = GenericOperations.anyMatchLazy(
            () -> hasPeriod.test(data), 
            () -> hasPeriodMax.test(data)
        );
        return GenericOperations.allMatchLazy(
            () -> hasValue, 
            () -> hasPeriodUnit.test(data)
        );
    }

    @Override
    public CompletableFuture<String> convert(D data) {
        return CompletableFuture.supplyAsync(() -> {

            var hasBoth = GenericOperations.allMatchLazy(
                () -> hasPeriod.test(data), 
                () -> hasPeriodMax.test(data)
            );

            return GenericOperations.conditionalSelect(
                    hasBoth,
                    () -> turnPeriodAndPeriodMaxToString(data),
                    () -> turnPeriodToString(data)
                );
        });
    }

    /**
     * Converts both period and periodMax values into a formatted string.
     *
     * @param data The data object containing the values.
     * @return A formatted string representing both period and periodMax.
     */
    private String turnPeriodAndPeriodMaxToString(D data) {
        
        var periodMax = getPeriodMax.apply(data);
        var periodMin = getPeriod.apply(data);
        var periodUnit = getPeriodUnit.apply(data);

        var config = translationService.getConfig();

        var unitText = UnitsOfTimeFormatter.formatWithoutCount(config.getLocale(), periodUnit, periodMax);
        return formatPeriodAndPeriodMaxText(periodMin, periodMax, unitText);
    }

    /**
     * Converts the period value into a formatted string.
     *
     * @param data The data object containing the period value.
     * @return A formatted string representing the period.
     */
    private String turnPeriodToString(D data) {
        
        var period = getPeriod.apply(data);
        var periodUnit = getPeriodUnit.apply(data);
        var config = translationService.getConfig();

        var unitText = UnitsOfTimeFormatter.formatWithoutCount(config.getLocale(), periodUnit, period);
        return formatPeriodText(period, unitText);
    }

    /**
     * Formats a single-period value with its unit.
     *
     * @param period The period value.
     * @param unitText The localized unit of the period.
     * @return A formatted string representing the period and its unit.
     */
    private String formatPeriodText(BigDecimal period, String unitText){
        Map<String, Object> arguments = Map.of(
                "period", period,
                "periodUnit",unitText
        );
        var periodMsg = translationService.getMessage("fields.period");
        return periodMsg.format(arguments);
    }

    /**
     * Formats both period and periodMax values with their shared unit.
     *
     * @param periodMin The minimum period value.
     * @param periodMax The maximum period value.
     * @param unitText The localized unit of the periods.
     * @return A formatted string representing the range of periods and their unit.
     */
    private String formatPeriodAndPeriodMaxText(BigDecimal periodMin, BigDecimal periodMax, String unitText) {
        Map<String, Object> arguments = Map.of(
                "maxPeriod", periodMax,
                "minPeriod", periodMin,
                "unit",unitText
        );
        var periodMaxMsg = translationService.getMessage("fields.periodMax");
        return periodMaxMsg.format(arguments);
    }
}
