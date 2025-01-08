package jy95.fhir.common.config;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import jy95.fhir.common.bundle.MultiResourceBundleControl;

public class DefaultImplementations {
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
