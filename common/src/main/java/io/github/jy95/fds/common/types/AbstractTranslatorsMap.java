package io.github.jy95.fds.common.types;

import io.github.jy95.fds.common.config.FDSConfig;
import java.util.Map;

/**
 * Abstract class representing a map of translators for various {@link DisplayOrder} values.
 * This class provides a mechanism to retrieve an {@link AbstractTranslator} based on the specified
 * display order.
 *
 * @param <C> the type of configuration extending {@link FDSConfig}
 * @param <D> the type of dosage field handled by the translators
 */
public abstract class AbstractTranslatorsMap<C extends FDSConfig, D> {

    /**
     * A map associating {@link DisplayOrder} values with their corresponding
     * {@link AbstractTranslator} instances.
     */
    private final Map<DisplayOrder, AbstractTranslator<C, D>> translatorMap;

    /**
     * Constructs a new {@code AbstractTranslatorsMap} with the specified translator map.
     * @param translatorMap a map of {@link DisplayOrder} to {@link AbstractTranslator} instances
     */
    public AbstractTranslatorsMap(Map<DisplayOrder, AbstractTranslator<C, D>> translatorMap) {
        this.translatorMap = translatorMap;
    }

    /**
     * Retrieves the {@link AbstractTranslator} associated with the specified {@link DisplayOrder}.
     * @param displayOrder the {@link DisplayOrder} used to locate the translator
     * @return the corresponding {@link AbstractTranslator}, or {@code null} if no match is found
     */
    public AbstractTranslator<C, D> getTranslator(DisplayOrder displayOrder) {
        return translatorMap.get(displayOrder);
    }
}
