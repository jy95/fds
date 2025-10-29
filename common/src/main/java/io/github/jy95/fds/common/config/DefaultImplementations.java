package io.github.jy95.fds.common.config;

import io.github.jy95.fds.common.bundle.MultiResourceBundleControl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.ibm.icu.number.NumberFormatter;

import org.hl7.fhir.instance.model.api.IBaseExtension;
import org.hl7.fhir.instance.model.api.IPrimitiveType;

/**
 * Provides default implementations for common operations in the library.
 *
 * @author jy95
 * @since 1.0.0
 */
public final class DefaultImplementations {

    // Define the new package path for resource bundles
    private static final String RESOURCE_PACKAGE = "io.github.jy95.fds.common.l10n.";

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
        var bundleControl = new MultiResourceBundleControl(
                "translations",
                RESOURCE_PACKAGE + "DosageFields",
                RESOURCE_PACKAGE + "EventTiming",
                RESOURCE_PACKAGE + "QuantityComparator"
        );
        return ResourceBundle.getBundle(
                bundleControl.getBaseName(),
                locale,
                bundleControl
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
    public static CompletableFuture<String> fromExtensionsToString(List<? extends IBaseExtension<?, ?>> extensions) {
        return CompletableFuture.supplyAsync(() -> {

            if (extensions.isEmpty()) {
                return null;
            }

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

                        if (hasValue) {
                            
                            var valuIPrimitiveType = (IPrimitiveType<?>) ext.getValue();

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

}
