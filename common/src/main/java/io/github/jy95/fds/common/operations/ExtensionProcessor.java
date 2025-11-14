package io.github.jy95.fds.common.operations;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.hl7.fhir.instance.model.api.IBaseExtension;

/**
 * Interface for FHIR Extension-related conversion operations.
 *
 * @param <E> The type representing Extension in the FHIR version.
 * @since 2.1.4
 */
public interface ExtensionProcessor<E extends IBaseExtension<?, ?>> {
    /**
     * Converts a list of FHIR Extension objects to a JSON-like string representation.
     *
     * @param extensions the list of Extension objects to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to a JSON-like string representing the extensions.
     */
    CompletableFuture<String> fromExtensionsToString(List<E> extensions);
}
