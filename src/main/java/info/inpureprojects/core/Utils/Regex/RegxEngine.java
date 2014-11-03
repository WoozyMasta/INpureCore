package info.inpureprojects.core.Utils.Regex;

/**
 * Created by den on 11/3/2014.
 */
// http://codereview.stackexchange.com/questions/10776/simplified-regular-expression-engine
public class RegxEngine {
    public static boolean match(String regx, String candidate) {
        // Empty regx will always return false
        if (regx.isEmpty()) {
            return false;
        } else {
            if (regx.charAt(0) == '*') {
                // Last * matches everything
                if (regx.length() == 1) {
                    return true;
                } else {
                    return matchStar(regx.substring(1), candidate);
                }
            }
            // Return false if text is empty but pattern is not *
            else if (candidate.isEmpty()) {
                return false;
            } else if (regx.charAt(0) == '.' || regx.charAt(0) == candidate.charAt(0)) {
                // If the last regx matches the last text
                if (regx.length() == 1 && candidate.length() == 1) {
                    return true;
                }
                // If hasn't reached the end, try to match the rest strings
                else {
                    return match(regx.substring(1), candidate.substring(1));
                }
            } else {
                return false;
            }
        }
    }

    // Otherwise skip as many chars as required
    private static boolean matchStar(String regx, String candidate) {
        for (int i = 0; i < candidate.length(); i++) {
            if (match(regx, candidate.substring(i))) {
                return true;
            } else {
                continue;
            }
        }

        return false;
    }
}
