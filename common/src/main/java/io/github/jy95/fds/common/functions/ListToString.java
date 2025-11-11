package io.github.jy95.fds.common.functions;

import io.github.jy95.fds.common.types.LinkWord;

import java.util.List;

/**
 * Utility class for converting a list of strings into a human-readable string.
 *
 * @author jy95
 * @since 1.0.0
 */
public final class ListToString {

    /**
     * No constructor for this class
     */
    private ListToString(){}

    /**
     * Converts a list of strings into a human-readable string using a specified link word.
     * Example: ["A", "B", "C"] with the link word "and" results in "A, B and C".
     *
     * @param translationService The service providing localized string translations.
     * @param list               The list of strings to convert.
     * @param linkWord           The link word to use for combining the elements.
     * @return A human-readable string representation of the list.
     */
    public static String convert(TranslationService<?> translationService, List<String> list, LinkWord linkWord) {

        if (list.isEmpty()){
            return "";
        }

        // Handle single item list (avoids subList failure)
        if (list.size() == 1) {
            return list.get(0);
        }

        // Build the first part of the string (all except the last item)
        // Note: list.subList(0, list.size() - 1) is safe for size >= 2
        String firstString = String.join(", ", list.subList(0, list.size() - 1));
        // Get the last part of the string (the last item)
        String lastString = list.get(list.size() - 1);

        // Get the translated link word via delegate
        String linkWordTranslation = getLinkWordTranslation(translationService, linkWord);
        // The link word string is only needed if there is more than one item, 
        // but since we handled size 1 case, list.size() > 1 is true here.
        String linkWordString = " " + linkWordTranslation + " ";

        // Combine everything into the final string
        // Note: With the size 1 check, the ternary operator 'list.size() > 1 ? ...' is no longer needed
        return firstString + linkWordString + lastString;
    }

    /**
     * Converts a list of strings into a human-readable string using the default link word "and".
     * Example: ["A", "B", "C"] results in "A, B and C".
     *
     * @param translationService The service providing localized string translations.
     * @param list               The list of strings to convert.
     * @return A human-readable string representation of the list.
     */
    public static String convert(TranslationService<?> translationService, List<String> list) {
        return convert(translationService, list, LinkWord.AND);
    }

    /**
     * Retrieves the localized translation of the specified link word using the provided translation service.
     *
     * @param translationService The service providing localized string translations.
     * @param linkWord           The link word to translate.
     * @return The translated link word as a string.
     */
    private static String getLinkWordTranslation(TranslationService<?> translationService, LinkWord linkWord) {
        // We assume the TranslationService has access to the ResourceBundle keys 
        // needed for link words, as in the original implementation.
        String key = "linkwords." + linkWord.getValue();
        return translationService.getText(key);
    }

}