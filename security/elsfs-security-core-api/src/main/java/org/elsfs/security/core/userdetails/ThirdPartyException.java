package org.elsfs.security.core.userdetails;

/**
 * @author zeng
 * @since 0.0.1
 */
public class ThirdPartyException extends Exception {

    public ThirdPartyException() {
    }

    public ThirdPartyException(String message) {
        super(message);
    }

    public ThirdPartyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThirdPartyException(Throwable cause) {
        super(cause);
    }

    public ThirdPartyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
