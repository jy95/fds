package jy95.fhir.common.functions;

import java.util.List;
import java.util.ResourceBundle;

public final class ListToString {

    public enum LinkWord {
        AND("and"), 
        THEN("then");

        private final String value;

        LinkWord(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

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

    public static String convert(ResourceBundle bundle, List<String> list) {
        return convert(bundle,list,LinkWord.AND);
    }

    private static String getLinkWordTranslation(ResourceBundle bundle, LinkWord linkWord) {
        // Delegate method for link word translation
        return bundle.getString("linkwords." + linkWord.getValue());
    }

}
