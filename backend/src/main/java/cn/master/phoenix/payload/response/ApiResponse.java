package cn.master.phoenix.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * created by 11's papa at 2025/4/25-11:42 @version v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String message;
    private Object data;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    public static ApiResponse success(String message) {
        return new ApiResponse(true, message);
    }

    public static ApiResponse error(String message, Object data) {
        return new ApiResponse(false, message, data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(false, message);
    }
}
