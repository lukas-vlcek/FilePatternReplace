package org.gparsexperiment.util

import java.util.regex.Pattern

/**
 * TBD
 */
class RegexUtils {

    /**
     *
     * @param content
     * @param pattern
     * @param value
     * @return
     */
    static public String replace(String content, Pattern pattern, String value) {

        (content =~ pattern).replaceAll(value)

    }

}
