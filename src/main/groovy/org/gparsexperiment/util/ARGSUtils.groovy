package org.gparsexperiment.util

/**
 * TBD
 */
class ARGSUtils {

    /**
     * Try to extract number of threads from arguments
     * If not provided, returns defValue
     *
     * @param args
     * @param defValue
     * @return
     */
    static int getThreads(String[] args, String defValue) {
        return Integer.parseInt(getValue(args, 4, defValue))
    }

    /**
     * Try to extract file extension from arguments
     * If not provided, returns defValue
     *
     * @param args
     * @param defValue
     * @return
     */
    static String getExtension(String[] args, String defValue) {
        return getValue(args, 5, defValue)
    }

    static private String getValue (String[] args, int position, String defValue) {
        return args.length < position ? defValue : args[position-1]
    }
}
