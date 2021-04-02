/**
 * 
 */
package examples.pubhub.utilities;

/**
 * @author hectorv
 *
 */
public class Utils {

    //check if an object is null, return true if the given object is null false otherwise
    public static boolean isNull(Object obj) {
        if (obj == null) return true;
        else return false;
    }

    //check if an object is not null, return true if the given object is null false otherwise
    public static boolean isNotNull(Object obj) {
        if (obj != null) return true;
        else return false;
    }
    
    /**
     * Returns true if null or trims to an empty string.
     *
     * @param s The string
     * @return Whether the given string is blank.
     */
    public static boolean isBlank(String s) {
        return s == null || s.trim().length()==0 || "null".equals(s.trim());
    }

    /**
     * Returns true if given string is not null or all blanks..
     *
     * @param s The string
     * @return Whether the given string is not blank.
     */
    public static boolean isNotBlank(String s) {
        return s != null && s.trim().length() > 0;
    } 

    /**
     * Returns true if both strings are same
     */
    public static boolean isSame(String aString, String other) {
        return isBlank(aString) && isBlank(other)
                || !isBlank(aString) && !isBlank(other) && aString.equals(other);
    } 

}

