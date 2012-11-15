package org.gparsexperiment.util;

import junit.framework.TestCase;

public class ARGSUtilsTest extends TestCase {

    public void testShouldExtractCorrectNumberOfThreads() throws Exception {
        String[] args = {"path", "pattern", "replace", "4"};
        assertEquals(4, ARGSUtils.getThreads(args, "0"));
    }

    public void testShouldReturnDefaultNumberOfThreads() throws Exception {
        String[] args = {"path", "pattern", "replace"};
        assertEquals(4, ARGSUtils.getThreads(args, "4"));
    }

    public void testShouldFailParsingNumberOfThreads() throws Exception {
        String[] args = {"path", "pattern", "replace", "x"};
        try {
            assertEquals(4, ARGSUtils.getThreads(args, "0"));
            fail("Should fail!");
        } catch (Exception e) {
            assertTrue("Expected", true);
        }
    }

    public void testShouldExtractCorrectExtension() throws Exception {
        String[] args = {"path", "pattern", "replace", "4", "back"};
        assertEquals("back", ARGSUtils.getExtension(args, "na"));
    }

    public void testShouldReturnDefaultExtension() throws Exception {
        String[] args = {"path", "pattern", "replace", "4"};
        assertEquals("back", ARGSUtils.getExtension(args, "back"));
    }
}
