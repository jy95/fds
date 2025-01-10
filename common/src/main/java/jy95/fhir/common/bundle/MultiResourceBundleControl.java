package jy95.fhir.common.bundle;

import lombok.Getter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * A custom ResourceBundle.Control implementation that aggregates multiple ResourceBundles.
 * This class facilitates the creation of a MultiResourceBundle by combining dependent ResourceBundles.
 */
@Getter
public class MultiResourceBundleControl extends ResourceBundle.Control {

    /**
     * The base name of the primary ResourceBundle.
     */
    private final String baseName;
    /**
     * The base names of dependent ResourceBundles to be aggregated.
     */
    private final String[] dependentBaseNames;

    /**
     * Constructs a MultiResourceBundleControl with the specified base name and dependent base names.
     *
     * @param baseName          the base name of the primary ResourceBundle.
     * @param dependentBaseNames the base names of additional ResourceBundles to aggregate.
     */
    public MultiResourceBundleControl(String baseName, String... dependentBaseNames) {
        this.baseName = baseName;
        this.dependentBaseNames = dependentBaseNames;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        List<ResourceBundle> delegates = Arrays.stream(this.dependentBaseNames)
                .filter(currentBaseName -> currentBaseName != null && !currentBaseName.trim().isEmpty())
                .map(currentBaseName -> ResourceBundle.getBundle(currentBaseName, locale))
                .collect(Collectors.toList());

        return new MultiResourceBundle(delegates);
    }
}
