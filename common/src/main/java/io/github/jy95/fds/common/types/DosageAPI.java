package io.github.jy95.fds.common.types;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Abstract class providing API methods for translating and formatting dosage data.
 *
 * @param <C> the type of configuration extending {@link io.github.jy95.fds.common.config.FDSConfig}
 * @param <D> the type of dosage handled by this API
 * @author jy95
 * @since 1.0.0
 */
@Getter
public abstract class DosageAPI<C extends FDSConfig, D> {
    /**
     * The configuration object used by this API.
     */
    private final C config;
    
    /**
     * The translation service used for localization.
     */
    protected final TranslationService<C> translationService;

    /**
     * Constructs a new {@code DosageAPI} with the specified configuration.
     *
     * @param config the configuration object providing locale and resource bundle
     */
    public DosageAPI(C config) {
        this.config = config;
        var bundle = config.getSelectResourceBundle().apply(config.getLocale());
        this.translationService = TranslationService.<C>builder()
                .config(config)
                .bundle(bundle)
                .build();
    }

    /**
     * Converts specified dosage fields into a human-readable string representation asynchronously.
     *
     * @param dosage the dosage object to translate
     * @param fields the fields to include in the translation
     * @return a {@link java.util.concurrent.CompletableFuture} with the combined human-readable string
     */
    public CompletableFuture<String> getFields(D dosage, DisplayOrder... fields) {
        var separator = this.config.getDisplaySeparator();

        // Create a list of CompletableFutures for each field translation
        var translatorsFields = Arrays
                .stream(fields)
                .map(this::getTranslator)
                .filter(Objects::nonNull)
                .filter(translator -> translator.isPresent(dosage))
                .map(translator -> translator.convert(dosage))
                .toList();

        // Combine the results of all CompletableFutures into a single result
        return CompletableFuture
                .allOf(translatorsFields.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> translatorsFields
                        .stream()
                        .map(s -> s.getNow(""))
                        .collect(Collectors.joining(separator))
                );
    }

    /**
     * Retrieves the translator associated with the specified display order.
     *
     * @param displayOrder the display order used to find the translator
     * @return the corresponding {@link io.github.jy95.fds.common.types.Translator}, or {@code null} if not found
     */
    public abstract Translator<D> getTranslator(DisplayOrder displayOrder);

    /**
     * Checks if the given list of dosages contains only sequential instructions.
     *
     * @param dosages the list of dosages to check
     * @return {@code true} if all dosages are sequential, {@code false} otherwise
     */
    public abstract boolean containsOnlySequentialInstructions(List<D> dosages);

    /**
     * Converts a single dosage object into human-readable text asynchronously.
     *
     * @param dosage the dosage object to translate
     * @return a {@link java.util.concurrent.CompletableFuture} with the human-readable string
     */
    public CompletableFuture<String> asHumanReadableText(D dosage) {
        var fields = this.config.getDisplayOrder().toArray(DisplayOrder[]::new);
        return getFields(dosage, fields);
    }

    /**
     * Converts a list of dosage objects into human-readable text asynchronously.
     * Handles both sequential and grouped dosages appropriately.
     *
     * @param dosages the list of dosage objects to translate
     * @return a {@link java.util.concurrent.CompletableFuture} with the combined human-readable string
     */
    public CompletableFuture<String> asHumanReadableText(List<D> dosages) {
        if (containsOnlySequentialInstructions(dosages)) {
            return convertSequentialDosagesToText(dosages);
        }
        return convertGroupedDosagesToText(dosages);
    }

    /**
     * Converts sequential dosages into a human-readable text asynchronously.
     *
     * @param dosages the list of sequential dosages
     * @return a {@link java.util.concurrent.CompletableFuture} with the human-readable string
     */
    protected CompletableFuture<String> convertSequentialDosagesToText(List<D> dosages) {
        List<CompletableFuture<String>> dosagesAsTextFutures = dosages.stream()
                .map(this::asHumanReadableText)
                .toList();

        return CompletableFuture
                .allOf(dosagesAsTextFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var dosagesAsText = extractCompletedFutures(dosagesAsTextFutures);
                    return convertToText(dosagesAsText, LinkWord.THEN);
                });
    }

    /**
     * Converts grouped dosages into a human-readable text asynchronously.
     *
     * @param dosages the list of grouped dosages
     * @return a {@link java.util.concurrent.CompletableFuture} with the human-readable string
     */
    protected CompletableFuture<String> convertGroupedDosagesToText(List<D> dosages) {
        var sortedDosages = groupBySequence(dosages);

        var sequentialInstructionsFutures = sortedDosages.stream()
                .map(this::convertConcurrentDosagesToText)
                .toList();

        return CompletableFuture
                .allOf(sequentialInstructionsFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var dosagesAsText = extractCompletedFutures(sequentialInstructionsFutures);
                    return convertToText(dosagesAsText, LinkWord.THEN);
                });
    }

    /**
     * Converts concurrent dosages into a human-readable text asynchronously.
     *
     * @param dosages the list of concurrent dosages
     * @return a {@link java.util.concurrent.CompletableFuture} with the human-readable string
     */
    protected CompletableFuture<String> convertConcurrentDosagesToText(List<D> dosages) {
        List<CompletableFuture<String>> concurrentInstructionsFutures = dosages.stream()
                .map(this::asHumanReadableText)
                .toList();

        return CompletableFuture
                .allOf(concurrentInstructionsFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var dosagesAsText = extractCompletedFutures(concurrentInstructionsFutures);
                    return convertToText(dosagesAsText, LinkWord.AND); // Defaults to LinkWord.AND
                });
    }

    /**
     * Groups the given list of dosages by sequence for processing.
     *
     * @param dosages the list of dosages to group
     * @return a list of grouped dosages
     */
    protected abstract List<List<D>> groupBySequence(List<D> dosages);

    /**
     * Extracts completed results from a list of {@link CompletableFuture} instances.
     * @param futures the list of futures to extract results from
     * @return a list of completed results
     */
    private List<String> extractCompletedFutures(List<CompletableFuture<String>> futures) {
        return futures.stream()
                .map(future -> future.getNow("")) // Extracting results with default fallback
                .toList();
    }

    /**
     * Converts a list of strings into a single formatted string using a link word.
     * @param textList the list of strings to combine
     * @param linkWord the linking word to use between strings
     * @return the combined string
     */
    private String convertToText(List<String> textList, LinkWord linkWord) {
        return ListToString.convert(translationService, textList, linkWord);
    }
}
