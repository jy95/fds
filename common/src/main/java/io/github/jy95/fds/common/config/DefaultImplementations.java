package io.github.jy95.fds.common.config;

import io.github.jy95.fds.common.bundle.MultiResourceBundleControl;

import java.util.Locale;
import java.util.ResourceBundle;

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
}
