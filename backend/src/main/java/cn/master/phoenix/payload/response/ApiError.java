package cn.master.phoenix.payload.response;

import lombok.Getter;

/**
 * @author Created by 11's papa on 2025/5/21
 */
@Getter
public enum ApiError {
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");

    private final Integer code;
    private final String message;

    ApiError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
