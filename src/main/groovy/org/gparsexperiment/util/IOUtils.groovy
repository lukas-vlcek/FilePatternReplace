package org.gparsexperiment.util

import static groovyx.gpars.GParsPool.withPool
import static groovyx.gpars.GParsPool.runForkJoin
import groovy.util.logging.Slf4j

/**
 * TBD
 */
@Slf4j
class IOUtils {

    /**
     * Returns list of all files found in given folder (including files from sub-folders).
     * Internally, it is using Fork/Join to split the work among threads.
     * @param dir
     * @param threads number og threads
     * @return
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
     *
     * @param files
     * @param threads
     * @param pattern
     * @param value
     */
    static public void replace(File[] files, int threads, String pattern, String value, String extension) {

        log.debug ('starting replacing pattern in {} files using {} threads', files.length, threads)

        withPool(threads) {

            files.eachParallel { File file ->
                def fileContent = file.text
                def modifiedContent = RegexUtils.replace(fileContent, ~pattern, value)
                try {
                    log.trace ('replacing content in file {}', file)
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
