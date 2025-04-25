package cn.master.phoenix.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * created by 11's papa at 2025/4/25-13:39 @version v1.0
 */
@Setter
@Getter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
