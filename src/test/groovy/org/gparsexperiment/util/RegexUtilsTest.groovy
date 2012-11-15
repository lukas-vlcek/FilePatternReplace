package org.gparsexperiment.util

class RegexUtilsTest extends GroovyTestCase {

    void testSimpleReplace() {
        assertEquals("Using ~// notation", "Hi there!", RegexUtils.replace("Hello there!", ~/Hello/, "Hi"))
        assertEquals("Using ~'' notation", "Hi there!", RegexUtils.replace("Hello there!", ~'Hello', "Hi"))
        def pattern = 'Hello'
        assertEquals("Using ~object notation", "Hi there!", RegexUtils.replace("Hello there!", ~pattern, "Hi"))
    }

    void testShouldReplaceAll() {
        def originalContent = "The quick brown fox jumps over the lazy dog"
        def replacedContent = "The dummy brown dummy jumps over the lazy dummy"
        assertEquals("Should replace all", replacedContent, RegexUtils.replace(originalContent, ~"quick|fox|dog", "dummy"))
    }
}
