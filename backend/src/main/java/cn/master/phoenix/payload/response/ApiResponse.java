package cn.master.phoenix.payload.response;

import lombok.*;

/**
 * created by 11's papa at 2025/4/25-11:42 @version v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private Long timestamp;
    private String message;
    private T data;
    private Integer code;

    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(200)
                .message("操作成功")
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static <T> ApiResponse<T> error(Integer code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static <T> ApiResponse<T> error(ApiError apiError) {
        return ApiResponse.<T>builder()
                .code(apiError.getCode())
                .message(apiError.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
