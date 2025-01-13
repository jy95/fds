package io.github.jy95.fds.common.config;

import java.util.Locale;
import java.util.ResourceBundle;

import io.github.jy95.fds.common.bundle.MultiResourceBundleControl;

/**
 * Provides default implementations for common operations in the library.
 *
 * @author jy95
 */
public final class DefaultImplementations {

    /**
     * Selects a ResourceBundle for the specified locale using a MultiResourceBundleControl.
     *
     * @param locale the locale for which the ResourceBundle is desired.
     * @return the aggregated ResourceBundle for the specified locale.
     */
    public static ResourceBundle selectResourceBundle(Locale locale) {
        var bundleControl = new MultiResourceBundleControl(
                "translations",
                "common",
                "daysOfWeek",
                "eventTiming",
                "quantityComparator",
                "unitsOfTime"
        );
        return ResourceBundle.getBundle(
                bundleControl.getBaseName(),
                locale,
                bundleControl
        );
    }
}
