package io.github.jy95.fds.common.config;

import io.github.jy95.fds.common.bundle.MultiResourceBundleControl;
import io.github.jy95.fds.common.types.DoseAndRateExtractor;
import io.github.jy95.fds.common.types.DoseAndRateKey;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.ibm.icu.number.NumberFormatter;

import org.hl7.fhir.instance.model.api.IBaseExtension;
import org.hl7.fhir.instance.model.api.IPrimitiveType;
import org.hl7.fhir.instance.model.api.IBaseCoding;
import org.hl7.fhir.instance.model.api.IBase;

import lombok.NonNull;

/**
 * Provides default implementations for common operations in the library.
 *
 * @author jy95
 * @since 1.0.0
 */
public final class DefaultImplementations {

    // Define the new package path for resource bundles
    private static final String RESOURCE_PACKAGE = "io.github.jy95.fds.common.l10n.";

    // Initialize the MultiResourceBundleControl with resource bundle names
    private static final MultiResourceBundleControl BUNDLE_CONTROL =
        new MultiResourceBundleControl(
                "translations",
                RESOURCE_PACKAGE + "DosageFields",
                RESOURCE_PACKAGE + "EventTiming",
                RESOURCE_PACKAGE + "QuantityComparator"
        );

    /**
     * No constructor for this class
     */
    private DefaultImplementations(){}

    /**
     * Selects a ResourceBundle for the specified locale using a MultiResourceBundleControl.
     *
     * @param locale the locale for which the ResourceBundle is desired.
     * @return the aggregated ResourceBundle for the specified locale.
     */
    public static ResourceBundle selectResourceBundle(Locale locale) {
        return ResourceBundle.getBundle(
                BUNDLE_CONTROL.getBaseName(),
                locale,
                BUNDLE_CONTROL
        );
    }

    /**
     * Formats a BigDecimal quantity number for display.
     *
     * @param locale the locale for formatting.
     * @param value  the BigDecimal value to format.
     * @return the formatted string representation of the quantity number.
     * @since 2.1.0
     */
    public static String formatQuantityNumber(Locale locale, BigDecimal value) {
        return NumberFormatter.withLocale(locale).format(value).toString();
    }

    /**
     * Converts a list of FHIR {@link org.hl7.fhir.instance.model.api.IBaseExtension} objects to a JSON-like string representation.
     *
     * @param extensions the list of {@link org.hl7.fhir.instance.model.api.IBaseExtension} objects to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to a JSON-like string representing the extensions.
     * @since 2.1.1
     */
    public static CompletableFuture<String> fromExtensionsToString(@NonNull List<? extends IBaseExtension<?, ?>> extensions) {
        return CompletableFuture.supplyAsync(() -> {

            return extensions
                    .stream()
                    .map(ext -> {

                        StringBuilder sb = new StringBuilder();
                        sb.append("{");

                        var hasUrl = Objects.nonNull(ext.getUrl());
                        if (hasUrl) {
                            sb
                                .append("\"url\":\"")
                                .append(ext.getUrl())
                                .append("\"");
                        }

                        var hasValue = Objects.nonNull(ext.getValue());
                        if (hasUrl && hasValue) {
                            sb.append(",");
                        }

                        if (hasValue && ext.getValue() instanceof IPrimitiveType<?> valuIPrimitiveType) {
                            sb
                                .append("\"value[x]\":\"")
                                .append(
                                    valuIPrimitiveType.getValueAsString()
                                ).append("\"");
                        }

                        sb.append("}");
                        return sb.toString();
                    })
                    .collect(Collectors.joining(", ", "[", "]"));
        });
    }

    /**
     * Converts a FHIR {@link org.hl7.fhir.instance.model.api.IBaseCoding} to a string representation.
     *
     * @param coding the {@link org.hl7.fhir.instance.model.api.IBaseCoding} to be converted.
     * @return the display or code of the coding.
     * @since 2.1.1
     */
    public static String fromCodingToString(@NonNull IBaseCoding coding) {
        var display = coding.getDisplay();
        var code = coding.getCode();

        return (Objects.nonNull(display)) ? display : code;
    }

    /**
     * Selects a specific dosage and rate field from a list of dose and rate components.
     *
     * @param doseAndRateComponentList the list of dose and rate components.
     * @param doseAndRateKey the key indicating which field to extract.
     * @param extractorMap a map of dose and rate keys to their corresponding extractor functions.
     * @param <T> the type of the dose and rate component.
     * @param <U> the type of the extracted field.
     * @return the extracted field from the first dose and rate component.
     * @since 2.1.3
     */
    public static <T extends IBase, U extends IBase> U selectDosageAndRateField(
        List<T> doseAndRateComponentList,
        DoseAndRateKey doseAndRateKey,
        Map<DoseAndRateKey, DoseAndRateExtractor<T, U>> extractorMap
    ) {
        var doseAndRateExtractor = extractorMap.get(doseAndRateKey);
        var firstRep = doseAndRateComponentList.get(0);
        return doseAndRateExtractor.extract(firstRep);
    }

}
