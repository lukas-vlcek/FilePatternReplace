package org.gparsexperiment

import org.gparsexperiment.util.IOUtils
import org.gparsexperiment.util.ARGSUtils
import org.gparsexperiment.util.SortUtils
import groovy.util.logging.Slf4j

/**
 * Command called from command line.
 * For more info about what it does please read https://github.com/lukas-vlcek/FilePatternReplace
 */
@Slf4j
class Command {

    static void main(String[] args) {

        log.info 'Starting'

        if (args.length < 3) {

            log.fatal ('Invalid arguments found {}', args)

            System.out.println("usage: java -jar FilePatternReplace.jar folder pattern value [threads] [extension]")
            System.out.println("folder    - A path to a directory containing files")
            System.out.println("pattern   - regex pattern to find in files")
            System.out.println("value     - if pattern is found then it is replaced by this value")
            System.out.println("threads   - optional argument, number of threads used. Defaults to 4.")
            System.out.println("extension - optional argument, extension of modified file. Defaults to 'mod'")

            System.out.println()
            System.out.println("Example: java -jar FilePatternReplace.jar /Places/WhiteHouse [O|o]bama Romney")

            log.error 'Exit'

            return;

        } else {

            log.info ('Arguments: {}', args)

            String pattern = args[1]
            String replace = args[2]
            int threads = ARGSUtils.getThreads(args, "4")
            String extension = ARGSUtils.getExtension(args, "mod")

            log.info ('Threads: {}', threads)
            log.info ('File extension: {}', extension)

            String dir = args[0]

            File[] files = IOUtils.collectFiles(dir, threads)

            log.info ('Found {} files to process', files.length)

            if (files.length > 0) {

                // sort descending by size
                files = SortUtils.mergeSort(files, threads)

                log.info ('All occurences of \'{}\' in those files will be replace by \'{}\'', pattern, replace)
                IOUtils.replace(files, threads, pattern, replace, extension)

            }

            log.info 'Done!'
        }
    }
}
