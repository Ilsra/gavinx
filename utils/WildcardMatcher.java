package util;

/**
 * ֧��ͨ���ƥ���У����
 * 
 * @author Gavinx 2012-12-20����10:49:55
 */
public class WildcardMatcher {
    public static final String WILDCARD         = "*";
    public static final String WILDCARD_PATTERN = "\\*";

    /**
     * �ж�һ���ַ����ǲ���ͨ������ʽ
     * 
     * @author Gavinx 2012-12-22����11:08:33
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
     * ͨ���ƥ��У��
     * 
     * @author Gavinx 2012-12-21����12:43:40
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
        //���pattern�н�����ͨ���,ֱ�ӷ���
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
            if (i != (parts.length - 1)) {//δ�����һ��partʱ��������ƥ��
                offset = text.indexOf(part, fromIndex);
            } else {//�������һ��part��������ƥ��
                offset = text.lastIndexOf(part);
                if (offset < fromIndex) {//ƥ���������ֵ��fromIndex֮ǰʱ��ĿǰĿǰ���ڴ�����ַ������Ҳ���������false
                    return false;
                }
            }

            //�Ҳ�������false
            if (offset == -1) {
                return false;
            }

            //�����ͷһ����ƥ��ͬʱƥ�����ƫ����>0����Ҫ���Ƿ���*��ͷ���������������Ҫ��
            if (fromIndex == 0 && offset > fromIndex && !startWithWildcard) {
                return false;
            }
            fromIndex = offset + part.length();
        }

        //����ǰ�����е�У�飬���滹���ַ������ֲ�����*��β
        if (fromIndex < text.length() && !endWithWildcard) {
            return false;
        }

        return true;
    }

}
