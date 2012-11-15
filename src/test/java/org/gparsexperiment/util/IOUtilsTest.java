package org.gparsexperiment.util;

import junit.framework.TestCase;

import java.io.File;

public class IOUtilsTest extends TestCase {

    public void testShouldCollectFilesFromFolder() throws Exception {

        String dir = "src/test/resources/files";
        File[] files = IOUtils.collectFiles(dir, 3);

        assertEquals("We have 12 testing files", 12, files.length);

    }

    public void testCollectFilesShouldFailOnFile() throws Exception {

        String dir = "src/test/resources/files/file01";
        try {
            IOUtils.collectFiles(dir, 3);
            fail("An exception is expected");
        } catch (Exception e) {
            assertTrue("Expected", true);
        }

    }

    public void testCollectFilesShouldFailOnMissingResource() throws Exception {

        String dir = "src/test/resources/missing_folder";
        try {
            IOUtils.collectFiles(dir, 3);
            fail("An exception is expected");
        } catch (Exception e) {
            assertTrue("Expected", true);
        }

    }
}
