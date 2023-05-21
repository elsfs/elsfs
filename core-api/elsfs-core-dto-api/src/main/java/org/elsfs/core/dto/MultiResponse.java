package org.elsfs.core.dto;

import java.util.Collection;
import java.util.Collections;

/**
 * ����Ҫ���ص�������¼����Ӧ��
 * <p>
 * ͨ������������ѯ
 * </p>
 *
 * @author zeng
 */
public class MultiResponse<T> extends Response {

    private Iterable<T> result;

    public Iterable<T> getResult() {
        if (null == result) {
            return Collections.emptyList();
        }
        return result;
    }

    public void setResult(Iterable<T> result) {
        this.result = result;
    }

    /**
     * @return �ɹ���Ӧ����
     */
    public static MultiResponse<Object> buildSuccess() {
        MultiResponse<Object> response = new MultiResponse<Object>();
        response.setSuccess(true);
        return response;
    }

    /**
     * ʧ����Ӧ����
     * @param errCode ʧ�ܴ���
     * @param errMessage ʧ����ʾ
     * @return ʧ����Ӧ����
     */
    public static MultiResponse<Object> buildFailure(String errCode, String errMessage) {
        MultiResponse<Object> response = new MultiResponse<Object>();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    /**
     * �ɹ���Ӧ����
     * @param result ����
     * @return �ɹ���Ӧ����
     * @param <T> ��Ӧ����
     */
    public static <T> MultiResponse<T> of(Collection<T> result) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(true);
        response.setResult(result);
        return response;
    }

    public static <T> MultiResponse<T> of(Iterable<T> result) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(true);
        response.setResult(result);
        return response;
    }

}
