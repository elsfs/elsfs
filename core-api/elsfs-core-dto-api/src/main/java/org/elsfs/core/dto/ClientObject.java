package org.elsfs.core.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * �ͻ���ͨ�ŵĶ��� �ͻ��˿�������ͼ�������HSF������
 *
 * @author zeng
 */
public interface ClientObject extends Serializable {

    long serialVersionUID = 1L;

    /**
     * ���������չֵ
     */
    Map<String, Object> extValues = new HashMap<String, Object>();

    /**
     * ��ȡֵ
     * @param key key
     * @return value
     */
    default Object getExtField(String key) {
        return extValues.get(key);
    }

    /**
     * ���ֵ
     * @param fieldName �ֶ���
     * @param value value
     */
    default void putExtField(String fieldName, Object value) {
        extValues.put(fieldName, value);
    }

    /**
     * ��ȡmap
     * @return map
     */
    default Map<String, Object> getExtValues() {
        return extValues;
    }

    /**
     * ����map ����
     * @param extValuesMap
     */
    default void setExtValues(Map<String, Object> extValuesMap) {
        Objects.requireNonNull(extValuesMap);
        extValues.putAll(extValuesMap);
    }

}
