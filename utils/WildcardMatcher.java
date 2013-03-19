package util;

/**
 * 支持通配符匹配的校验类
 * 
 * @author Gavinx 2012-12-20下午10:49:55
 */
public class WildcardMatcher {
    public static final String WILDCARD         = "*";
    public static final String WILDCARD_PATTERN = "\\*";

    /**
     * 判断一个字符串是不是通配符表达式
     * 
     * @author Gavinx 2012-12-22上午11:08:33
     * @param pattern
     * @return
     */
    public static boolean isWildcardExpression(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return false;
        }

        return pattern.contains(WILDCARD);
    }

    /**
     * 通配符匹配校验
     * 
     * @author Gavinx 2012-12-21上午12:43:40
     * @param pattern
     * @param text
     * @return
     */
    public static boolean matches(String pattern, String text) {
        if (pattern == null || pattern.isEmpty() || text == null) {
            return false;
        }

        boolean startWithWildcard = pattern.startsWith(WILDCARD);
        boolean endWithWildcard = pattern.endsWith(WILDCARD);
        String[] parts = pattern.split(WILDCARD_PATTERN);
        //如果pattern中仅包含通配符,直接返回
        if (startWithWildcard && parts.length == 0) {
            return true;
        }

        int fromIndex = 0;
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.isEmpty()) {
                continue;
            }

            int offset = -1;
            if (i != (parts.length - 1)) {//未到最后一个part时，做靠左匹配
                offset = text.indexOf(part, fromIndex);
            } else {//到了最后一个part，做靠右匹配
                offset = text.lastIndexOf(part);
                if (offset < fromIndex) {//匹配出的索引值在fromIndex之前时，目前目前正在处理的字符串中找不到，返回false
                    return false;
                }
            }

            //找不到返回false
            if (offset == -1) {
                return false;
            }

            //如果是头一次做匹配同时匹配出的偏移量>0，则要看是否以*开头，如果不是则不满足要求
            if (fromIndex == 0 && offset > fromIndex && !startWithWildcard) {
                return false;
            }
            fromIndex = offset + part.length();
        }

        //经过前面所有的校验，后面还有字符串但又不是以*结尾
        if (fromIndex < text.length() && !endWithWildcard) {
            return false;
        }

        return true;
    }

}
