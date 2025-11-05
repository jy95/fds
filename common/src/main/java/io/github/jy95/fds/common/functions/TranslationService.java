package io.github.jy95.fds.common.functions;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;
import lombok.Builder;
import lombok.Getter;

/**
 * A class that provides localized translation messages for a given configuration and resource bundle.
 *
 * @param <C> the type of FDSConfig
 */
@Builder
@Getter
public final class TranslationService<C extends FDSConfig> {
    
    private final ResourceBundle bundle;
    private final C config;
    /**
     * Constructs a new {@code TranslationMessagesMap} with the specified supplier map.
     *
     * @param messageSuppliers a map of message keys to lazy {@link java.util.function.Supplier} instances
     */
    @Builder.Default private final Map<String, MessageFormat> messagesCache = new ConcurrentHashMap<>();

    /**
     * Retrieves the localized message format for the specified key.
     *
     * @param key the message key
     * @return the localized {@link com.ibm.icu.text.MessageFormat} instance
     */
    public MessageFormat getMessage(String key) {
        return messagesCache.computeIfAbsent(key, k -> {
            var msg = bundle.getString(k);
            return new MessageFormat(msg, config.getLocale());
        });
    }

}