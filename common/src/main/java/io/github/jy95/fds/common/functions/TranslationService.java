package io.github.jy95.fds.common.functions;

import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;
import lombok.Builder;
import lombok.Getter;
import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import org.hl7.fhir.utilities.DateTimeUtil;

/**
 * A class that provides localized translation messages for a given configuration and resource bundle.
 *
 * @param <C> the type of FDSConfig
 * @author jy95
 * @since 2.1.1
 */
@Builder
public final class TranslationService<C extends FDSConfig> {
    
    /**
     * The resource bundle containing localized strings for translation.
     */
    @Getter
    private final ResourceBundle bundle;

    /**
     * The configuration object containing locale and other settings.
     */
    @Getter
    private final C config;

    /**
     * A thread-safe cache for storing localized message strings.
     */
    @Builder.Default
    private final Map<String, String> messagesCache = new ConcurrentHashMap<>();

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

    /**
     * Converts a Date object to a human-readable string based on the configuration's locale.
     *
     * @param date      the Date object to convert
     * @param timeZone  the TimeZone of the date
     * @param precision the TemporalPrecisionEnum indicating the precision of the date
     * @return a human-readable string representation of the date
     */
    public String dateTimeToHumanDisplay(
        Date date,
        TimeZone timeZone,
        TemporalPrecisionEnum precision
    ) {
        var locale = config.getLocale();
        return DateTimeUtil.toHumanDisplay(
                locale,
                timeZone,
                precision,
                date
        );
    }

}