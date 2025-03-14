package io.github.jy95.fds.common.bundle;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A ResourceBundle implementation that aggregates multiple ResourceBundles.
 * This class allows retrieving values from a list of ResourceBundles,
 * checking each bundle in the order they are provided.
 *
 * @author jy95
 */
public class MultiResourceBundle extends ResourceBundle {

    /**
     * The list of delegate ResourceBundles used to retrieve resources.
     */
    private final List<ResourceBundle> delegates;

    /**
     * Constructs a MultiResourceBundle with the specified list of ResourceBundles.
     *
     * @param resourceBundles the list of ResourceBundles to aggregate.
     *                        If {@code null}, an empty list will be used.
     */
    public MultiResourceBundle(List<ResourceBundle> resourceBundles) {
        this.delegates = resourceBundles == null ? new ArrayList<>() : resourceBundles;
    }

    /** {@inheritDoc} */
    @Override
    protected Object handleGetObject(String key) {
        Optional<Object> firstPropertyValue = this.delegates.stream()
                .filter(delegate -> delegate.containsKey(key))
                .map(delegate -> delegate.getObject(key))
                .findFirst();

        return firstPropertyValue.orElse(null);
    }

    /** {@inheritDoc} */
    @Override
    public Enumeration<String> getKeys() {

        List<String> keys = this.delegates.stream()
                .filter(Objects::nonNull)
                .flatMap(delegate -> Collections.list(delegate.getKeys()).stream())
                .collect(Collectors.toList());

        return Collections.enumeration(keys);
    }
}
