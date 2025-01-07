package jy95.fhir.common.types;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

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

    public abstract CompletableFuture<String> getFields(D dosage, DisplayOrder... fields);

    public CompletableFuture<String> asHumanReadableText(D dosage) {
        var fields = this.config.getDisplayOrder().toArray(DisplayOrder[]::new);
        return getFields(dosage, fields);
    }

    public abstract CompletableFuture<String> asHumanReadableText(List<D> dosages);

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

    protected CompletableFuture<String> convertConcurrentDosagesToText(List<D> dosages) {
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
