package jy95.fhir.r4.dosage.utils.miscellaneous;

// Credits to https://coiaf.de/java-multiple-resource-bundles/

import lombok.Getter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Getter
public class MultiResourceBundleControl extends ResourceBundle.Control {

    private final String baseName;
    private final String[] dependentBaseNames;

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
