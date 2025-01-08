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
        return convertConcurrentDosagesToText(dosages);
    }

    protected CompletableFuture<String> convertSequentialDosagesToText(List<D> dosages) {
        // Process each dosage into text asynchronously
        List<CompletableFuture<String>> dosagesAsTextFutures = dosages.stream()
                .map(this::asHumanReadableText)
                .toList();

        return CompletableFuture
                .allOf(dosagesAsTextFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    // Extract results from completed futures
                    List<String> dosagesAsText = dosagesAsTextFutures
                            .stream()
                            .map(future -> future.getNow(""))
                            .toList();

                    // Use ListToString.convert with the translators' resources
                    var bundle = this.getResources();
                    return ListToString.convert(bundle, dosagesAsText, ListToString.LinkWord.THEN);
                });
    }

    protected abstract CompletableFuture<String> convertConcurrentDosagesToText(List<D> dosages);

    protected CompletableFuture<String> convertGroupedDosagesToText(List<List<D>> dosages) {
        var concurrentInstructionsFutures = dosages
                .stream()
                .map(this::asHumanReadableText)
                .toList();

        return CompletableFuture
                .allOf(concurrentInstructionsFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    // Extract results from completed futures
                    List<String> dosagesAsText = concurrentInstructionsFutures
                            .stream()
                            .map(s -> s.getNow(""))
                            .toList();

                    // Use ListToString.convert with the translators' resources
                    var bundle = this.getResources();
                    return ListToString.convert(bundle, dosagesAsText);
                });
    }
}
