package org.gparsexperiment.util

import static groovyx.gpars.GParsPool.runForkJoin
import static groovyx.gpars.GParsPool.withPool
import groovy.util.logging.Slf4j

/**
 * Based on MergeSort example from http://www.gpars.org/guide/guide/3.%20Data%20Parallelism.html
 */
@Slf4j
class SortUtils {

    /**
     * Splits a list of Files in half
     */
    private static List<File> splitFileList(List<File> list) {
        int listSize = list.size()
        int middleIndex = listSize / 2
        def list1 = list[0..<middleIndex]
        def list2 = list[middleIndex..listSize - 1]
        return [list1, list2]
    }

    /**
     * Merges two sorted lists into one
     */
    private static List<File> merge(List<File> a, List<File> b) {
        int i = 0, j = 0
        final int newSize = a.size() + b.size()
        List<File> result = new ArrayList<File>(newSize)

        while ((i < a.size()) && (j < b.size())) {
            if (a[i].length() >= b[j].length()) result << a[i++]
            else result << b[j++]
        }

        if (i < a.size()) result.addAll(a[i..-1])
        else result.addAll(b[j..-1])
        return result
    }

    /**
     * Sort files in descending order by size.
     * @param files
     * @param threads
     * @return
     */
    public static File[] mergeSort(File[] files, int threads) {

        log.debug ('starting mergeSort on {} files using {} threads', files.length, threads)

        assert threads > 0

        withPool(threads) {
            runForkJoin(files.toList()) {fileList ->
                switch (fileList.size()) {
                    case 0..1:
                        return fileList                                   //store own result
                    case 2:
                        if (fileList[0].length() >= fileList[1].length()) return fileList     //store own result
                        else return fileList[-1..0]                       //store own result
                    default:
                        def splitList = splitFileList(fileList)
                        [splitList[0], splitList[1]].each {forkOffChild it}  //fork a child task
                        return merge(* childrenResults)      //use results of children tasks to calculate and store own result
                }
            }
        } as File[]
    }
}
