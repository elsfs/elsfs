package org.elsfs.core.util;

/**
 * @author zeng
 * @since 0.0.1
 */
public class ArrayUtil {

    /**
     * �ж������Ƿ�Ϊ��
     * @param array the array
     * @return empty true, not empty false
     */
    public static boolean isEmpty(Object... array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(String... array) {
        return array == null || array.length == 0;
    }

}
