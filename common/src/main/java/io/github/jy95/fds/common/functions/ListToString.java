package io.github.jy95.fds.common.functions;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Utility class for converting a list of strings into a human-readable string.
 */
public final class ListToString {

    /**
     * Enum representing the link words used to combine list elements.
     */
    public enum LinkWord {
        /**
         * Represents the "and" link word.
         */
        AND("and"),
        /**
         * Represents the "then" link word.
         */
        THEN("then");

        private final String value;

        LinkWord(String value) {
            this.value = value;
        }

        /**
         * Retrieves the value of the link word.
         *
         * @return The string value of the link word.
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * Converts a list of strings into a human-readable string using a specified link word.
     * Example: ["A", "B", "C"] with the link word "and" results in "A, B and C".
     *
     * @param bundle   The resource bundle for localization.
     * @param list     The list of strings to convert.
     * @param linkWord The link word to use for combining the elements.
     * @return A human-readable string representation of the list.
     */
    public static String convert(ResourceBundle bundle, List<String> list, LinkWord linkWord) {

        if (list.isEmpty()){
            return "";
        }

        // Build the first part of the string (all except the last item)
        String firstString = String.join(", ", list.subList(0, list.size() - 1));
        // Get the last part of the string (the last item)
        String lastString = list.getLast();

        // Get the translated link word via delegate
        String linkWordTranslation = getLinkWordTranslation(bundle, linkWord);
        String linkWordString = list.size() > 1 ? " " + linkWordTranslation + " " : "";

        // Combine everything into the final string
        return firstString + linkWordString + lastString;
    }

    /**
     * Converts a list of strings into a human-readable string using the default link word "and".
     * Example: ["A", "B", "C"] results in "A, B and C".
     *
     * @param bundle The resource bundle for localization.
     * @param list   The list of strings to convert.
     * @return A human-readable string representation of the list.
     */
    public static String convert(ResourceBundle bundle, List<String> list) {
        return convert(bundle,list,LinkWord.AND);
    }

    /**
     * Retrieves the localized translation of the specified link word.
     *
     * @param bundle   The resource bundle for localization.
     * @param linkWord The link word to translate.
     * @return The translated link word as a string.
     */
    private static String getLinkWordTranslation(ResourceBundle bundle, LinkWord linkWord) {
        // Delegate method for link word translation
        return bundle.getString("linkwords." + linkWord.getValue());
    }

}
