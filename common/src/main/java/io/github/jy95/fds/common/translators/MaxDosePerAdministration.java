package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.types.Translator;

/**
 * Interface for translating "maxDosePerAdministration".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface MaxDosePerAdministration<D> extends Translator<D> {

    /**
     * Key constant for maxDosePerAdministration message
     */
    String KEY_MAX_DOSE_PER_ADMINISTRATION = "fields.maxDosePerAdministration";

}
