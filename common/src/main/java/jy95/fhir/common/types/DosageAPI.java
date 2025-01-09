package jy95.fhir.common.types;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.functions.ListToString;
import lombok.Getter;

@Getter
public abstract class DosageAPI<C extends FDSConfig, D> {
    private final C config;
    private final ResourceBundle resources;

    public DosageAPI(C config) {
        this.config = config;
        this.resources = config.getSelectResourceBundle().apply(config.getLocale());
    }

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

    public abstract AbstractTranslator<C, D> getTranslator(DisplayOrder displayOrder);

    public abstract boolean containsOnlySequentialInstructions(List<D> dosages);

    public CompletableFuture<String> asHumanReadableText(D dosage) {
        var fields = this.config.getDisplayOrder().toArray(DisplayOrder[]::new);
        return getFields(dosage, fields);
    }

    public CompletableFuture<String> asHumanReadableText(List<D> dosages) {
        if (containsOnlySequentialInstructions(dosages)) {
            return convertSequentialDosagesToText(dosages);
        }
        return convertGroupedDosagesToText(dosages);
    }

    protected CompletableFuture<String> convertSequentialDosagesToText(List<D> dosages) {
        List<CompletableFuture<String>> dosagesAsTextFutures = dosages.stream()
                .map(this::asHumanReadableText)
                .toList();

        return CompletableFuture
                .allOf(dosagesAsTextFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var dosagesAsText = extractCompletedFutures(dosagesAsTextFutures);
                    return convertToText(dosagesAsText, ListToString.LinkWord.THEN);
                });
    }

    protected CompletableFuture<String> convertGroupedDosagesToText(List<D> dosages) {
        var sortedDosages = groupBySequence(dosages);

        var sequentialInstructionsFutures = sortedDosages.stream()
                .map(this::convertConcurrentDosagesToText)
                .toList();

        return CompletableFuture
                .allOf(sequentialInstructionsFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var dosagesAsText = extractCompletedFutures(sequentialInstructionsFutures);
                    return convertToText(dosagesAsText, ListToString.LinkWord.THEN);
                });
    }

    protected CompletableFuture<String> convertConcurrentDosagesToText(List<D> dosages) {
        List<CompletableFuture<String>> concurrentInstructionsFutures = dosages.stream()
                .map(this::asHumanReadableText)
                .toList();

        return CompletableFuture
                .allOf(concurrentInstructionsFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var dosagesAsText = extractCompletedFutures(concurrentInstructionsFutures);
                    return convertToText(dosagesAsText, ListToString.LinkWord.AND); // Defaults to LinkWord.AND
                });
    }

    protected abstract List<List<D>> groupBySequence(List<D> dosages);

    // Helper method to extract completed futures
    private List<String> extractCompletedFutures(List<CompletableFuture<String>> futures) {
        return futures.stream()
                .map(future -> future.getNow("")) // Extracting results with default fallback
                .toList();
    }

    // Helper method to use ListToString.convert with  LinkWord
    private String convertToText(List<String> textList, ListToString.LinkWord linkWord) {
        var bundle = this.getResources();
        return ListToString.convert(bundle, textList, linkWord);
    }
}