package org.elsfs.core.dto;

/**
 * ���������Ӧ
 *
 * @author zeng
 */
public class Response implements DTO {

    /**
     * �Ƿ�ɹ�
     */
    private boolean success;

    /**
     * �������
     */
    private String errCode;

    /**
     * ������ʾ
     */
    private String errMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        return "Response [success=" + success + ", errCode=" + errCode + ", errMessage=" + errMessage + "]";
    }

    /**
     * @return response
     */
    public static Response buildSuccess() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    /**
     * ʧ�ܶ���
     * @param errCode ʧ�ܴ���
     * @param errMessage ʧ����ʾ
     * @return response
     */
    public static Response buildFailure(String errCode, String errMessage) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

}
