package cn.master.phoenix.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * created by 11's papa at 2025/4/25-11:42 @version v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private Integer code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-dd-MM hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private Boolean success;
    private T data;


    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(200)
                .message("操作成功")
                .data(data)
                .timestamp(LocalDateTime.now())
                .success(true)
                .build();
    }

    public static <T> ApiResponse<T> error(Integer code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(ApiError apiError) {
        return ApiResponse.<T>builder()
                .code(apiError.getCode())
                .message(apiError.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
