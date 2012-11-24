package org.gparsexperiment

class CommandTest extends GroovyTestCase {

    String dir = "src/test/resources/modified_files"

    private void deleteModifiedFiles() {
        new File(dir).eachFileMatch(~/.*\.test/) { it.delete() }
    }

    void setUp() {
        deleteModifiedFiles()
    }

    void tearDown() {
        deleteModifiedFiles()
    }

    /**
     * Replaces all occurrences of string '2' with 'X' in files found under ${dir} folder recursively.
     * Modified files will be stored using '.test' extension.
     */
    void testMain() {

        def args = [ dir, "2", "X", 1, "test" ] as String[]

        Command.main(args)

        def modifiedFiles = [];
        new File(dir).eachFileMatch(~/.*\.test/) { modifiedFiles.add(it) }

        // only two files were modified
        assertEquals(2, modifiedFiles.size())
    }
}
