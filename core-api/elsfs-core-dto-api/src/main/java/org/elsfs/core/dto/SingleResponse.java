package org.elsfs.core.dto;

/**
 * ���ص�����¼����Ӧ
 * <p/>
 *
 * @author zeng
 */
public class SingleResponse<T> extends Response {

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static SingleResponse<Object> buildSuccess() {
        SingleResponse<Object> response = new SingleResponse<Object>();
        response.setSuccess(true);
        return response;
    }

    public static SingleResponse buildFailure(String errCode, String errMessage) {
        SingleResponse response = new SingleResponse<>();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> SingleResponse<T> of(T result) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(true);
        response.setResult(result);
        return response;
    }

}
