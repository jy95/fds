package io.github.jy95.fds.common.types;

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
