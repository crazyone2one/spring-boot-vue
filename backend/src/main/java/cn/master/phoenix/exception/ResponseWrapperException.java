package cn.master.phoenix.exception;

/**
 * @author Created by 11's papa on 2025/5/21
 */
public class ResponseWrapperException extends RuntimeException {
    public ResponseWrapperException(String message) {
        super(message);
    }

    public ResponseWrapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
