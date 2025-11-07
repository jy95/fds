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
     * Constructs a new {@code TranslationService} with the specified supplier map.
     *
     * @param messagesCache a map of message keys to lazy String instances
     */
    @Builder.Default private final Map<String, String> messagesCache = new ConcurrentHashMap<>();

    /**
     * Retrieves the localized message format for the specified key.
     *
     * @param key the message key
     * @return a new, localized {@link com.ibm.icu.text.MessageFormat} instance
     */
    public MessageFormat getMessage(String key) {
        // 1. Cache only the raw format String, which IS thread-safe to share.
        var msg = getText(key);
        
        // 2. Return a brand-new MessageFormat instance every time.
        // This ensures each thread has its own dedicated, non-shared instance.
        return new MessageFormat(msg, config.getLocale());
    }

    /**
     * Retrieves the localized message for the specified key without arguments.
     *
     * @param key the message key
     * @return the localized message string
     */
    public String getText(String key) {
        return messagesCache.computeIfAbsent(key, k -> bundle.getString(k));
    }

}