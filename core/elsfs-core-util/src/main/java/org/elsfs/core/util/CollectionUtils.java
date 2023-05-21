package org.elsfs.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author zeng
 * @since 0.0.1
 */
@SuppressWarnings("all")
public class CollectionUtils {

    /**
     * �ж� map�Ƿ�Ϊ��
     * @param extra extra
     * @return ��Ϊtrue �ǿ� false
     */
    public static boolean isEmpty(Map extra) {
        if (extra == null || extra.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * �ж� map�Ƿ�Ϊ�ǿ�
     * @param extra extra
     * @return ��Ϊfalse �ǿ� true
     */
    public static boolean isNotEmpty(Map extra) {
        if (extra == null || extra.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * �ж� map�Ƿ�Ϊ��
     * @param extra extra
     * @return ��Ϊtrue �ǿ� false
     */
    public static boolean isEmpty(Collection extra) {
        if (extra == null || extra.isEmpty()) {
            return true;
        }
        return false;
    }

}
