package org.gparsexperiment

import org.gparsexperiment.util.IOUtils
import org.gparsexperiment.util.ARGSUtils
import org.gparsexperiment.util.SortUtils
import groovy.util.logging.Slf4j

/**
 * Command called from command line.
 */
@Slf4j
class Command {

    static void main(String[] args) {

        log.debug 'Starting'

        if (args.length < 3) {

            log.fatal ('Invalid arguments found {}', args)

            System.out.println("usage: java -jar FilePatternReplace.jar dir pattern replace [threads] [extension]")
            System.out.println("dir       - A path to a directory containing files")
            System.out.println("pattern   - regex pattern to find in files")
            System.out.println("replace   - if pattern is found then it is replaced by this value")
            System.out.println("threads   - optional argument, number of threads used. Defaults to 4.")
            System.out.println("extension - optional argument, extension of modified file. Defaults to 'mod'")

            System.out.println()
            System.out.println("Example: java -jar FilePatternReplace.jar /Places/WhiteHouse [O|o]bama Romney")

            log.error 'Exit'

            return;

        } else {

            log.debug ('Arguments: {}', args)

            int threads = ARGSUtils.getThreads(args, "4")
            String extension = ARGSUtils.getExtension(args, "mod")

            String dir = args[0]

            File[] files = IOUtils.collectFiles(dir, threads)

            if (files.length > 0) {

                // sort descending by size
                files = SortUtils.mergeSort(files, threads)

                //IOUtils.replace(files, threads, "x", "x", extension)

            }
        }

    }
}
