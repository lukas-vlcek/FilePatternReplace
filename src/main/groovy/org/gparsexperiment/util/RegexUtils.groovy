package org.gparsexperiment.util

import java.util.regex.Pattern

/**
 * Simple utility for regex.
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

    /**
     * Test if pattern can be found in the content.
     *
     * @param content
     * @param pattern
     *
     * @return true if pattern can be found in the content
     */
    static public boolean find(String content, Pattern pattern) {
        return pattern.matcher(content).find()
    }

}
