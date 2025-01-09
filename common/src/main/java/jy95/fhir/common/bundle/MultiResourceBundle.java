package jy95.fhir.common.bundle;

import java.util.*;

// Credits to https://coiaf.de/java-multiple-resource-bundles/

public class MultiResourceBundle extends ResourceBundle {

    private final List<ResourceBundle> delegates;

    public MultiResourceBundle(List<ResourceBundle> resourceBundles) {
        this.delegates = resourceBundles == null ? new ArrayList<>() : resourceBundles;
    }

    @Override
    protected Object handleGetObject(String key) {
        Optional<Object> firstPropertyValue = this.delegates.stream()
                .filter(delegate -> delegate.containsKey(key))
                .map(delegate -> delegate.getObject(key))
                .findFirst();

        return firstPropertyValue.orElse(null);
    }

    @Override
    public Enumeration<String> getKeys() {

        List<String> keys = this.delegates.stream()
                .filter(Objects::nonNull)
                .flatMap(delegate -> Collections.list(delegate.getKeys()).stream())
                .toList();

        return Collections.enumeration(keys);
    }
}