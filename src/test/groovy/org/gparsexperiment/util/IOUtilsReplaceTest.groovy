package org.gparsexperiment.util

class IOUtilsReplaceTest extends GroovyTestCase {

    String dir = "src/test/resources/modified_files"

    private void deleteModifiedFiles() {
        new File(dir).eachFileMatch(~/.*\.mod/) { it.delete() }
    }

    void setUp() {
        deleteModifiedFiles()
    }

    void tearDown() {
        deleteModifiedFiles()
    }

    void testShouldReplacePatternsInFiles() throws Exception {

        File[] files = IOUtils.collectFiles(dir, 3);
        assertEquals("We have 3 testing files", 3, files.length)

        IOUtils.replace(files, 3, "x", "x", "mod");
        files = IOUtils.collectFiles(dir, 3);
        assertEquals("Now we have 6 testing files", 6, files.length)
    }

}
