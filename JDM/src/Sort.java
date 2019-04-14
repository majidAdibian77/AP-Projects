import java.util.Comparator;
import java.util.Date;

/**
 * this class is a static class and used for sort all list
 * there are tow another method in this class that used in som class
 */
public class Sort {
    public static void sortArray() {
        //in these lines we compare date of download descending. we use sort method of ArrayList

        Comparator<InformationNewDownload> comparator = (InformationNewDownload a, InformationNewDownload b) ->
        {
            for (String typeOfSort : SaveInformation.getTypeOfSort()) {
                if (setComparator(typeOfSort, a, b) != 0) {
                    return setComparator(typeOfSort, a, b);
                }
            }
            return 0;
        };

        SaveInformation.getAllDownloads().sort(comparator);
        SaveInformation.getCompletedDownloads().sort(comparator);
        SaveInformation.getProcessingDownloads().sort(comparator);
    }


    /**
     * In this method we compare to variable that can be String or int or float
     *
     * @param input1
     * @param input2
     * @param <T>    is Type of variable
     * @return
     */
    private static <T> int compare(T input1, T input2) {

        if (input1 instanceof Long) {
            return ((Long) input1).compareTo((Long) input2);
        } else if (input1 instanceof String) {
            return ((String) input1).compareTo((String) input2);
        } else if (input1 instanceof Date) {
            return ((Date) input1).compareTo((Date) input2);
        }else if (input1 instanceof Integer) {
            return ((Integer) input1).compareTo((Integer) input2);
        }
        return 0;
    }

    /**
     * this method gives to variable and a String and use another method to compare tow variable according to String
     *
     * @param s
     * @param a
     * @param b
     * @return
     */
    public static int setComparator(String s, InformationNewDownload a, InformationNewDownload b) {
        if (s.equals("Date(Ascending)")) {
            return compare(a.getDateOfDownload(), b.getDateOfDownload());
        }
        if (s.equals("Date(Descending)")) {
            return compare(b.getDateOfDownload(), a.getDateOfDownload());
        }
        if (s.equals("Name(Ascending)")) {
            return compare(a.getNameNewDownload(), b.getNameNewDownload());
        }
        if (s.equals("Name(Descending)")) {
            return compare(b.getNameNewDownload(), a.getNameNewDownload());
        }
        if (s.equals("Size(Ascending)")) {
            return compare(a.getSizeOfDownload(), b.getSizeOfDownload());
        }
        if (s.equals("Size(Descending)")) {
            return compare(b.getSizeOfDownload(), a.getSizeOfDownload());
        }
        return 0;
    }
/**
 * this class is used for recognize type of size
 */
    public static String recognizeSizeType(long size) {
        if (size < 1024) {
            return (float)size + " byte";
        } else if (size < 1024 * 1024) {
            return (cuttingNumber(((float) size / 1024) + "") + " KB");
        } else if (size < 1024 * 1024 * 1024) {
            return (cuttingNumber((float) size / (1024 * 1024) + "") + " MB");
        } else {
            return (cuttingNumber(((float) size / (1024 * 1024 * 1024)) + "") + " GB");
        }
    }

    // this method is for cutting number for create a float number with tow decimal places
    public static String cuttingNumber(String s) {
        if (s.contains(".") && s.indexOf(".") < s.length() - 3) {
            return (s.substring(0, s.indexOf(".") + 3));
        } else {
            return (s);
        }
    }
}

