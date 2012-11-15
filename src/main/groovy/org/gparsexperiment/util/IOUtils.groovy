package org.gparsexperiment.util

import static groovyx.gpars.GParsPool.withPool
import static groovyx.gpars.GParsPool.runForkJoin

/**
 * TBD
 */
class IOUtils {

    /**
     * Returns list of all files found in given folder (including files from sub-folders).
     * Internally, it is using Fork/Join to split the work among threads.
     * @param dir
     * @param threads number og threads
     * @return
     */
    static File[] collectFiles(String dir, int threads) {

        assert threads > 0

        withPool(threads) {

            runForkJoin(new File(dir)) {file ->

                final files = new ArrayList<File>()

                file.eachFile {

                    if (it.isDirectory()) {
                        forkOffChild(it)
                    } else {
                        files << it
                    }
                }

                files.plus(childrenResults.flatten())

            }
        } as File[]

    }

    /**
     *
     * @param files
     * @param threads
     * @param pattern
     * @param value
     */
    static public void replace(File[] files, int threads, String pattern, String value, String extension) {

        withPool(threads) {

            files.eachParallel { File file ->
                def fileContent = file.text
                def modifiedContent = RegexUtils.replace(fileContent, ~pattern, value)
                try {
                    def newFile = new File(file.absolutePath + "." + extension)
                    if (newFile.createNewFile()) {
                        newFile.setText(modifiedContent, 'UTF-8')
                    }
                    else {
                        // not created!
                    }
                } catch (Exception e) {
                    System.out.println(e)
                }
            }

        }
    }

}
