package org.gparsexperiment.util

class SortUtilsTest extends GroovyTestCase {

    File[] files;

    void setUp() {
        String dir = "src/test/resources/files"
        files = IOUtils.collectFiles(dir, 3)
    }

    void testMergeSort() {

        files = SortUtils.mergeSort(files, 3)

        assertEquals("We have 12 testing files", 12, files.length)

        //files.each { System.out.println (it.length() + " > " + it) }

    }
}
