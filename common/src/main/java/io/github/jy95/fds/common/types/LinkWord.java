package io.github.jy95.fds.common.types;

import lombok.Getter;

/**
 * Enum representing the link words used to combine list elements.
 *
 * @author jy95
 * @since 1.0.0
 */
@Getter
public enum LinkWord {
    /**
     * Represents the "and" link word.
     */
    AND("and"),
    /**
     * Represents the "then" link word.
     */
    THEN("then");

    /**
     * Retrieves the value of the link word.
     */
    private final String value;

    LinkWord(String value) {
        this.value = value;
    }

}
