package org.gparsexperiment.util

import static groovyx.gpars.GParsPool.withPool
import static groovyx.gpars.GParsPool.runForkJoin
import java.util.regex.Pattern
import groovy.util.logging.Slf4j

/**
 * Utilities related to the file IO.
 */
@Slf4j
class IOUtils {

    /**
     * Returns list of all files found in given folder (including files from sub-folders).
     * Internally, it is using Fork/Join to split the work among threads.
     *
     * @param dir where to start collection files
     * @param threads number of threads
     *
     * @return all files found under the dir (recursively)
     */
    static File[] collectFiles(String dir, int threads) {

        log.debug ('starting collecting info about files in {} using {} threads', dir, threads)

        assert threads > 0

        withPool(threads) {

            runForkJoin(new File(dir)) {file ->

                final files = new ArrayList<File>()

                file.eachFile {

                    if (it.isDirectory()) {
                        log.trace ('starting a fork for {}', it)
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
     * For each file that contains pattern a new file will be created with modified content
     * (all patterns replaced with the value) and stored next to the original file. Name
     * of the new file is the same as original file plus the extension.
     *
     * @param files to process
     * @param threads how many parallel threads are used
     * @param pattern regex patter to find in the files
     * @param value to replace pattern
     * @param extension of modified file
     */
    static public void replace(File[] files, int threads, String pattern, String value, String extension) {

        log.debug ('starting replacing pattern in {} files using {} threads', files.length, threads)

        Pattern _pattern = ~pattern;

        withPool(threads) {

            files.eachParallel { File file ->

                String fileContent = file.text

                if ( RegexUtils.find(fileContent, _pattern) ) {

                    def modifiedContent = RegexUtils.replace(fileContent, _pattern, value)
                    try {
                        def newFile = new File(file.absolutePath + "." + extension)
                        if (newFile.createNewFile()) {
                            newFile.setText(modifiedContent, 'UTF-8')
                            log.trace ('created a new file {}', newFile)
                        }
                        else {
                            // not created!
                            log.warn ('unable to create a new file {}', newFile)
                        }
                    } catch (Exception e) {
                        log.warn ('Something went wrong when processing file {}, {}', file, e)
                    }
                }
            }
        }
    }

}
