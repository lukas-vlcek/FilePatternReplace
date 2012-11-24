package org.gparsexperiment.util

import java.util.regex.Pattern

/**
 * Simple utility for regex replacing.
 */
class RegexUtils {

    /**
     * Replaces all occurrences of pattern with value in given content.
     *
     * @param content
     * @param pattern
     * @param value
     *
     * @return content
     */
    static public String replace(String content, Pattern pattern, String value) {

        (content =~ pattern).replaceAll(value)

    }

}
