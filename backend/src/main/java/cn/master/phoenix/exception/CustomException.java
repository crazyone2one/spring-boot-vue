package cn.master.phoenix.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Created by 11's papa on 2025/6/10
 */
public class CustomException extends RuntimeException {
    protected IResultCode resultCode;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(IResultCode resultCode) {
        super(StringUtils.EMPTY);
        this.resultCode = resultCode;
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(IResultCode errorCode, String message) {
        super(message);
        this.resultCode = errorCode;
    }

    public CustomException(IResultCode errorCode, Throwable t) {
        super(t);
        this.resultCode = errorCode;
    }

    public IResultCode getErrorCode() {
        return resultCode;
    }
}
