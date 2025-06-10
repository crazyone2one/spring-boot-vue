package cn.master.phoenix.handler;

import cn.master.phoenix.exception.CustomException;
import cn.master.phoenix.exception.IResultCode;
import cn.master.phoenix.exception.MsHttpResultCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * created by 11's papa at 2025/4/25-13:40 @version v1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultHolder handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResultHolder.error(HttpStatus.BAD_REQUEST.value(), "参数校验失败", errors);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResultHolder> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.internalServerError()
                .body(ResultHolder.error(HttpStatus.UNAUTHORIZED.value(),
                        "Authentication error", getStackTraceAsString(ex)));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResultHolder> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Bad credentials", ex);
        return ResponseEntity.internalServerError()
                .body(ResultHolder.error(HttpStatus.UNAUTHORIZED.value(),
                        "Invalid username or password", getStackTraceAsString(ex)));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultHolder handleHttpRequestMethodNotSupportedException(HttpServletResponse response, Exception exception) {
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        return ResultHolder.error(HttpStatus.METHOD_NOT_ALLOWED.value(), exception.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResultHolder> handleCustomException(CustomException ex) {
        IResultCode errorCode = ex.getErrorCode();
        if (Objects.isNull(errorCode)) {
            return ResponseEntity.internalServerError()
                    .body(ResultHolder.error(MsHttpResultCode.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage()));
        }
        int code = errorCode.getCode();
        String message = errorCode.getMessage();
        if (errorCode instanceof MsHttpResultCode) {
            return ResponseEntity.status(code)
                    .body(ResultHolder.error(code, message, ex.getMessage()));
        } else {
            // 响应码返回 500，设置业务状态码
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResultHolder.error(code, message, ex.getErrorCode()));
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResultHolder> handleGlobalException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(ResultHolder.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        e.getMessage(), getStackTraceAsString(e)));
    }

    public static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }
}
