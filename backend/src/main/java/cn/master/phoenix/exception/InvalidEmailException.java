package cn.master.phoenix.exception;

/**
 * @author Created by 11's papa on 2025/6/10
 */
public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
    @lombok.Getter
    private int errorCode;
    public InvalidEmailException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
