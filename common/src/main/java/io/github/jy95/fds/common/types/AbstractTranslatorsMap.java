package io.github.jy95.fds.common.types;

import io.github.jy95.fds.common.config.FDSConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Abstract class representing a map of translators for various {@link io.github.jy95.fds.common.types.DisplayOrder} values.
 * This class provides a mechanism to retrieve an {@link io.github.jy95.fds.common.types.Translator} based on the specified
 * display order.
 *
 * @param <C> the type of configuration extending {@link io.github.jy95.fds.common.config.FDSConfig}
 * @param <D> the type of dosage field handled by the translators
 * @author jy95
 * @since 1.0.0
 */
public abstract class AbstractTranslatorsMap<C extends FDSConfig, D> {

    /**
     * A map associating {@link DisplayOrder} values with their corresponding
     * lazy {@link Supplier} instances for {@link Translator}.
     */
    private final Map<DisplayOrder, Supplier<Translator<C, D>>> translatorSuppliers;

    /**
     * A map associating {@link DisplayOrder} values with their corresponding {@link Translator} instance.
     * So if you use part of the supported fields, you won't have a bunch of useless new calls to for your needs
     */
    private final Map<DisplayOrder, Translator<C, D>> translatorCache = new ConcurrentHashMap<>();

    /**
     * Constructs a new {@code AbstractTranslatorsMap} with the specified supplier map.
     *
     * @param translatorSuppliers a map of {@link io.github.jy95.fds.common.types.DisplayOrder} to lazy {@link java.util.function.Supplier} instances
     */
    public AbstractTranslatorsMap(Map<DisplayOrder, Supplier<Translator<C, D>>> translatorSuppliers) {
        this.translatorSuppliers = translatorSuppliers;
    }

    /**
     * Retrieves the {@link io.github.jy95.fds.common.types.Translator} associated with the specified {@link io.github.jy95.fds.common.types.DisplayOrder}.
     * The translator is created lazily on first access.
     *
     * @param displayOrder the {@link io.github.jy95.fds.common.types.DisplayOrder} used to locate the translator
     * @return the corresponding {@link io.github.jy95.fds.common.types.Translator}, or {@code null} if no match is found
     */
    public Translator<C, D> getTranslator(DisplayOrder displayOrder) {
        return translatorCache.computeIfAbsent(displayOrder, key -> {
            Supplier<Translator<C, D>> supplier = translatorSuppliers.get(key);
            return supplier != null ? supplier.get() : null;
        });
    }
}
